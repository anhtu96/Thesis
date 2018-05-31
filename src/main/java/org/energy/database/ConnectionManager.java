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
package org.energy.database;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.TimerTask;
import org.energy.Context;
import org.energy.GlobalTimer;
import org.energy.Protocol;
import org.energy.helper.Log;
import org.energy.model.Device;
import org.energy.model.Event;
import org.energy.model.Position;
import org.energy.model.TempDisplayModel;
import org.energy.model.TempSensorModel;
import org.energy.model.TempSensorHomeModel;

import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.energy.SocketConnected;
import org.energy.model.FlameHomeModel;
import org.energy.model.LightbulbHomeModel;
import org.energy.model.TempControlHomeModel;

public class ConnectionManager {

    private static final long DEFAULT_TIMEOUT = 600;

    private final long deviceTimeout;

    private final Map<Long, ActiveDevice> activeDevices = new HashMap<>();
    private final Map<Long, Set<UpdateListener>> listeners = new HashMap<>();
    private final Map<Long, Timeout> timeouts = new HashMap<>();

    public ConnectionManager() {
        deviceTimeout = Context.getConfig().getLong("status.timeout", DEFAULT_TIMEOUT) * 1000;
    }

    /*public void addActiveDevice(long deviceId, Protocol protocol, Channel channel, SocketAddress remoteAddress) {
        activeDevices.put(deviceId, new ActiveDevice(deviceId, protocol, channel, remoteAddress));
    }

    public void removeActiveDevice(Channel channel) {
        for (ActiveDevice activeDevice : activeDevices.values()) {
            if (activeDevice.getChannel() == channel) {
                updateDevice(activeDevice.getDeviceId(), Device.STATUS_OFFLINE, null);
                activeDevices.remove(activeDevice.getDeviceId());
                break;
            }
        }
    }*/
    public ActiveDevice getActiveDevice(long deviceId) {
        return activeDevices.get(deviceId);
    }

