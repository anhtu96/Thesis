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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author songo
 */
public class Flame implements Runnable {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();
    private Statement stHome = null;
    private ResultSet rs = null;
    public static byte[] sendData = {0, 0, 0, 0, 0, 1};
    private byte crc_high = 0;
    private byte crc_low = 0;
    private Checksum checksum = new Checksum();

    @Override
    public void run() {

        try {
            stHome = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                try {
                    byte data[] = event.getBytes();
                    if (data[0] == 0 && data[1] == 0) {
                        System.out.println(data[3] == 0 ? "Danger" : "Normal");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        while (true) {
            try {
                rs = stHome.executeQuery("select * from flamehome");
                while (rs.next()) {
                    sendData[2] = (byte) rs.getInt("deviceid");
                    crc_low = (byte) (checksum.getResult(sendData, sendData.length) & 0x00ff);
                    crc_high = (byte) ((checksum.getResult(sendData, sendData.length) & 0xff00) >> 8);
                    Main.serial.write(sendData);
                    Main.serial.write(crc_low);
                    Main.serial.write(crc_high);
                    Thread.sleep(1000);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStateException ex) {
                Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Flame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
