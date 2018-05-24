/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author songo
 */
public class NewClass {

    public static void main(String[] args) {
        new GlobalVars();
        System.out.println(GlobalVars.getCheckTemp());
//        Connection connection = null;
//        try {
//            connection = DriverManager
//                    .getConnection("jdbc:mysql://localhost:3306/my_db", "anhtu", "1234");
//
//        } catch (SQLException e) {
//            System.out.println("Connection Failed! Check output console");
//            e.printStackTrace();
//            return;
//        }
//        if (connection == null) {
//            System.out.println("null");
//        } else {
//            System.out.println("connected");
//        }
    }
}