    public void updateDevice(final long deviceId, String status, Date time) {
        Device device = Context.getIdentityManager().getDeviceById(deviceId);
        if (device == null) {
            return;
        }

        if (!status.equals(device.getStatus())) {
            Event event = new Event(Event.TYPE_DEVICE_OFFLINE, deviceId);
            if (status.equals(Device.STATUS_ONLINE)) {
                event.setType(Event.TYPE_DEVICE_ONLINE);
            }
//            if (Context.getNotificationManager() != null) {
//                Context.getNotificationManager().updateEvent(event, null);
//            }
        }
        device.setStatus(status);

        Timeout timeout = timeouts.remove(deviceId);
        if (timeout != null) {
            timeout.cancel();
        }

        if (time != null) {
            device.setLastUpdate(time);
        }

        if (status.equals(Device.STATUS_ONLINE)) {
            timeouts.put(deviceId, GlobalTimer.getTimer().newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    if (!timeout.isCancelled()) {
                        updateDevice(deviceId, Device.STATUS_UNKNOWN, null);
                    }
                }
            }, deviceTimeout, TimeUnit.MILLISECONDS));
        }

        /*try {
            Context.getDeviceManager().updateDeviceStatus(device);
        } catch (SQLException error) {
            Log.warning(error);
        }*/
        updateDevice(device);
    }

    public synchronized void updateDevice(Device device) {
        /*for (long userId : Context.getPermissionsManager().getDeviceUsers(device.getId())) {
            if (listeners.containsKey(userId)) {
                for (UpdateListener listener : listeners.get(userId)) {
                    listener.onUpdateDevice(device);
                }
            }
        }*/
    }

    public synchronized void updatePosition(Position position) {
        long deviceId = position.getDeviceId();

        /*for (long userId : Context.getPermissionsManager().getDeviceUsers(deviceId)) {
            if (listeners.containsKey(userId)) {
                for (UpdateListener listener : listeners.get(userId)) {
                    listener.onUpdatePosition(position);
                }
            }
        }*/
    }

    public synchronized void updateTempDisplay(TempDisplayModel tempdisplay) {
//        long deviceId = tempdisplay.getDeviceId();
        long userId = 1;
        SocketConnected.setStatus(false);
        //for (long userId : Context.getPermissionsManager().getDeviceUsers(deviceId)) {
        if (listeners.containsKey(userId)) {
            for (UpdateListener listener : listeners.get(userId)) {
                SocketConnected.setStatus(true);
                listener.onUpdateTempDisplay(tempdisplay);
            }
        }
        //}
    }

    public synchronized void updateTempSensor(TempSensorModel tempsensor) {
//        long deviceId = position.getDeviceId();
        long userId = 1;
        SocketConnected.setStatus(false);
        //for (long userId : Context.getPermissionsManager().getDeviceUsers(deviceId)) {
        if (listeners.containsKey(userId)) {
            for (UpdateListener listener : listeners.get(userId)) {
                SocketConnected.setStatus(true);
                listener.onUpdateTempSensor(tempsensor);
            }
        }
//        }
    }

    public synchronized void updateTempSensorHome(TempSensorHomeModel tempsensorhome) {
        long userId = 1;
        SocketConnected.setStatus(false);
        if (listeners.containsKey(userId)) {
            for (UpdateListener listener : listeners.get(userId)) {
                SocketConnected.setStatus(true);
                listener.onUpdateTempSensorHome(tempsensorhome);
            }
        }
    }

    public synchronized void updateTempControlHome(TempControlHomeModel tempcontrolhome) {
        long userId = 1;
        SocketConnected.setStatus(false);
        if (listeners.containsKey(userId)) {
            for (UpdateListener listener : listeners.get(userId)) {
                SocketConnected.setStatus(true);
                listener.onUpdateTempControlHome(tempcontrolhome);
            }
        }
    }

    public synchronized void updateLightbulbHome(LightbulbHomeModel lightbulbhome) {
        long userId = 1;
        SocketConnected.setStatus(false);
        if (listeners.containsKey(userId)) {
            for (UpdateListener listener : listeners.get(userId)) {
                SocketConnected.setStatus(true);
                listener.onUpdateLightbulbHome(lightbulbhome);
            }
        }
    }

    public synchronized void updateFlameHome(FlameHomeModel flamehome) {
        long userId = 1;
        SocketConnected.setStatus(false);
        if (listeners.containsKey(userId)) {
            for (UpdateListener listener : listeners.get(userId)) {
                SocketConnected.setStatus(true);
                listener.onUpdateFlameHome(flamehome);
            }
        }
    }

    public synchronized void updateEvent(long userId, Event event, Position position) {
        if (listeners.containsKey(userId)) {
            for (UpdateListener listener : listeners.get(userId)) {
                listener.onUpdateEvent(event, position);

            }
        }
    }

    public interface UpdateListener {

        void onUpdateDevice(Device device);

        void onUpdatePosition(Position position);

        void onUpdateEvent(Event event, Position position);

        void onUpdateTempDisplay(TempDisplayModel tempdisplay);

        void onUpdateTempSensor(TempSensorModel tempsensor);

        void onUpdateTempSensorHome(TempSensorHomeModel tempsensorhome);

        void onUpdateTempControlHome(TempControlHomeModel tempcontrolhome);

        void onUpdateLightbulbHome(LightbulbHomeModel lightbulbhome);

        void onUpdateFlameHome(FlameHomeModel flamehome);
    }

    public synchronized void addListener(long userId, UpdateListener listener) {
        if (!listeners.containsKey(userId)) {
            listeners.put(userId, new HashSet<UpdateListener>());
        }
        listeners.get(userId).add(listener);
    }

    public synchronized void removeListener(long userId, UpdateListener listener) {
        if (!listeners.containsKey(userId)) {
            listeners.put(userId, new HashSet<UpdateListener>());
        }
        listeners.get(userId).remove(listener);
    }

}
