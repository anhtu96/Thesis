/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.energy.model.TempDisplayModel;

/**
 *
 * @author root
 */
public class SyncTempDisplay {

    public static void insert(int deviceid, String devicename, double temp, double humid) {
        TempDisplayModel tempdisplay = new TempDisplayModel();
        tempdisplay.setId(0);
        tempdisplay.setDeviceid(deviceid);
        tempdisplay.setDevicename(devicename);
        tempdisplay.setTemp(temp);
        tempdisplay.setHumid(humid);
        Context.getConnectionManager().updateTempDisplay(tempdisplay);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncTempDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                System.out.println("dis");
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("insert into tempdisplay (devicename,deviceid,temp,humid) VALUES ('" + devicename + "','" + deviceid + "','" + temp + "','" + humid + "')");
                System.out.println("insert into tempdisplay (devicename,deviceid,temp,humid) VALUES ('" + devicename + "','" + deviceid + "','" + temp + "','" + humid + "')");
            } catch (SQLException ex) {
                Logger.getLogger(SyncTempDisplay.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("prob");
            }
        } else {
            System.out.println("conn");
        }
    }

    public static void update(int deviceid, String devicename, double temp, double humid) {
        TempDisplayModel tempdisplay = new TempDisplayModel();
        tempdisplay.setId(1);
        tempdisplay.setDeviceid(deviceid);
        tempdisplay.setDevicename(devicename);
        tempdisplay.setTemp(temp);
        tempdisplay.setHumid(humid);
        Context.getConnectionManager().updateTempDisplay(tempdisplay);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncTempDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                System.out.println("dis");
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("update tempdisplay set "
                        + " devicename = " + "'" + devicename + "'"
                        + ", temp = " + temp
                        + ", humid = " + humid
                        + " where deviceid = " + (int) deviceid
                );
            } catch (SQLException ex) {
                Logger.getLogger(SyncTempDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("conn");
        }
    }

    public static void delete(int deviceid) {
        TempDisplayModel tempdisplay = new TempDisplayModel();
        tempdisplay.setId(2);
        tempdisplay.setDeviceid(deviceid);
        Context.getConnectionManager().updateTempDisplay(tempdisplay);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncTempDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                System.out.println("dis");
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("delete from tempdisplay where deviceid like " + deviceid);
            } catch (SQLException ex) {
                Logger.getLogger(SyncTempDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("conn");
        }
    }
}
