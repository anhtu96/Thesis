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
import org.energy.model.FlameHomeModel;

/**
 *
 * @author root
 */
public class SyncFlameHome {

    public static void update(int deviceid, String onlinestatus, String color, int state) {

        FlameHomeModel flamehome = new FlameHomeModel();
        flamehome.setDeviceid(deviceid);
        flamehome.setOnlinestatus(onlinestatus);
        flamehome.setColor(color);
        flamehome.setState(state);
        Context.getConnectionManager().updateFlameHome(flamehome);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncFlameHome.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                System.out.println("dis flame");
                System.out.println("update flamehome set "
                        + " onlinestatus = " + "'" + onlinestatus + "'"
                        + ", color = " + "'" + color + "'"
                        + ", state = " + "'" + state + "'"
                        + " where deviceid = " + deviceid);
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("update flamehome set "
                        + " onlinestatus = " + "'" + onlinestatus + "'"
                        + ", color = " + "'" + color + "'"
                        + ", state = " + "'" + state + "'"
                        + " where deviceid = " + deviceid);

            } catch (SQLException ex) {
                Logger.getLogger(SyncTempSensorHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
