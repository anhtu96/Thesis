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
import org.energy.model.MusicModeModel;

/**
 *
 * @author Le Duc Huy
 */
@Path("musicmode")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicModeResource extends BaseResource {

    private MysqlConfig mysql = new MysqlConfig();
    private Connection conn = mysql.getConn();

    @PermitAll
    @GET
    public ArrayList<MusicModeModel> getStudentRecord() {
        ArrayList<MusicModeModel> prod = new ArrayList<MusicModeModel>();
        System.out.println("hello GET mode");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from musicmode");
            while (rs.next()) {
                MusicModeModel pr = new MusicModeModel();
                pr.setId(rs.getInt("id"));
                pr.setState(rs.getString("state"));
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
        st.executeUpdate("DELETE FROM musicmode where id = " + id);
        st.close();
        return Response.noContent().build();
    }

    @PermitAll
    @Path("{id}")
    @PUT
    public Response doPut(@PathParam("id") long id, MusicModeModel entity) throws SQLException {
        System.out.println("helloput");
        Statement st = conn.createStatement();
        String sqlvalue = "UPDATE musicmode SET"
                + " state = '" + entity.getState() + "'"
                + " WHERE id = '" + id + "'";
        st.executeUpdate(sqlvalue);
        st.close();
        return null;
    }

    @PermitAll
    @POST
    public Response postStudentRecord(MusicModeModel entity) {
        System.out.println("hellopost");
        System.out.println(entity.getId());
        try {
            Statement st = conn.createStatement();
            String sqlvalue = "(id,state) VALUES ('" + entity.getId() + "','" + entity.getState() + "')";
            st.executeUpdate("INSERT INTO musicmode " + sqlvalue);
            st.close();
        } catch (SQLException ex) {

        }
        return null;
    }

}
