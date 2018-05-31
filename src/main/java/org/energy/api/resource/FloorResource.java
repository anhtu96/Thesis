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
import org.energy.model.FloorModel;

/**
 *
 * @author root
 */
@Path("floor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FloorResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<FloorModel> getStudentRecord() {
        ArrayList<FloorModel> prod = new ArrayList<FloorModel>();
        System.out.println("GET FLOOR");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from floor");
            while (rs.next()) {
                FloorModel pr = new FloorModel();
                pr.setId(rs.getInt("id"));
                pr.setNum(rs.getInt("num"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FloorResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL FLOOR");
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM floor where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FloorResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, FloorModel entity) {
        try {
            System.out.println("PUT FLOOR");
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE floor SET"
                    + " num = '" + entity.getNum() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FloorResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(FloorModel entity) {
        System.out.println("POST FLOOR");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,num) VALUES ('" + entity.getId() + "','" + entity.getNum() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO floor " + sqlvalue);
            System.out.println("INSERT INTO floor " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(FloorResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
