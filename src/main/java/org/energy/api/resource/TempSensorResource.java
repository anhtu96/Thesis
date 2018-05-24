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
import org.energy.model.TempSensorModel;

/**
 *
 * @author songo
 */
@Path("tempsensor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TempSensorResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<TempSensorModel> getStudentRecord() {
        ArrayList<TempSensorModel> prod = new ArrayList<TempSensorModel>();
        System.out.println("hello GET tempsensor");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tempsensor");
            while (rs.next()) {
                TempSensorModel pr = new TempSensorModel();
                pr.setId(rs.getInt("id"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setTemp(rs.getDouble("temp"));
                pr.setHumid(rs.getDouble("humid"));
                pr.setSendtime(rs.getString("sendtime"));
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
        st.executeUpdate("DELETE FROM tempsensor where id = " + id);
        st.close();
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, TempSensorModel entity) throws SQLException {
        System.out.println("helloput");
        Statement st = conn.createStatement();
        String sqlvalue = "UPDATE tempsensor SET"
                + " devicename = '" + entity.getDevicename() + "'"
                + ", deviceid = '" + entity.getDeviceid() + "'"
                + ", sendtime = '" + entity.getSendtime() + "'"
                + ", temp = '" + entity.getTemp() + "'"
                + ", humid = '" + entity.getHumid() + "'"
                + " WHERE id = '" + id + "'";
        System.out.println(sqlvalue);
        st.executeUpdate(sqlvalue);
        st.close();
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(TempSensorModel entity) {
        System.out.println("hellopost");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,temp,humid,sendtime) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getTemp() + "','" + entity.getHumid() + "','" + entity.getSendtime() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO tempsensor " + sqlvalue);
            st.close();
        } catch (SQLException ex) {

        }
        return null;
    }
}
