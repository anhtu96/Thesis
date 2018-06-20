/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import com.pi4j.io.gpio.exception.UnsupportedBoardType;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.util.Console;
import java.io.IOException;
import static java.lang.Math.round;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author songo
 */
public class TempControl implements Runnable {

    private byte[] sendData = new byte[6];
    private MysqlConfig mysql = new MysqlConfig();
    private byte crc_high = 0;
    private byte crc_low = 0;
    private int sensorCount = 0;
    private Checksum checksum = new Checksum();
    public static ResultSet rs = null;
    private ResultSet rsDisplay = null;
    private Connection conn = mysql.getConn();
    private Statement st = null;
    private Statement stDisplay = null;
    private Statement stControlHome = null;
    private int rcvCount = 0;
    private long start = 0;
    private long duration = 0;

    @Override
    public void run() {

        try {
            st = conn.createStatement();
            stDisplay = conn.createStatement();
            stControlHome = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(TempControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        sendData[0] = 1;
        sendData[1] = 1;
        Main.serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    byte[] data = event.getBytes();
//                    for (int i = 0; i < data.length; i++) {
//                        System.out.println(data[i]);
//                    }
                    if (data[0] == sendData[0] && data[1] == sendData[1] && data[2] == sendData[2]) {
                        if ((data[6] == (byte) (checksum.getResult(data, data.length - 2) & 0x00ff)) && (data[7] == (byte) ((checksum.getResult(data, data.length - 2) & 0xff00) >> 8))) {
                            rcvCount = 1;
                            System.out.println("RECEIVE CONTROL" + data[2]);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TempControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        while (true) {

            try {
                for (int i = 2; i < sendData.length; i++) {
                    sendData[i] = 0;
                }
                rs = st.executeQuery("select * from tempcontrol");
//                System.out.println("checkTemp " + Main.checkTemp + "checkLight " + Main.checkLight);
                while (GlobalVars.getCheckLight() == 0 && GlobalVars.getCheckTemp() == 1) {
                    while (rs.next()) {
                        rcvCount = 0;
                        sendData[2] = (byte) rs.getInt("deviceid");
                        if (rs.getInt("auto") == 0) {
                            sendData[3] = 0;
                            sendData[4] = (byte) rs.getInt("fanspeed");
                            sendData[5] = (byte) rs.getInt("mist");
                        } else {
                            sensorCount = 0;
                            sendData[3] = 1;
                            rsDisplay = stDisplay.executeQuery("select * from tempdisplay where devicename like '" + rs.getString("sensor") + "'");
                            while (rsDisplay.next()) {
                                sensorCount++;
//                                sendData[4] = (byte) ((int) round(rsDisplay.getFloat("temp")) - rs.getInt("tempset"));
//                                System.out.println("send4 = " + sendTemp[4]);
//                                System.out.println("sensor " + rs.getString("sensor"));
//                                sendData[5] = (byte) ((int) round(rs.getInt("humidset") - rsDisplay.getFloat("humid")));
                                int humidVariance = (int) round(rs.getInt("humidset") - rsDisplay.getFloat("humid"));
                                int tempVariance = (int) round(rsDisplay.getFloat("temp")) - rs.getInt("tempset");
                                if (humidVariance > 0 && tempVariance > 0) {
                                    sendData[4] = -1;
                                    sendData[5] = 1;
                                }
                                if (tempVariance <= 0 && tempVariance > -2 && humidVariance > 5) {
                                    sendData[4] = -1;
                                    sendData[5] = 1;
                                }
                                if (tempVariance > 2 && humidVariance <= 0) {
                                    sendData[4] = (byte) humidVariance;
                                    sendData[5] = 1;
                                }
                                if (tempVariance <= 0 && humidVariance <= 0) {
                                    sendData[4] = (byte) humidVariance;
                                    sendData[5] = -1;
                                }
                            }
                            if (sensorCount == 0) {
                                sendData[4] = sendData[5] = -1;
                            }
                            System.out.println("fan " + sendData[4]);
                            System.out.println("humid " + sendData[5]);
                            rsDisplay.close();
                        }
                        crc_low = (byte) (checksum.getResult(sendData, sendData.length) & 0x00ff);
                        crc_high = (byte) ((checksum.getResult(sendData, sendData.length) & 0xff00) >> 8);
                        Main.serial.write(sendData);
                        Main.serial.write(crc_low);
                        Main.serial.write(crc_high);
                        start = System.currentTimeMillis();
                        duration = 0;
                        while (rcvCount == 0 && duration < 2000) {
                            duration = System.currentTimeMillis() - start;
                            if ((1000 - duration < 100) && (duration < 1000)) {
                                System.out.println("resend control");
                                Main.serial.write(sendData);
                                Main.serial.write(crc_low);
                                Main.serial.write(crc_high);
                            }
                            Thread.sleep(100);
                        }
                        if (rcvCount == 1) {
//                        System.out.println("online");
                            SyncTempControlHome.update(rs.getInt("deviceid"), "online", "green");
                        } else {
//                        System.out.println("offline");
                            SyncTempControlHome.update(rs.getInt("deviceid"), "offline", "red");
                        }
                        Thread.sleep(500);
                    }
                    GlobalVars.setCheckLight(1);
                    GlobalVars.setCheckTemp(0);
                }
//                System.out.println("checklight " + Main.checkLight);
                rs.close();
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedBoardType ex) {
                Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(TempControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TempControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(TempControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
