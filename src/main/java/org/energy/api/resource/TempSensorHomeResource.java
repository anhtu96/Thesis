/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.api.resource;

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
import org.energy.model.TempSensorHomeModel;

/**
 *
 * @author songo
 */
@Path("tempsensorhome")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TempSensorHomeResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<TempSensorHomeModel> getStudentRecord() {
        ArrayList<TempSensorHomeModel> prod = new ArrayList<TempSensorHomeModel>();
        System.out.println("GET TEMPSENSORHOME");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tempsensorhome");
            while (rs.next()) {
                TempSensorHomeModel pr = new TempSensorHomeModel();
                pr.setId(rs.getInt("id"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setColor(rs.getString("color"));
                pr.setState(rs.getString("state"));
                pr.setFloor(rs.getInt("floor"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempSensorHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL TEMPSENSORHOME");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM tempsensorhome where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempSensorHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, TempSensorHomeModel entity) {
        try {
            System.out.println("PUT TEMPSENSORHOME");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE tempsensorhome SET"
                    + " devicename = '" + entity.getDevicename() + "'"
                    + ", deviceid = '" + entity.getDeviceid() + "'"
                    + ", state = '" + entity.getState() + "'"
                    + ", color = '" + entity.getColor() + "'"
                    + ", floor = '" + entity.getFloor() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempSensorHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(TempSensorHomeModel entity) {
        System.out.println("POST TEMPSENSORHOME");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,state,color,floor) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getState() + "','" + entity.getColor() + "','" + entity.getFloor() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO tempsensorhome " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempSensorHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
