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
import org.energy.model.MusicManual1Model;

/**
 *
 * @author songo
 */
@Path("musicmanual1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicManual1Resource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<MusicManual1Model> getStudentRecord() {
        ArrayList<MusicManual1Model> prod = new ArrayList<MusicManual1Model>();
        System.out.println("GET MUSICMANUAL 1");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from musicmanual1");
            while (rs.next()) {
                MusicManual1Model pr = new MusicManual1Model();
                pr.setId(rs.getInt("id"));
                pr.setName(rs.getString("name"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicManual1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL MUSICMANUAL 1");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM musicmanual1 where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicManual1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, MusicManual1Model entity) {
        try {
            System.out.println("PUT MUSICMANUAL 1");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE musicmanual1 SET"
                    + " name = '" + entity.getName() + "'"
                    + " WHERE id = '" + id + "'";
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicManual1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(MusicManual1Model entity) {
        System.out.println("POST MUSICMANUAL 1");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,name) VALUES ('" + entity.getId() + "','" + entity.getName() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO musicmanual1 " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicManual1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
