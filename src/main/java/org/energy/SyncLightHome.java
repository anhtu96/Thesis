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
import org.energy.model.LightbulbHomeModel;

/**
 *
 * @author root
 */
public class SyncLightHome {

    public static void update(int deviceid, String onlinestatus, String color) {
        LightbulbHomeModel lightbulbhome = new LightbulbHomeModel();
        lightbulbhome.setDeviceid(deviceid);
        lightbulbhome.setOnlinestatus(onlinestatus);
        lightbulbhome.setColor(color);
        Context.getConnectionManager().updateLightbulbHome(lightbulbhome);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncLightHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                System.out.println("dis");
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                System.out.println("update lightbulbhome set "
                        + " onlinestatus = " + "'" + onlinestatus + "'"
                        + ", color = " + "'" + color + "'"
                        + " where deviceid = " + deviceid);
                st.executeUpdate("update lightbulbhome set "
                        + " onlinestatus = " + "'" + onlinestatus + "'"
                        + ", color = " + "'" + color + "'"
                        + " where deviceid = " + deviceid);

                st.close();

            } catch (SQLException ex) {
                Logger.getLogger(SyncTempSensorHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
