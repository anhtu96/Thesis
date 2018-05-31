/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.api.resource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.energy.MysqlConfig;
import org.energy.model.TempControlModel;
import org.energy.Checksum;
import org.energy.Main;
import org.energy.TempControl;

/**
 *
 * @author songo
 */
@Path("tempcontrol")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TempControlResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();
    private Checksum checksum = new Checksum();
    private byte[] sendData = new byte[6];
    int crc;
    private byte crc_low;
    private byte crc_high;

    @PermitAll
    @GET
    public ArrayList<TempControlModel> getStudentRecord() {
        ArrayList<TempControlModel> prod = new ArrayList<TempControlModel>();
        System.out.println("GET TEMPCONTROL");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tempcontrol");
            while (rs.next()) {
                TempControlModel pr = new TempControlModel();
                pr.setId(rs.getInt("id"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setAuto(rs.getInt("auto"));
                pr.setModestr(rs.getString("modestr"));
                pr.setTempset(rs.getInt("tempset"));
                pr.setHumidset(rs.getInt("humidset"));
                pr.setMist(rs.getInt("mist"));
                pr.setFanspeed(rs.getInt("fanspeed"));
                pr.setSensor(rs.getString("sensor"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id, TempControlModel entity) {
        try {
            System.out.println("DEL TEMPCONTROL");
            Statement st = conn.createStatement();
            if (!TempControl.rs.isClosed()) {
                TempControl.rs.close();
            }
            st.executeUpdate("DELETE FROM tempcontrol where id = " + id);
//        TempControl.rs.close();
            byte[] sendData = {1, 1, (byte) entity.getDeviceid(), 0, 0, 0};
            crc = checksum.getResult(sendData, sendData.length);
            crc_low = (byte) (crc & 0x00ff);
            crc_high = (byte) ((crc & 0xff00) >> 8);
            try {
                Main.serial.write(sendData);
                Main.serial.write(crc_low);
                Main.serial.write(crc_high);
            } catch (IllegalStateException ex) {
                Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, TempControlModel entity) {
        try {
            System.out.println("PUT TEMPCONTROL");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tempcontrol where id like " + id);
            byte[] sendData = new byte[6];
            sendData[0] = 1;
            sendData[1] = 1;
            if (!TempControl.rs.isClosed()) {
                TempControl.rs.close();
            }
//        while (rs.next()) {
//            if (rs.getInt("deviceid") != entity.getDeviceid()) {
//                System.out.println("DEVICEID " + rs.getInt("deviceid"));
//                sendData[2] = (byte) rs.getInt("deviceid");
//                sendData[3] = 0;
//                sendData[4] = 0;
//                sendData[5] = 0;
//                crc = checksum.getResult(sendData, sendData.length);
//                crc_low = (byte) (crc & 0x00ff);
//                crc_high = (byte) ((crc & 0xff00) >> 8);
//                try {
//                    Main.serial.write(sendData);
//                    Main.serial.write(crc_low);
//                    Main.serial.write(crc_high);
//                } catch (IllegalStateException ex) {
//                    Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        }

            sendData[2] = (byte) entity.getDeviceid();
            if (entity.getAuto() == 0) {
                sendData[3] = 0;
                sendData[4] = (byte) entity.getFanspeed();
                sendData[5] = (byte) entity.getMist();
                crc = checksum.getResult(sendData, sendData.length);
                crc_low = (byte) (crc & 0x00ff);
                crc_high = (byte) ((crc & 0xff00) >> 8);
                try {
                    Main.serial.write(sendData);
                    Main.serial.write(crc_low);
                    Main.serial.write(crc_high);
                } catch (IllegalStateException ex) {
                    Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String sqlvalue = "UPDATE tempcontrol SET"
                    + " devicename = '" + entity.getDevicename() + "'"
                    + ", deviceid = '" + entity.getDeviceid() + "'"
                    + ", auto = '" + entity.getAuto() + "'"
                    + ", modestr = '" + entity.getModestr() + "'"
                    + ", tempset = '" + entity.getTempset() + "'"
                    + ", humidset = '" + entity.getHumidset() + "'"
                    + ", mist = '" + entity.getMist() + "'"
                    + ", fanspeed = '" + entity.getFanspeed() + "'"
                    + ", sensor = '" + entity.getSensor() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(TempControlModel entity) {
        System.out.println("POST TEMPCONTROL");
        try {
            byte[] sendData = new byte[6];
            sendData[0] = 1;
            sendData[1] = 1;
            sendData[2] = (byte) entity.getDeviceid();
            if (entity.getAuto() == 0) {
                sendData[3] = 0;
                sendData[4] = (byte) entity.getFanspeed();
                sendData[5] = (byte) entity.getMist();
                crc = checksum.getResult(sendData, sendData.length);
                crc_low = (byte) (crc & 0x00ff);
                crc_high = (byte) ((crc & 0xff00) >> 8);
                try {
                    Main.serial.write(sendData);
                    Main.serial.write(crc_low);
                    Main.serial.write(crc_high);
                } catch (IllegalStateException ex) {
                    Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,auto,tempset,humidset,mist,fanspeed,sensor,modestr) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getAuto() + "','" + entity.getTempset() + "','" + entity.getHumidset() + "','" + entity.getMist() + "','" + entity.getFanspeed() + "','" + entity.getSensor() + "','" + entity.getModestr() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO tempcontrol " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempControlResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
