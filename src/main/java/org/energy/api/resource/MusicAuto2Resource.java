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
import org.energy.PlayMusicAuto2;
import org.energy.model.MusicAuto2Model;

/**
 *
 * @author songo
 */
@Path("musicauto2")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicAuto2Resource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<MusicAuto2Model> getStudentRecord() {
        ArrayList<MusicAuto2Model> prod = new ArrayList<MusicAuto2Model>();
        System.out.println("GET MUSICAUTO 2");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from musicautoch2");
            while (rs.next()) {
                MusicAuto2Model pr = new MusicAuto2Model();
                pr.setId(rs.getInt("id"));
                pr.setName(rs.getString("name"));
                pr.setHour(rs.getString("hour"));
                pr.setMin(rs.getString("min"));
                pr.setPeriod(rs.getString("period"));
                pr.setState(rs.getString("state"));
                pr.setColor1(rs.getString("color1"));
                pr.setColor2(rs.getString("color2"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL MUSICAUTO 2");
            if (PlayMusicAuto2.clip.isOpen()) {
                PlayMusicAuto2.clip.close();
                PlayMusicAuto2.currentSong = "";
                PlayMusicAuto2.songTmp = "";
                PlayMusicAuto2.rs.close();
            }
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM musicautoch2 where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, MusicAuto2Model entity) {
        try {
            System.out.println("PUT MUSICAUTO 2");
            if (PlayMusicAuto2.clip.isOpen()) {
                PlayMusicAuto2.clip.close();
                PlayMusicAuto2.currentSong = "";
                PlayMusicAuto2.songTmp = "";
                PlayMusicAuto2.rs.close();
            }
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE musicautoch2 SET"
                    + " name = '" + entity.getName() + "'"
                    + ", hour = '" + entity.getHour() + "'"
                    + ", min = '" + entity.getMin() + "'"
                    + ", period = '" + entity.getPeriod() + "'"
                    + ", state = '" + entity.getState() + "'"
                    + ", color1 = '" + entity.getColor1() + "'"
                    + ", color2 = '" + entity.getColor2() + "'"
                    + " WHERE id = '" + id + "'";
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(MusicAuto2Model entity) {
        System.out.println("POST MUSICAUTO 2");
        try {
            if (PlayMusicAuto2.clip.isOpen()) {
                PlayMusicAuto2.clip.close();
                PlayMusicAuto2.currentSong = "";
                PlayMusicAuto2.songTmp = "";
                PlayMusicAuto2.rs.close();
            }
            Statement st = conn.createStatement();
            String sqlvalue = "(id,name,hour,min,period,state,color1,color2) VALUES ('" + entity.getId() + "','" + entity.getName() + "','" + entity.getHour() + "','" + entity.getMin() + "','" + entity.getPeriod() + "','" + entity.getState() + "','" + entity.getColor1() + "','" + entity.getColor2() + "')";
            st.executeUpdate("INSERT INTO musicautoch2 " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto2Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
