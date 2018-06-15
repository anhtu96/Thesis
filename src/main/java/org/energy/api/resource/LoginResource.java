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
import org.energy.model.LoginModel;

/**
 *
 * @author songo
 */
@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<LoginModel> getStudentRecord() {
        ArrayList<LoginModel> prod = new ArrayList<LoginModel>();
        System.out.println("GET LOGIN");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from login");
            while (rs.next()) {
                LoginModel pr = new LoginModel();
                pr.setId(rs.getInt("id"));
                pr.setUsername(rs.getString("username"));
                pr.setPassword(rs.getString("password"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL LOGIN");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM login where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, LoginModel entity) {
        try {
            System.out.println("PUT LOGIN");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE login SET"
                    + " username = '" + entity.getUsername() + "'"
                    + ", password = '" + entity.getPassword() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(LoginModel entity) {
        System.out.println("POST LOGIN");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,username,password) VALUES ('" + entity.getId() + "','" + entity.getUsername() + "','" + entity.getPassword() + "','" + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO login " + sqlvalue);
            System.out.println("INSERT INTO login " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
