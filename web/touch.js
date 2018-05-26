Ext.application({
    name: 'myApp',
    extend: 'myApp.Touch',
    launch: function() {

        Ext.Loader.setPath('Ext', 'src');
        Ext.require('Ext.chart.*');
        Ext.require('Ext.draw.*');
        this.asyncUpdate(true);
    },
    asyncUpdate: function(first) {

        var protocol, socket, self = this;
        protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
        socket = new WebSocket(protocol + '//' + window.location.host + window.location.pathname + 'api/socket');

        socket.onclose = function(event) {
            self.asyncUpdate(false);
        };

        socket.onmessage = function(event) {
            var i, j, store, data, array, entity, device, typeKey, alarmKey, text, geofence;

            data = Ext.decode(event.data);




            if (data.tempsensor) {
                var id = 1;
                array = data.tempsensor;
                store = Ext.getStore('TempSensor');
                var record = Ext.create('myApp.model.TempSensor');
                if (store.getCount() > 0) {
                    id = store.getAt(store.getCount() - 1).get('id') + 1
                }
                record.set({
                    id: id,
                    deviceid: array[0].deviceid,
                    devicename: array[0].devicename,
                    sendtime: array[0].sendtime,
                    temp: array[0].temp,
                    humid: array[0].humid
                });
                store.add(record);
                store.sync();
            }

            if (data.tempsensorhome) {
                array = data.tempsensorhome;
                store = Ext.getStore('TempSensorHome');
                var record = store.findRecord('deviceid', array[0].deviceid, 0, false, false, true);
                record.set({
                    state: array[0].state,
                    color: array[0].color
                });
                if (Ext.getCmp('tempsensor-' + array[0].deviceid)) {
                    var btn = Ext.getCmp('tempsensor-' + array[0].deviceid);
                    btn.setIconCls((record.get('state') == 'online') ? 'tempsensor_onl' : 'tempsensor_off');
                    btn.setStyle({
                        'background': (record.get('state') == 'online') ? '#FED08C' : '#434343',
                        'color': (record.get('state') == 'online') ? '#633B01' : '#FED08C'
                    });
                }
                store.add(record);
                store.sync()
            }
            if (data.flamehome) {
                array = data.flamehome;
                store = Ext.getStore('FlameHome');
                var record = store.findRecord('deviceid', array[0].deviceid, 0, false, false, true);
                record.set({
                    onlinestatus: array[0].onlinestatus,
                    color: array[0].color
                });
                if (Ext.getCmp('flame-' + array[0].deviceid)) {
                    var btn = Ext.getCmp('flame-' + array[0].deviceid);
                    btn.setIconCls((record.get('onlinestatus') == 'online') ? 'flame_onl' : 'flame_off');
                    btn.setStyle({
                        'background': (record.get('onlinestatus') == 'online') ? '#FF7575' : '#434343',
                        'color': (record.get('onlinestatus') == 'online') ? '#8C0000' : '#FF7575',
                    });
                    if (array[0].state == 0) {
                        console.log(Ext.getCmp('task'));
                        var someFunction = function() {
                            var task = new Ext.util.DelayedTask(function(task) {
                                    console.log('a');
                                    someFunction.call(task)
                                },
                                id: 'task');
                            task.delay(1000);
                        };
                        someFunction();
                    }
                }
                if (array[0].state == 0) {
                    Ext.getCmp('fireaudio').play();
                    Ext.Msg.show({
                        title: 'Something burning',
                        message: 'Danger signal from Sensor',
                        buttons: [{
                            itemId: 'ok',
                            text: 'Dismiss',
                        }],
                        fn: function() {
                            Ext.getCmp('fireaudio').stop();
                        }
                    });
                }
                // if (Ext.getCmp('tempsensor-' + array[0].deviceid)) {
                //     var btn = Ext.getCmp('tempsensor-' + array[0].deviceid);
                //     btn.setIconCls((record.get('state') == 'online') ? 'tempsensor_onl' : 'tempsensor_off');
                //     btn.setStyle({
                //         'background': (record.get('state') == 'online') ? '#FED08C' : '#434343',
                //         'color': (record.get('state') == 'online') ? '#633B01' : '#FED08C'
                //     });
                // }
                store.add(record);
                store.sync()
            }

            if (data.tempcontrolhome) {
                array = data.tempcontrolhome;
                store = Ext.getStore('TempControlHome');
                var record = store.findRecord('deviceid', array[0].deviceid, 0, false, false, true);
                record.set({
                    state: array[0].state,
                    color: array[0].color
                });
                if (Ext.getCmp('tempcontrol-' + array[0].deviceid)) {
                    var btn = Ext.getCmp('tempcontrol-' + array[0].deviceid);
                    btn.setIconCls((record.get('state') == 'online') ? 'fan_onl' : 'fan_off');
                    btn.setStyle({
                        'background': (record.get('state') == 'online') ? '#91F38E' : '#434343',
                        'color': (record.get('state') == 'online') ? '#098532' : '#91F38E'
                    });
                }
                store.add(record);
                store.sync()
            }

            if (data.lightbulbhome) {
                array = data.lightbulbhome;
                store = Ext.getStore('LightbulbHome');
                var record = store.findRecord('deviceid', array[0].deviceid, 0, false, false, true);
                record.set({
                    onlinestatus: array[0].onlinestatus,
                    color: array[0].color
                });
                if (Ext.getCmp('light-' + array[0].deviceid)) {
                    var btn = Ext.getCmp('light-' + array[0].deviceid);
                    btn.setIconCls((record.get('onlinestatus') == 'online') ? 'light_onl' : 'light_off');
                    btn.setStyle({
                        'background': (record.get('onlinestatus') == 'online') ? 'cyan' : '#434343',
                        'color': (record.get('onlinestatus') == 'online') ? '#097381' : 'cyan'
                    });
                }
                store.add(record);
                store.sync()
            }

            if (data.tempdisplay) {
                array = data.tempdisplay;
                store = Ext.getStore('TempDisplay');
                var record,
                    id = 1;
                if (array[0].id == 0) {
                    record = Ext.create('myApp.model.TempDisplay');
                    if (store.getCount() > 0) {
                        id = store.getAt(store.getCount() - 1).get('id') + 1
                    }
                    record.set({
                        id: id,
                        devicename: array[0].devicename,
                        deviceid: array[0].deviceid,
                        temp: array[0].temp,
                        humid: array[0].humid
                    });
                    store.add(record);
                    store.sync();
                } else if (array[0].id == 1) {
                    record = store.findRecord('deviceid', array[0].deviceid, 0, false, false, true);
                    record.set({
                        devicename: array[0].devicename,
                        deviceid: array[0].deviceid,
                        temp: array[0].temp,
                        humid: array[0].humid
                    });
                    store.add(record);
                    store.sync();
                } else {
                    record = store.findRecord('deviceid', array[0].deviceid, 0, false, false, true);
                    store.remove(record);
                    store.sync()
                }

                // alert('aaa');
            }
        }
    }
})