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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.energy.MysqlConfig;
import org.energy.model.FlameModel;

/**
 *
 * @author songo
 */
public class FlameResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<FlameModel> getStudentRecord() {
        ArrayList<FlameModel> prod = new ArrayList<FlameModel>();
        System.out.println("GET FLAME");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from flame");
            while (rs.next()) {
                FlameModel pr = new FlameModel();
                pr.setId(rs.getInt("id"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setSendtime(rs.getString("sendtime"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlameResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) throws SQLException {
        System.out.println("DEL FLAME");
        Statement st = conn.createStatement();
        st.executeUpdate("DELETE FROM flame where id = " + id);
        st.close();
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, FlameModel entity) {
        try {
            System.out.println("PUT FLAME");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE flame SET"
                    + " devicename = '" + entity.getDevicename() + "'"
                    + ", deviceid = '" + entity.getDeviceid() + "'"
                    + ", sendtime = '" + entity.getSendtime() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlameResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(FlameModel entity) {
        System.out.println("POST FLAME");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,sendtime) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getSendtime() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO flame " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FlameResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
