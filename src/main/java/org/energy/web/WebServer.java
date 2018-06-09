/*
 * Copyright 2012 - 2016 Anton Tananaev (anton.tananaev@gmail.com)
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
package org.energy.web;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.energy.Config;
import org.energy.api.AsyncSocketServlet;
import org.energy.api.CorsResponseFilter;
import org.energy.api.ObjectMapperProvider;
import org.energy.api.ResourceErrorHandler;
import org.energy.api.SecurityRequestFilter;
import org.energy.api.resource.CommandResource;
//import org.energy.api.resource.GroupPermissionResource;
import org.energy.api.resource.ServerResource;
import org.energy.api.resource.SessionResource;
//import org.energy.api.resource.DevicePermissionResource;
import org.energy.api.resource.UserResource;
import org.energy.api.resource.GroupResource;
//import org.energy.api.resource.NotificationResource;
import org.energy.api.resource.DeviceResource;
import org.energy.api.resource.PositionResource;
/*import org.energy.api.resource.ReportResource;
import org.energy.api.resource.CommandTypeResource;
import org.energy.api.resource.DeviceGeofenceResource;
import org.energy.api.resource.EventResource;
import org.energy.api.resource.GeofencePermissionResource;
import org.energy.api.resource.GeofenceResource;
import org.energy.api.resource.GroupGeofenceResource;*/
import org.energy.helper.Log;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.net.InetSocketAddress;
import org.energy.api.resource.EmailResource;
import org.energy.api.resource.FlameHomeResource;
import org.energy.api.resource.FlameResource;
import org.energy.api.resource.FloorResource;
import org.energy.api.resource.LightbulbAutoResource;
import org.energy.api.resource.LightbulbHomeResource;
import org.energy.api.resource.MusicAuto1Resource;
import org.energy.api.resource.MusicAuto2Resource;
import org.energy.api.resource.MusicManual1Resource;
import org.energy.api.resource.MusicManual2Resource;
import org.energy.api.resource.MusicModeResource;
import org.energy.api.resource.TempControlHomeResource;
import org.energy.api.resource.TempSensorHomeResource;
import org.energy.api.resource.TempDisplayResource;
import org.energy.api.resource.TempSensorResource;
import org.energy.api.resource.TempControlResource;

public class WebServer {

    private Server server;
    private final Config config;
    private final DataSource dataSource;
    private final HandlerList handlers = new HandlerList();
    private final SessionManager sessionManager;

    private void initServer() {
        System.out.println("Hello test");
        String address = config.getString("web.address");
        int port = config.getInteger("web.port", 9090);
        if (address == null) {
            server = new Server(port);
        } else {
            server = new Server(new InetSocketAddress(address, port));
        }
    }

    public WebServer(Config config, DataSource dataSource) {

        this.config = config;
        this.dataSource = dataSource;

        sessionManager = new HashSessionManager();
        int sessionTimeout = config.getInteger("web.sessionTimeout");
        if (sessionTimeout != 0) {
            sessionManager.setMaxInactiveInterval(sessionTimeout);
        }

        initServer();
        initApi();
        if (config.getBoolean("web.console")) {
            initConsole();
        }
        switch (config.getString("web.type", "new")) {
            case "old":
                initOldWebApp();
                break;
            default:
                initWebApp();
                break;
        }
        server.setHandler(handlers);

        server.addBean(new ErrorHandler() {
            @Override
            protected void handleErrorPage(
                    HttpServletRequest request, Writer writer, int code, String message) throws IOException {
                writer.write("<!DOCTYPE<html><head><title>Error</title></head><html><body>"
                        + code + " - " + HttpStatus.getMessage(code) + "</body></html>");
            }
        }, false);
    }

    private void initWebApp() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(config.getString("web.path"));
        if (config.getBoolean("web.debug")) {
            resourceHandler.setWelcomeFiles(new String[]{"debug.html"});
            //Troubleshooting Locked UI Files on Windows while app is running (like html, js, css, etc...),
            //you can make changes to the UI Files and refresh the page in the browser without stopping the app first
            resourceHandler.setMinMemoryMappedContentLength(-1);
        } else {
            resourceHandler.setWelcomeFiles(new String[]{"release.html", "index.html"});
        }
        handlers.addHandler(resourceHandler);
    }

    private void initOldWebApp() {
        try {
            javax.naming.Context context = new InitialContext();
            context.bind("java:/DefaultDS", dataSource);
        } catch (Exception error) {
            Log.warning(error);
        }

        WebAppContext app = new WebAppContext();
        app.setContextPath("/");
        app.getSessionHandler().setSessionManager(sessionManager);
        app.setWar(config.getString("web.application"));
        handlers.addHandler(app);
    }

    private void initApi() {

        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.setContextPath("/api");
        servletHandler.getSessionHandler().setSessionManager(sessionManager);

        servletHandler.addServlet(new ServletHolder(new AsyncSocketServlet()), "/socket");

        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(ObjectMapperProvider.class);
        resourceConfig.register(JacksonFeature.class);
        resourceConfig.register(ResourceErrorHandler.class);
        resourceConfig.register(SecurityRequestFilter.class);
        resourceConfig.register(CorsResponseFilter.class);
        resourceConfig.registerClasses(ServerResource.class, SessionResource.class, CommandResource.class,
                UserResource.class,
                GroupResource.class, DeviceResource.class, PositionResource.class,
                //            CommandTypeResource.class, EventResource.class, GeofenceResource.class,
                //            DeviceGeofenceResource.class, GeofencePermissionResource.class, GroupGeofenceResource.class,
                //            NotificationResource.class, ReportResource.class

                MusicAuto1Resource.class,
                MusicAuto2Resource.class,
                MusicManual1Resource.class,
                MusicManual2Resource.class,
                MusicModeResource.class,
                TempSensorHomeResource.class,
                TempSensorResource.class,
                TempDisplayResource.class,
                TempControlHomeResource.class,
                TempControlResource.class,
                LightbulbHomeResource.class,
                LightbulbAutoResource.class,
                FloorResource.class,
                FlameHomeResource.class,
                FlameResource.class,
                EmailResource.class);
        servletHandler.addServlet(new ServletHolder(new ServletContainer(resourceConfig)), "/*");

        handlers.addHandler(servletHandler);
        System.out.println("Hello initApi");

    }

    private void initConsole() {
        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.setContextPath("/console");
        servletHandler.addServlet(new ServletHolder(new ConsoleServlet()), "/*");
        handlers.addHandler(servletHandler);
    }

    public void start() {
        try {
            server.start();
        } catch (Exception error) {
            System.out.println("Hello error: ----------------------------------------");
            System.out.println(error);
            Log.warning(error);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception error) {
            Log.warning(error);
        }
    }

}
