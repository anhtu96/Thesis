/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.energy.api.resource;

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
import org.energy.MysqlConfig;
import org.energy.model.FlameHomeModel;

/**
 *
 * @author songo
 */
@Path("flamehome")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlameHomeResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<FlameHomeModel> getStudentRecord() {
        ArrayList<FlameHomeModel> prod = new ArrayList<FlameHomeModel>();
        System.out.println("GET FLAMEHOME");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from flamehome");
            while (rs.next()) {
                FlameHomeModel pr = new FlameHomeModel();
                pr.setId(rs.getInt("id"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setState(rs.getInt("state"));
                pr.setOnlinestatus(rs.getString("onlinestatus"));
                pr.setColor(rs.getString("color"));
                pr.setFloor(rs.getInt("floor"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlameHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL FLAMEHOME");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM flamehome where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlameHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, FlameHomeModel entity) {
        try {
            System.out.println("PUT FLAMEHOME");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE flamehome SET"
                    + " deviceid = '" + entity.getDeviceid() + "'"
                    + ", devicename = '" + entity.getDevicename() + "'"
                    + ", state = '" + entity.getState() + "'"
                    + ", onlinestatus = '" + entity.getOnlinestatus() + "'"
                    + ", color = '" + entity.getColor() + "'"
                    + ", floor = '" + entity.getFloor() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlameHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(FlameHomeModel entity) {
        System.out.println("POST FLAMEHOME");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,deviceid,devicename,state,onlinestatus,color,floor) VALUES ('" + entity.getId() + "','" + entity.getDeviceid() + "','" + entity.getDevicename() + "','" + entity.getState() + "','" + entity.getOnlinestatus() + "','" + entity.getColor() + "','" + entity.getFloor() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO flamehome " + sqlvalue);
            System.out.println("INSERT INTO flamehome " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlameHomeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
