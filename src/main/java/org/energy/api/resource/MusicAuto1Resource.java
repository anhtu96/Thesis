/*
 * Copyright 2015 - 2017 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.energy.api.resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.jetty.http.HttpTester;
import org.energy.MysqlConfig;
import org.energy.PlayMusicAuto1;
import org.energy.api.BaseResource;
//import org.traccar.model.Device;
import org.energy.model.MusicAuto1Model;
import org.joda.time.DateTime;

/**
 *
 * @author Le Duc Huy
 */
@Path("musicauto1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicAuto1Resource extends BaseResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<MusicAuto1Model> getStudentRecord() {
        ArrayList<MusicAuto1Model> prod = new ArrayList<MusicAuto1Model>();
        System.out.println("GET MUSICAUTO 1");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from musicautoch1");
            while (rs.next()) {
                MusicAuto1Model pr = new MusicAuto1Model();
                pr.setId(rs.getInt("id"));
                pr.setName(rs.getString("name"));
                pr.setHour(rs.getString("hour"));
                pr.setMin(rs.getString("min"));
                pr.setPeriod(rs.getString("period"));
                pr.setState(rs.getInt("state"));
                prod.add(pr);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prod;
    }

    @PermitAll
    @Path("{id}")
    @DELETE
    public Response remove(@PathParam("id") long id) {
        try {
            System.out.println("DEL MUSICAUTO 1");
            if (PlayMusicAuto1.clip.isOpen()) {
                PlayMusicAuto1.clip.close();
                PlayMusicAuto1.rs.close();
                PlayMusicAuto1.currentSong = "";
                PlayMusicAuto1.songTmp = "";

            }
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM musicautoch1 where id = " + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, MusicAuto1Model entity) {
        try {
            System.out.println("PUT MUSICAUTO 1");
            if (PlayMusicAuto1.clip.isOpen()) {
                PlayMusicAuto1.clip.close();
                PlayMusicAuto1.rs.close();
                PlayMusicAuto1.currentSong = "";
                PlayMusicAuto1.songTmp = "";

            }
            Statement st = conn.createStatement();
            String sqlvalue = "UPDATE musicautoch1 SET"
                    + " name = '" + entity.getName() + "'"
                    + ", hour = '" + entity.getHour() + "'"
                    + ", min = '" + entity.getMin() + "'"
                    + ", period = '" + entity.getPeriod() + "'"
                    + ", state = '" + entity.getState() + "'"
                    + " WHERE id = '" + id + "'";
            System.out.println(sqlvalue);
            st.executeUpdate(sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(MusicAuto1Model entity) {
        System.out.println("POST MUSICAUTO 1");
        try {
            System.out.println("heeeeh");
            if (PlayMusicAuto1.clip.isOpen()) {
                PlayMusicAuto1.clip.close();
                PlayMusicAuto1.rs.close();
                PlayMusicAuto1.currentSong = "";
                PlayMusicAuto1.songTmp = "";

            }

            Statement st = conn.createStatement();

            String sqlvalue = "(id,name,hour,min,period,state) VALUES ('" + entity.getId() + "','" + entity.getName() + "','" + entity.getHour() + "','" + entity.getMin() + "','" + entity.getPeriod() + "','" + entity.getState() + "')";
            System.out.println("INSERT INTO musicautoch1 " + sqlvalue);
            st.executeUpdate("INSERT INTO musicautoch1 " + sqlvalue);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(MusicAuto1Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
