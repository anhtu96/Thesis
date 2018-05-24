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
import org.energy.model.LightbulbAutoModel;

/**
 *
 * @author songo
 */
@Path("lightbulbauto")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LightbulbAutoResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<LightbulbAutoModel> getStudentRecord() {
        ArrayList<LightbulbAutoModel> prod = new ArrayList<LightbulbAutoModel>();
        System.out.println("hello GET lightbulbauto");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from lightbulbauto");
            while (rs.next()) {
                LightbulbAutoModel pr = new LightbulbAutoModel();
                pr.setId(rs.getInt("id"));
                pr.setDevicename(rs.getString("devicename"));
                pr.setDeviceid(rs.getInt("deviceid"));
                pr.setHour(rs.getString("hour"));
                pr.setMin(rs.getString("min"));
                pr.setPeriod(rs.getString("period"));
                pr.setState(rs.getInt("state"));
                pr.setSwitchtype(rs.getString("switchtype"));
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
        st.executeUpdate("DELETE FROM lightbulbauto where id = " + id);
        st.close();
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, LightbulbAutoModel entity) throws SQLException {
        System.out.println("helloput");
        Statement st = conn.createStatement();
        String sqlvalue = "UPDATE lightbulbauto SET"
                + " devicename = '" + entity.getDevicename() + "'"
                + ", deviceid = '" + entity.getDeviceid() + "'"
                + ", hour = '" + entity.getHour() + "'"
                + ", min = '" + entity.getMin() + "'"
                + ", period = '" + entity.getPeriod() + "'"
                + ", switchtype = '" + entity.getSwitchtype() + "'"
                + ", state = '" + entity.getState() + "'"
                + " WHERE id = '" + id + "'";
        System.out.println(sqlvalue);
        st.executeUpdate(sqlvalue);
        st.close();
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(LightbulbAutoModel entity) {
        System.out.println("hellopost");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,devicename,deviceid,hour,min,period,switchtype,state) VALUES ('" + entity.getId() + "','" + entity.getDevicename() + "','" + entity.getDeviceid() + "','" + entity.getHour() + "','" + entity.getMin() + "','" + entity.getPeriod() + "','" + entity.getSwitchtype() + "','" + entity.getState() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO lightbulbauto " + sqlvalue);
            System.out.println("INSERT INTO lightbulbauto " + sqlvalue);
            st.close();
        } catch (SQLException ex) {

        }
        return null;
    }
}
