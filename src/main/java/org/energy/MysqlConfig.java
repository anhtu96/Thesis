/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author songo
 */
public class MysqlConfig {

//    private String url = "jdbc:mysql://162.241.169.6/haohmaru_mydb";
    private String url = "jdbc:mysql://localhost/my_db";
    public Connection conn;

    public MysqlConfig() {
        try {
//            conn = DriverManager.getConnection(url, "haohmaru_cs", "ChoCon8574*");
            conn = DriverManager.getConnection(url, "root", "1234");
        } catch (SQLException ex) {
            Logger.getLogger(MysqlConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

}
