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
import org.energy.model.TempSensorHomeModel;

/**
 *
 * @author root
 */
public class SyncTempSensorHome {

    public static void update(int deviceid, String state, String color) {
        TempSensorHomeModel tempsensorhome = new TempSensorHomeModel();
        tempsensorhome.setDeviceid(deviceid);
        tempsensorhome.setState(state);
        tempsensorhome.setColor(color);
        Context.getConnectionManager().updateTempSensorHome(tempsensorhome);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncTempSensorHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                System.out.println("dis");
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("update tempsensorhome set "
                        + " state = " + "'" + state + "'"
                        + ", color = " + "'" + color + "'"
                        + " where deviceid = " + deviceid);
            } catch (SQLException ex) {
                Logger.getLogger(SyncTempSensorHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
