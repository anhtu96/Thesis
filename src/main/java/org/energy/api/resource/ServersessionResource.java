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
import org.energy.model.ServersessionModel;

/**
 *
 * @author songo
 */
@Path("serversession")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServersessionResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<ServersessionModel> getStudentRecord() {
        ArrayList<ServersessionModel> prod = new ArrayList<ServersessionModel>();
        System.out.println("GET SERVERSESSION");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from serversession");
            while (rs.next()) {
                ServersessionModel pr = new ServersessionModel();
                pr.setId(rs.getInt("id"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServersessionResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL SERVERSESSION");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM serversession where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServersessionResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, ServersessionModel entity) {
        try {
            System.out.println("PUT SERVERSESSION");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE serversession SET"
                    + " id = '" + entity.getId() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServersessionResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(ServersessionModel entity) {
        System.out.println("POST SERVERSESSION");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id) VALUES ('" + entity.getId() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO serversession " + sqlvalue);
            System.out.println("INSERT INTO serversession " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ServersessionResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
