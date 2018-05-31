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
import org.energy.model.TempDisplayModel;

/**
 *
 * @author songo
 */
@Path("tempdisplay")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TempDisplayResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<TempDisplayModel> getStudentRecord() {
        ArrayList<TempDisplayModel> prod = new ArrayList<TempDisplayModel>();
        System.out.println("GET TEMPDISPLAY");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tempdisplay");
            while (rs.next()) {
                TempDisplayModel pr = new TempDisplayModel();
                pr.setId(rs.getInt("id"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setTemp(rs.getDouble("temp"));
                pr.setHumid(rs.getDouble("humid"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempDisplayResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL TEMPDISPLAY");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM tempdisplay where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempDisplayResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, TempDisplayModel entity) {
        try {
            System.out.println("PUT TEMPDISPLAY");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE tempdisplay SET"
                    + " devicename = '" + entity.getDevicename() + "'"
                    + ", deviceid = '" + entity.getDeviceid() + "'"
                    + ", temp = '" + entity.getTemp() + "'"
                    + ", humid = '" + entity.getHumid() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempDisplayResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(TempDisplayModel entity) {
        System.out.println("POST TEMPDISPLAY");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,temp,humid) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getTemp() + "','" + entity.getHumid() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO tempdisplay " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(TempDisplayResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
