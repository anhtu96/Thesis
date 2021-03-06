/*
 * Copyright 2015 Anton Tananaev (anton.tananaev@gmail.com)
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

import org.energy.Context;
import org.energy.api.BaseResource;
import org.energy.model.User;

import javax.annotation.security.PermitAll;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("session")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class SessionResource extends BaseResource {

    public static final String USER_ID_KEY = "userId";
    public static final String USER_COOKIE_KEY = "user";
    public static final String PASS_COOKIE_KEY = "password";

    @javax.ws.rs.core.Context
    private HttpServletRequest request;

    @PermitAll
    @GET
    public User get() throws SQLException {
        Long userId = (Long) request.getSession().getAttribute(USER_ID_KEY);
        if (userId == null) {
            Cookie[] cookies = request.getCookies();
            String email = null, password = null;
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals(USER_COOKIE_KEY)) {
                        email = cookies[i].getValue();
                    }
                    if (cookies[i].getName().equals(PASS_COOKIE_KEY)) {
                        password = cookies[i].getValue();
                    }
                }
            }
            /*if (email != null && password != null) {
                User user = Context.getDataManager().login(email, password);
                if (user != null) {
                    userId = user.getId();
                    request.getSession().setAttribute(USER_ID_KEY, userId);
                }
            }*/
        }

        if (userId != null) {
 //           return Context.getPermissionsManager().getUser(userId);
            return null;
        } else {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }
    }

    @PermitAll
    @POST
    public User add(
            @FormParam("email") String email, @FormParam("password") String password) throws SQLException {
        /*User user = Context.getPermissionsManager().login(email, password);
        if (user != null) {
            request.getSession().setAttribute(USER_ID_KEY, user.getId());
            return user;
        } else {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).build());
        }*/
        return null;
    }

    @DELETE
    public Response remove() {
        request.getSession().removeAttribute(USER_ID_KEY);
        return Response.noContent().build();
    }

}
