/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author songo
 */
public class LightControl implements Runnable {

    private byte[] sendData = new byte[6];
    private Checksum checksum = new Checksum();
    private byte crc_high = 0;
    private byte crc_low = 0;
    private int rcvCount = 0;
    private String switchtype;
    private int cntMax;
    private int cntBefore;
    private int checkBefore;
    private int state;
    private long start = 0;
    private long duration = 0;
    private Calendar currentCalendar = Calendar.getInstance();
    private Calendar listCalendar = Calendar.getInstance();
    private Calendar maxCalendar = Calendar.getInstance();
    private Calendar maxbeforeCalendar = Calendar.getInstance();
    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    public static ResultSet rsHome = null;
    private ResultSet rsAuto = null;
    private Statement stAuto = null;
    private Statement stHome = null;

    @Override
    public void run() {

        try {
            stAuto = conn.createStatement();
            stHome = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(LightControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendData[0] = 2;
        sendData[1] = 0;
        sendData[3] = 0;
        sendData[4] = 0;

        Main.serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    byte[] data = event.getBytes();
                    if (data[0] == sendData[0] && data[1] == sendData[1] && data[2] == sendData[2]) {
                        if ((data[6] == (byte) (checksum.getResult(data, data.length - 2) & 0x00ff)) && (data[7] == (byte) ((checksum.getResult(data, data.length - 2) & 0xff00) >> 8))) {
                            rcvCount = 1;
                            System.out.println("LIGHT RECEIVED");
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TempControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        while (true) {
            while (GlobalVars.getCheckLight() == 1) {
                System.out.println("LIGHT CONTROL");
                try {
                    rsHome = stHome.executeQuery("select * from lightbulbhome");
                    while (rsHome.next()) {
                        rcvCount = 0;
                        sendData[2] = (byte) rsHome.getInt("deviceid");
                        switchtype = "Off";
                        checkBefore = 0;
                        cntMax = 0;
                        cntBefore = 0;
                        state = 0;
                        if (rsHome.getString("modestr").equals("auto")) {
                            rsAuto = stAuto.executeQuery("select * from lightbulbauto where deviceid like " + rsHome.getInt("deviceid"));
                            while (rsAuto.next()) {
                                if (rsAuto.getInt("state") == 1) {
                                    String time = rsAuto.getString("hour") + ":" + rsAuto.getString("min") + " " + rsAuto.getString("period");
                                    listCalendar.setTime(new SimpleDateFormat("hh:mm a").parse(time));
                                    String currentTime = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());
//                                System.out.println(time);
                                    currentCalendar.setTime(new SimpleDateFormat("hh:mm a").parse(currentTime));
                                    if (listCalendar.after(currentCalendar) && checkBefore == 0) {
                                        if ((maxCalendar.before(listCalendar) || cntMax == 0)) {
                                            maxCalendar.setTime(listCalendar.getTime());
                                            switchtype = rsAuto.getString("switchtype");
                                        }
                                        cntMax++;
                                    }
                                    if (listCalendar.before(currentCalendar) || listCalendar.equals(currentCalendar)) {
                                        checkBefore = 1;
                                        if (maxbeforeCalendar.before(listCalendar) || cntBefore == 0) {
                                            maxbeforeCalendar.setTime(listCalendar.getTime());
                                            switchtype = rsAuto.getString("switchtype");
                                        }
                                        cntBefore++;
                                    }
                                }
                            }
                            if (switchtype.equals("Off")) {
                                state = 0;
                            } else {
                                state = 1;
                            }
                            rsAuto.close();
                        } else {
                            state = rsHome.getInt("state");
                        }
                        sendData[5] = (byte) state;
                        crc_low = (byte) (checksum.getResult(sendData, sendData.length) & 0x00ff);
                        crc_high = (byte) ((checksum.getResult(sendData, sendData.length) & 0xff00) >> 8);
                        Main.serial.write(sendData);
                        Main.serial.write(crc_low);
                        Main.serial.write(crc_high);
                        start = System.currentTimeMillis();
                        duration = 0;
                        while (rcvCount == 0 && duration < 1000) {
                            duration = System.currentTimeMillis() - start;
                        }
                        if (rcvCount == 1) {
                            System.out.println("light online");
                            SyncLightHome.update(rsHome.getInt("deviceid"), "online", "green");
                        } else {
                            System.out.println("light offline");
                            SyncLightHome.update(rsHome.getInt("deviceid"), "offline", "red");
                        }
                        Thread.sleep(500);
                    }
                    rsHome.close();
                    GlobalVars.setCheckLight(0);
                } catch (SQLException ex) {
                    Logger.getLogger(LightControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(LightControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalStateException ex) {
                    Logger.getLogger(LightControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LightControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LightControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(LightControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
