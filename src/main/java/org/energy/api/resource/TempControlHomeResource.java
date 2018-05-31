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
import org.energy.model.TempControlHomeModel;

/**
 *
 * @author songo
 */
@Path("tempcontrolhome")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TempControlHomeResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<TempControlHomeModel> getStudentRecord() {
        ArrayList<TempControlHomeModel> prod = new ArrayList<TempControlHomeModel>();
        System.out.println("GET TEMPCONTROLHOME");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tempcontrolhome");
            while (rs.next()) {
                System.out.println(rs.getString("devicename"));
                TempControlHomeModel pr = new TempControlHomeModel();
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
            Logger.getLogger(TempControlHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL TEMPCONTROLHOME");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM tempcontrolhome where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempControlHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, TempControlHomeModel entity) {
        try {
            System.out.println("PUT TEMPCONTROLHOME");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE tempcontrolhome SET"
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
            Logger.getLogger(TempControlHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(TempControlHomeModel entity) {
        System.out.println("POST TEMPCONTROLHOME");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,state,color,floor) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getState() + "','" + entity.getColor() + "','" + entity.getFloor() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO tempcontrolhome " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempControlHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
