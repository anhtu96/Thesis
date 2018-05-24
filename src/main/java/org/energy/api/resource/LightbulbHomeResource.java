/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.api.resource;

import java.io.IOException;
import java.sql.Connection;
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
import org.energy.Checksum;
import org.energy.LightControl;
import org.energy.Main;
import org.energy.MysqlConfig;
import org.energy.model.LightbulbHomeModel;

/**
 *
 * @author songo
 */
@Path("lightbulbhome")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LightbulbHomeResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<LightbulbHomeModel> getStudentRecord() {
        ArrayList<LightbulbHomeModel> prod = new ArrayList<LightbulbHomeModel>();
        System.out.println("hello GET lightbulbhome");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from lightbulbhome");
            while (rs.next()) {
                LightbulbHomeModel pr = new LightbulbHomeModel();
                pr.setId(rs.getInt("id"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setModestr(rs.getString("modestr"));
                pr.setState(rs.getInt("state"));
                pr.setOnlinestatus(rs.getString("onlinestatus"));
                pr.setColor(rs.getString("color"));
                pr.setFloor(rs.getInt("floor"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            //System.out.println("hello loi GET");
        }

        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) throws SQLException {
        System.out.println("hello DELETE");
        Statement st = conn.createStatement();
        st.executeUpdate("DELETE FROM lightbulbhome where id = " + id);
        st.close();
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, LightbulbHomeModel entity) throws SQLException {
        if (entity.getModestr().equals("manual")) {
            byte[] sendData = new byte[6];
            sendData[0] = 2;
            sendData[1] = 0;
            sendData[2] = (byte) entity.getDeviceid();
            sendData[3] = 0;
            sendData[4] = 0;
            sendData[5] = (byte) entity.getState();
            Checksum checksum = new Checksum();
            byte crc_high;
            byte crc_low;
            crc_low = (byte) (checksum.getResult(sendData, sendData.length) & 0x00ff);
            crc_high = (byte) ((checksum.getResult(sendData, sendData.length) & 0xff00) >> 8);
            try {
                if (!LightControl.rsHome.isClosed()) {
                    LightControl.rsHome.close();
                }
                Main.serial.write(sendData);
                Main.serial.write(crc_low);
                Main.serial.write(crc_high);
            } catch (IllegalStateException ex) {
                Logger.getLogger(LightbulbHomeResource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LightbulbHomeResource.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("helloput");
        Statement st = conn.createStatement();
        String sqlvalue = "UPDATE lightbulbhome SET"
                + " devicename = '" + entity.getDevicename() + "'"
                + ", deviceid = '" + entity.getDeviceid() + "'"
                + ", modestr = '" + entity.getModestr() + "'"
                + ", state = '" + entity.getState() + "'"
                + ", onlinestatus = '" + entity.getOnlinestatus() + "'"
                + ", color = '" + entity.getColor() + "'"
                + ", floor = '" + entity.getFloor() + "'"
                + " WHERE id = '" + id + "'";
        System.out.println(sqlvalue);
        st.executeUpdate(sqlvalue);
        st.close();
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(LightbulbHomeModel entity) {
        System.out.println("hellopost");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,modestr,state,onlinestatus,color,floor) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getModestr() + "','" + entity.getState() + "','" + entity.getOnlinestatus() + "','" + entity.getColor() + "','" + entity.getFloor() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO lightbulbhome " + sqlvalue);
            st.close();
        } catch (SQLException ex) {

        }
        return null;
    }
}
