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
import org.energy.model.EmailModel;

/**
 *
 * @author songo
 */
@Path("email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<EmailModel> getStudentRecord() {
        ArrayList<EmailModel> prod = new ArrayList<EmailModel>();
        System.out.println("GET EMAIL");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from email");
            while (rs.next()) {
                EmailModel pr = new EmailModel();
                pr.setId(rs.getInt("id"));
                pr.setSender(rs.getString("sender"));
                pr.setPassword(rs.getString("password"));
                pr.setRecipient(rs.getString("recipient"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmailResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL EMAIL");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM email where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmailResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, EmailModel entity) {
        try {
            System.out.println("PUT EMAIL");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE email SET"
                    + " sender = '" + entity.getSender() + "'"
                    + ", password = '" + entity.getPassword() + "'"
                    + ", recipient = '" + entity.getRecipient() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmailResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(EmailModel entity) {
        System.out.println("POST EMAIL");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,sender,password,recipient) VALUES ('" + entity.getId() + "','" + entity.getSender() + "','" + entity.getPassword() + "','" + entity.getRecipient() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO email " + sqlvalue);
            System.out.println("INSERT INTO email " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmailResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
