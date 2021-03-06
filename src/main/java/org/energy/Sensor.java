/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

/**
 *
 * @author songo
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.pi4j.io.serial.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensor implements Runnable {

    /**
     *
     */
    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();
    private Statement st = null;
    private Statement stDisplay = null;
    private Statement stDisplay2 = null;
    private Statement stMail = null;
    private final byte[] sendTemp = {1, 0, 0, 0, 0, 2};
    private final byte[] sendFlame = {0, 0, 0, 0, 0, 1};
    // id = 1, sensor=0/control=1, "000" not used, require 2 sensors (temp + humid)
    public static byte[] check = new byte[2];
    public static int rcv, trans;
    public static boolean[] motorstate = new boolean[4];
    public static double[] sensor = new double[2];
    int cntTemp = 0;
    int cntFlame = 0;
    int id[] = new int[256];
    byte crc_low = 0;
    byte crc_high = 0;
    private Checksum checksum = new Checksum();
    private String devicename = null;
    private long start = 0;
    private long duration = 0;
    private int cntTimeTemp = 0;
    private int cntTimeFlame = 0;
    private int prevStateFlame = 0;
    private int cntStateFlame = 0;
    public static int waitFlag = 0;
    ResultSet rs = null;
    ResultSet rs2 = null;
    private ResultSet rsMail = null;

    @Override
    public void run() {

        try {
            stDisplay = conn.createStatement();
            stDisplay2 = conn.createStatement();
            st = conn.createStatement();
            stMail = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
        }

        Main.serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    byte[] data;
                    data = event.getBytes();
                    if (data[0] == sendTemp[0] && data[1] == sendTemp[1]) {
                        if ((data[8] == (byte) (checksum.getResult(data, data.length - 2) & 0x00ff)) && (data[9] == (byte) ((checksum.getResult(data, data.length - 2) & 0xff00) >> 8))) {
                            ResultSet rsDisplay = stDisplay.executeQuery("select * from tempdisplay");
                            int existed = 0;
                            if (data[2] == sendTemp[2]) {
                                sensor[0] = (double) data[4] + (double) data[5] / 10;
                                sensor[1] = (double) data[6] + (double) data[7] / 10;
                            }
                            if (data[2] == sendTemp[2]) {
                                while (rsDisplay.next()) {
                                    if (rsDisplay.getInt("deviceid") == (int) data[2]) {
                                        existed = 1;
                                        SyncTempDisplay.update((int) data[2], devicename, sensor[0], sensor[1]);
                                        System.out.println("update tempdisplay set "
                                                + " devicename = " + "'" + devicename + "'"
                                                + ", temp = " + sensor[0]
                                                + ", humid = " + sensor[1]
                                                + " where deviceid = " + (int) data[2]);
                                        break;
                                    }
                                }
                                rsDisplay.close();
                                if (existed == 0) {
                                    SyncTempDisplay.insert((int) data[2], devicename, sensor[0], sensor[1]);
                                    System.out.println("insert into tempdisplay (devicename,deviceid,temp,humid) VALUES ('" + devicename + "','" + (int) data[2] + "','" + sensor[0] + "','" + sensor[1] + "')");
                                }
                                SyncTempSensorHome.update((int) data[2], "online", "green");
                                if (cntTimeTemp == 3) {
                                    Date date = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    String sendtime = sdf.format(date);
                                    SyncTempSensor.insert((int) data[2], devicename, sendtime, sensor[0], sensor[1]);
                                }
                                cntTemp++;
                            }
                        }
                    }
                    if (data[0] == sendFlame[0] && data[1] == sendFlame[1]) {
                        if ((data[6] == (byte) (checksum.getResult(data, data.length - 2) & 0x00ff)) && (data[7] == (byte) ((checksum.getResult(data, data.length - 2) & 0xff00) >> 8))) {
                            SyncFlameHome.update((int) data[2], "online", "green", (int) data[3]);
                            cntFlame++;
                            if (data[3] == 0) {
                                cntStateFlame = 1;
                                if (cntTimeFlame == 0) {
                                    rsMail = stMail.executeQuery("select * from email");
                                    while (rsMail.next()) {
                                        SendMail.send(rsMail.getString("sender"), rsMail.getString("password"), rsMail.getString("recipient"), devicename);
                                    }
                                    Date date = new Date();
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    String sendtime = sdf.format(date);
                                    SyncFlame.insert((int) data[2], devicename, sendtime);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException ex) {
                    Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );

        while (true) {
            try {
                if (cntTimeTemp > 3) {
                    cntTimeTemp = 0;
                }
                if (cntTimeFlame > 1) {
                    cntTimeFlame = 0;
                }
                rs = st.executeQuery("select * from tempsensorhome");
                while (GlobalVars.getCheckTemp() == 0 && GlobalVars.getCheckLight() == 0) {
                    while (rs.next()) {

                        sendTemp[2] = (byte) rs.getInt("deviceid");
                        devicename = rs.getString("devicename");
                        crc_low = (byte) (checksum.getResult(sendTemp, sendTemp.length) & 0x00ff);
                        crc_high = (byte) ((checksum.getResult(sendTemp, sendTemp.length) & 0xff00) >> 8);

                        Main.serial.write(sendTemp);
                        Main.serial.write(crc_low);
                        Main.serial.write(crc_high);
                        start = System.currentTimeMillis();
                        duration = 0;
                        while ((cntTemp < 1) && (duration < 2000)) {
                            duration = System.currentTimeMillis() - start;
                            if ((1500 - duration < 100) && (duration < 1500)) {
                                System.out.println("resend " + duration);
                                Main.serial.write(sendTemp);
                                Main.serial.write(crc_low);
                                Main.serial.write(crc_high);
                            }
                            Thread.sleep(100);
                        }
                        System.out.println(duration);
                        if (cntTemp < 1) {
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String sendtime = sdf.format(date);
                            if (cntTimeTemp == 3) {
//                                stSensor.executeUpdate("insert into tempsensor (deviceid,devicename,sendtime,temp,humid) values ('" + (int) sendTemp[2] + "','" + devicename + "','" + sendtime + "','" + 0 + "','" + 0 + "')");
                                SyncTempSensor.insert((int) sendTemp[2], devicename, sendtime, 0, 0);
                            }
                            int existed = 0;
                            rs2 = stDisplay2.executeQuery("select * from tempdisplay where deviceid like " + sendTemp[2]);
                            while (rs2.next()) {
                                existed++;
                            }
                            rs2.close();
                            if (existed > 0) {
                                SyncTempDisplay.delete((int) sendTemp[2]);
                                System.out.println("delete from tempdisplay where deviceid like " + sendTemp[2]);
                            }
                            SyncTempSensorHome.update((int) sendTemp[2], "offline", "red");
                        }
                        cntTemp = 0;
                        Thread.sleep(500);
                    }
                    cntTimeTemp++;

                    cntStateFlame = 0;
                    rs = st.executeQuery("select * from flamehome");
                    while (rs.next()) {
                        sendFlame[2] = (byte) rs.getInt("deviceid");
                        devicename = rs.getString("devicename");
                        crc_low = (byte) (checksum.getResult(sendFlame, sendFlame.length) & 0x00ff);
                        crc_high = (byte) ((checksum.getResult(sendFlame, sendFlame.length) & 0xff00) >> 8);
                        Main.serial.write(sendFlame);
                        Main.serial.write(crc_low);
                        Main.serial.write(crc_high);
                        start = System.currentTimeMillis();
                        duration = 0;
                        while ((cntFlame < 1) && (duration < 3000)) {
                            duration = System.currentTimeMillis() - start;
                            if ((1500 - duration < 100) && (duration < 1500)) {
                                System.out.println("resend flame " + duration);
                                Main.serial.write(sendFlame);
                                Main.serial.write(crc_low);
                                Main.serial.write(crc_high);
                            }
                            Thread.sleep(100);
                        }
                        if (cntFlame < 1) {
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            String sendtime = sdf.format(date);
                            SyncFlameHome.update((int) sendFlame[2], "offline", "red", 1);
                        }
                        cntFlame = 0;
                        Thread.sleep(500);
                    }
                    prevStateFlame = cntStateFlame;
                    if (prevStateFlame == 1) {
                        cntTimeFlame++;
                    } else {
                        cntTimeFlame = 0;
                    }
                    GlobalVars.setCheckTemp(1);
                    System.out.println("cnt time flame " + cntTimeFlame);
                }
                rs.close();
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
