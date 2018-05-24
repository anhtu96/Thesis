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
import org.energy.model.TempControlHomeModel;

/**
 *
 * @author root
 */
public class SyncTempControlHome {

    public static void update(int deviceid, String state, String color) {
        TempControlHomeModel tempcontrolhome = new TempControlHomeModel();
        tempcontrolhome.setDeviceid(deviceid);
        tempcontrolhome.setState(state);
        tempcontrolhome.setColor(color);
        Context.getConnectionManager().updateTempControlHome(tempcontrolhome);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncTempControlHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                System.out.println("dis control");
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("update tempcontrolhome set "
                        + " state = " + "'" + state + "'"
                        + ", color = " + "'" + color + "'"
                        + " where deviceid = " + deviceid);
                System.out.println("update tempcontrolhome set "
                        + " state = " + "'" + state + "'"
                        + ", color = " + "'" + color + "'"
                        + " where deviceid = " + deviceid);
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(SyncTempSensorHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
