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
import org.energy.model.FlameModel;

/**
 *
 * @author songo
 */
public class SyncFlame {

    public static void insert(int deviceid, String devicename, String sendtime) {
        FlameModel flame = new FlameModel();
        flame.setDeviceid(deviceid);
        flame.setDevicename(devicename);
        flame.setSendtime(sendtime);
        Context.getConnectionManager().updateFlame(flame);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncFlame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("insert into flame (deviceid,devicename,sendtime) values ('" + deviceid + "','" + devicename + "','" + sendtime + "')");
            } catch (SQLException ex) {
                Logger.getLogger(SyncFlame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
