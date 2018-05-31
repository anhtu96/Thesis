/*
 * Copyright 2015 - 2016 Anton Tananaev (anton.tananaev@gmail.com)
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
package org.energy.api;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.energy.Context;
import org.energy.database.ConnectionManager;
import org.energy.model.Device;
import org.energy.model.Event;
import org.energy.model.Position;
import org.energy.model.TempDisplayModel;
import org.energy.model.TempSensorModel;
import org.energy.model.TempSensorHomeModel;
import org.energy.web.JsonConverter;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.energy.model.FlameHomeModel;
import org.energy.model.FlameModel;
import org.energy.model.LightbulbHomeModel;
import org.energy.model.TempControlHomeModel;

public class AsyncSocket extends WebSocketAdapter implements ConnectionManager.UpdateListener {

    private static final String KEY_DEVICES = "devices";
    private static final String KEY_POSITIONS = "positions";
    private static final String KEY_EVENTS = "events";
    private static final String KEY_TEMPSENSOR = "tempsensor";
    private static final String KEY_TEMPSENSORHOME = "tempsensorhome";
    private static final String KEY_TEMPDISPLAY = "tempdisplay";
    private static final String KEY_TEMPCONTROLHOME = "tempcontrolhome";
    private static final String KEY_LIGHTBULBHOME = "lightbulbhome";
    private static final String KEY_FLAMEHOME = "flamehome";
    private static final String KEY_FLAME = "flame";

    private long userId;

    public AsyncSocket(long userId) {
        this.userId = userId;
    }

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);

        Map<String, Collection<?>> data = new HashMap<>();
        //       data.put(KEY_POSITIONS, Context.getDeviceManager().getInitialState(userId));
        sendData(data);

        Context.getConnectionManager().addListener(userId, this);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);

        Context.getConnectionManager().removeListener(userId, this);
    }

    @Override
    public void onUpdateDevice(Device device) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_DEVICES, Collections.singletonList(device));
        sendData(data);
    }

    @Override
    public void onUpdatePosition(Position position) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_POSITIONS, Collections.singletonList(position));
        sendData(data);
    }

    @Override
    public void onUpdateTempSensor(TempSensorModel tempsensor) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_TEMPSENSOR, Collections.singletonList(tempsensor));
        sendData(data);
    }

    @Override
    public void onUpdateTempSensorHome(TempSensorHomeModel tempsensorhome) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_TEMPSENSORHOME, Collections.singletonList(tempsensorhome));
        sendData(data);
    }

    @Override
    public void onUpdateTempDisplay(TempDisplayModel tempdisplay) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_TEMPDISPLAY, Collections.singletonList(tempdisplay));
        sendData(data);
    }

    @Override
    public void onUpdateTempControlHome(TempControlHomeModel tempcontrolhome) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_TEMPCONTROLHOME, Collections.singletonList(tempcontrolhome));
        sendData(data);
    }

    @Override
    public void onUpdateLightbulbHome(LightbulbHomeModel lightbulbhome) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_LIGHTBULBHOME, Collections.singletonList(lightbulbhome));
        sendData(data);
    }

    @Override
    public void onUpdateFlameHome(FlameHomeModel flamehome) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_FLAMEHOME, Collections.singletonList(flamehome));
        sendData(data);
    }

    @Override
    public void onUpdateFlame(FlameModel flame) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_FLAME, Collections.singletonList(flame));
        sendData(data);
    }

    @Override
    public void onUpdateEvent(Event event, Position position) {
        Map<String, Collection<?>> data = new HashMap<>();
        data.put(KEY_EVENTS, Collections.singletonList(event));
        if (position != null) {
            data.put(KEY_POSITIONS, Collections.singletonList(position));
        }
        sendData(data);
    }

    private void sendData(Map<String, Collection<?>> data) {
        if (!data.isEmpty() && isConnected()) {
            JsonObjectBuilder json = Json.createObjectBuilder();
            for (Map.Entry<String, Collection<?>> entry : data.entrySet()) {
                json.add(entry.getKey(), JsonConverter.arrayToJson(entry.getValue()));
            }
            getRemote().sendString(json.build().toString(), null);
        }
    }
}
