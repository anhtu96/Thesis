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
import org.energy.model.TempSensorModel;

/**
 *
 * @author root
 */
public class SyncTempSensor {

    public static void insert(int deviceid, String devicename, String sendtime, double temp, double humid) {
        TempSensorModel tempsensor = new TempSensorModel();
        tempsensor.setDeviceid(deviceid);
        tempsensor.setDevicename(devicename);
        tempsensor.setSendtime(sendtime);
        tempsensor.setTemp(temp);
        tempsensor.setHumid(humid);
        System.out.println("sendtime " + sendtime);
        Context.getConnectionManager().updateTempSensor(tempsensor);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SyncTempSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!SocketConnected.isConnected()) {
            try {
                MysqlConfig mysqlconfig = new MysqlConfig();
                Connection conn = mysqlconfig.getConn();
                Statement st = conn.createStatement();
                st.executeUpdate("insert into tempsensor (deviceid,devicename,sendtime,temp,humid) values ('" + deviceid + "','" + devicename + "','" + sendtime + "','" + temp + "','" + humid + "')");
            } catch (SQLException ex) {
                Logger.getLogger(SyncTempSensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
