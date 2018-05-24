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
        System.out.println("hello GET floor");
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
        st.executeUpdate("DELETE FROM floor where id = " + id);
        st.close();
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, FloorModel entity) throws SQLException {
        System.out.println("helloput");
        Statement st = conn.createStatement();
        String sqlvalue = "UPDATE floor SET"
                + " num = '" + entity.getNum() + "'"
                + " WHERE id = '" + id + "'";
        System.out.println(sqlvalue);
        st.executeUpdate(sqlvalue);
        st.close();
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(FloorModel entity) {
        System.out.println("hellopost");
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,num) VALUES ('" + entity.getId() + "','" + entity.getNum() + "')";
            System.out.println(entity.getId());
            st.executeUpdate("INSERT INTO floor " + sqlvalue);
            System.out.println("INSERT INTO floor " + sqlvalue);
            st.close();
        } catch (SQLException ex) {

        }
        return null;
    }
}
