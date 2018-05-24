Ext.define('myApp.controller.HomeController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.home',

    showMenu: function() {
        var menu = Ext.create('Ext.Menu', {
            width: '35%',
            title: 'HOME MENU',
            items: [{
                text: 'Set things up',
                iconCls: 'setting',
                handler: function(button) {
                    Ext.getCmp('homeView').setActiveItem(0);
                    button.up('menu').hide();
                }
            }, {
                text: 'Overview',
                iconCls: 'diagram',
                handler: function(button) {
                    Ext.getCmp('homeView').setActiveItem(1);
                    button.up('menu').hide();
                    // img.show();
                    // if (Ext.getCmp('draw') == null) {
                    //     var draw = Ext.create({
                    //         xtype: 'draw',
                    //         id: 'draw',
                    //         height: '100%',
                    //         width: '100%'
                    //     });
                    //     Ext.getCmp('floor2').add(draw);
                    //     var surface = Ext.getCmp('draw').getSurface(),
                    //         floor = Ext.getStore('Floor').getAt(0).get('num');
                    //     for (var i = 0; i < floor; i++) {
                    //         surface.add({
                    //             type: 'rect',
                    //             x: 0,
                    //             y: 100 * i + 2,
                    //             width: 300,
                    //             height: 100,
                    //             lineWidth: 2,
                    //             strokeStyle: '#000000',
                    //             fillStyle: '#FAFAFA'
                    //         });
                    //     }

                    //     surface.renderFrame();
                    // }
                }
            }]
        });
        Ext.Viewport.setMenu(menu, {
            side: 'right',
        });

        Ext.Viewport.toggleMenu('right');
    },

    changeFloorsNum: function(selectfield) {
        var storeTempSensor = Ext.getStore('TempSensorHome'),
            storeTempControl = Ext.getStore('TempControlHome'),
            storeLight = Ext.getStore('LightbulbHome'),
            storeFloor = Ext.getStore('Floor'),
            record = storeFloor.getAt(0),
            floor1 = Ext.getCmp('floor1'),
            floor2 = Ext.getCmp('floor2');
        floor1.removeAll();
        floor2.removeAll();
        record.set({
            num: selectfield.getValue()
        });
        storeFloor.add(record);
        storeFloor.sync();
        /*
         *
         * ======= ADD SENSORS' ICON ==========
         */

        /*
         *==========ADD HOUSE IMAGE=============
         *
         */
        var devCnt = 0;
        var floorNum = Ext.getStore('Floor').getAt(0).get('num');
        for (var i = floorNum; i >= 1; i--) {
            var devCntTmp = 0;
            storeTempSensor.each(function(record) {
                if (record.get('floor') == i) {
                    var btn = Ext.create('Ext.Button', {
                        iconCls: (record.get('state') == 'online') ? 'tempsensor_onl' : 'tempsensor_off',
                        id: 'tempsensor-' + record.get('deviceid'),
                        deviceid: record.get('deviceid'),
                        floor: record.get('floor'),
                        height: 32,
                        style: {
                            'background': (record.get('state') == 'online') ? '#FED08C' : '#434343',
                            'color': (record.get('state') == 'online') ? '#633B01' : '#FED08C'
                        },
                        text: '<b>' + record.get('devicename') + '</b>',
                        handler: function(button) {
                            var store = Ext.getStore('TempSensorHome');
                            store.filter('deviceid', button.deviceid);
                            if (Ext.getCmp('tip')) {
                                Ext.getCmp('tip').destroy();
                            }
                            var html = '<b><font size = 3>' + button.getText() + '</font></b>' +
                                '<br><b>&#9679 Status: </b>' + store.getAt(0).get('state') +
                                '<br><b>&#9679 Floor: </b>' + button.floor;
                            if (store.getAt(0).get('state') == 'online') {
                                var storeSensor = Ext.getStore('TempDisplay');
                                storeSensor.filter('deviceid', button.deviceid);
                                html += '<br><b>&#9679 Temperature: </b>' + storeSensor.getAt(0).get('temp') + '&#8451' +
                                    '<br><b>&#9679 Humidity: </b>' + storeSensor.getAt(0).get('humid') + '%';
                                storeSensor.clearFilter();
                            }
                            var tip = new Ext.tip.ToolTip({
                                id: 'tip',
                            });
                            tip.setHtml(html);
                            store.clearFilter();
                            tip.showBy(button);
                        }
                    });
                    btn.el.setY(130 * (floorNum - i) - 32 * (devCnt - devCntTmp));
                    devCnt++;
                    devCntTmp++;
                    floor1.add(btn)
                }

            });
            storeTempControl.each(function(record) {
                if (record.get('floor') == i) {
                    var btn = Ext.create('Ext.Button', {
                        iconCls: (record.get('state') == 'online') ? 'fan_onl' : 'fan_off',
                        id: 'tempcontrol-' + record.get('deviceid'),
                        deviceid: record.get('deviceid'),
                        floor: record.get('floor'),
                        height: 32,
                        style: {
                            'background': (record.get('state') == 'online') ? '#91F38E' : '#434343',
                            'color': (record.get('state') == 'online') ? '#098532' : '#91F38E'
                        },
                        text: '<b>' + record.get('devicename') + '</b>',
                        handler: function(button) {
                            var store = Ext.getStore('TempControlHome');
                            store.filter('deviceid', button.deviceid);
                            if (Ext.getCmp('tip')) {
                                Ext.getCmp('tip').destroy();
                            }
                            var html = '<b><font size = 3>' + button.getText() + '</font></b>' +
                                '<br><b>&#9679 Status: </b>' + store.getAt(0).get('state') +
                                '<br><b>&#9679 Floor: </b>' + button.floor;
                            if (store.getAt(0).get('state') == 'online') {
                                var storeControl = Ext.getStore('TempControl');
                                storeControl.filter('deviceid', button.deviceid);
                                var controlRecord = storeControl.getAt(0);
                                html += '<br><b>&#9679 Mode: </b>' + controlRecord.get('modestr');
                                if (controlRecord.get('modestr') == 'auto') {
                                    html += '<br><b>&#9679 Sensor: </b>' + controlRecord.get('sensor')
                                } else {
                                    var fanspeed = controlRecord.get('fanspeed');
                                    html += '<br><b>&#9679 Fan speed: </b>' + (fanspeed == 0 ? 'Off' : (fanspeed == 1 ? 'Low' : fanspeed == 2 ? 'Medium' : 'High')) +
                                        '<br><b>&#9679 Mist pump: </b>' + (controlRecord.get('mist') == 0 ? 'Off' : 'On');
                                    storeControl.clearFilter();
                                }
                            }
                            var tip = new Ext.tip.ToolTip({
                                id: 'tip',
                            });
                            tip.setHtml(html);
                            store.clearFilter();
                            tip.showBy(button);
                        }
                    });
                    btn.el.setY(130 * (floorNum - i) - 32 * (devCnt - devCntTmp));
                    devCnt++;
                    devCntTmp++;
                    floor1.add(btn);
                }
            });
            storeLight.each(function(record) {
                if (record.get('floor') == i) {
                    var btn = Ext.create('Ext.Button', {
                        id: 'light-' + record.get('deviceid'),
                        deviceid: record.get('deviceid'),
                        floor: record.get('floor'),
                        iconCls: (record.get('onlinestatus') == 'online') ? 'light_onl' : 'light_off',
                        height: 32,
                        style: {
                            'background': (record.get('onlinestatus') == 'online') ? 'cyan' : '#434343',
                            'color': (record.get('onlinestatus') == 'online') ? '#097381' : 'cyan'
                        },
                        text: '<b>' + record.get('devicename') + '</b>',
                        handler: function(button) {
                            var store = Ext.getStore('LightbulbHome');
                            store.filter('deviceid', button.deviceid);
                            if (Ext.getCmp('tip')) {
                                Ext.getCmp('tip').destroy();
                            }
                            var tip = new Ext.tip.ToolTip({
                                id: 'tip',
                                html: '<b><font size = 3>' + button.getText() + '</font></b>' +
                                    '<br><b>Status: </b>' + store.getAt(0).get('onlinestatus') +
                                    '<br><b>Floor: </b>' + button.floor
                            });
                            store.clearFilter();
                            tip.showBy(button);
                        }
                    });
                    btn.el.setY(130 * (floorNum - i) - 32 * (devCnt - devCntTmp));
                    devCnt++;
                    devCntTmp++;
                    floor1.add(btn);
                }

            });
            var img = Ext.create('Ext.Img', {
                src: 'resources/img/birdhouse.PNG',
                height: 130,
                width: 500
            });
            floor2.add(img);
            img.show();
        }

    },
    activeItemChange: function(panel, value) {
        if (panel.indexOf(panel.getActiveItem()) == 2) {
            if (Ext.getCmp('floorsNumber').getValue() > 0) {
                var devCnt = 0,
                    storeTempSensor = Ext.getStore('TempSensorHome'),
                    storeTempControl = Ext.getStore('TempControlHome'),
                    storeLight = Ext.getStore('LightbulbHome'),
                    floorNum = Ext.getStore('Floor').getAt(0).get('num'),
                    floor1 = Ext.getCmp('floor1'),
                    floor2 = Ext.getCmp('floor2');
                floor1.removeAll();
                floor2.removeAll();
                for (var i = floorNum; i >= 1; i--) {
                    var devCntTmp = 0;
                    storeTempSensor.each(function(record) {
                        if (record.get('floor') == i) {
                            var btn = Ext.create('Ext.Button', {
                                iconCls: (record.get('state') == 'online') ? 'tempsensor_onl' : 'tempsensor_off',
                                id: 'tempsensor-' + record.get('deviceid'),
                                deviceid: record.get('deviceid'),
                                floor: record.get('floor'),
                                height: 32,
                                style: {
                                    'background': (record.get('state') == 'online') ? '#FED08C' : '#434343',
                                    'color': (record.get('state') == 'online') ? '#633B01' : '#FED08C'
                                },
                                text: '<b>' + record.get('devicename') + '</b>',
                                handler: function(button) {
                                    var store = Ext.getStore('TempSensorHome');
                                    store.filter('deviceid', button.deviceid);
                                    if (Ext.getCmp('tip')) {
                                        Ext.getCmp('tip').destroy();
                                    }
                                    var html = '<b><font size = 3>' + button.getText() + '</font></b>' +
                                        '<br><b>&#9679 Status: </b>' + store.getAt(0).get('state') +
                                        '<br><b>&#9679 Floor: </b>' + button.floor;
                                    if (store.getAt(0).get('state') == 'online') {
                                        var storeSensor = Ext.getStore('TempDisplay');
                                        storeSensor.filter('deviceid', button.deviceid);
                                        html += '<br><b>&#9679 Temperature: </b>' + storeSensor.getAt(0).get('temp') + '&#8451' +
                                            '<br><b>&#9679 Humidity: </b>' + storeSensor.getAt(0).get('humid') + '%';
                                        storeSensor.clearFilter();
                                    }
                                    var tip = new Ext.tip.ToolTip({
                                        id: 'tip',
                                    });
                                    tip.setHtml(html);
                                    store.clearFilter();
                                    tip.showBy(button);
                                }
                            });
                            btn.el.setY(130 * (floorNum - i) - 32 * (devCnt - devCntTmp));
                            devCnt++;
                            devCntTmp++;
                            floor1.add(btn)
                        }

                    });
                    storeTempControl.each(function(record) {
                        if (record.get('floor') == i) {
                            var btn = Ext.create('Ext.Button', {
                                iconCls: (record.get('state') == 'online') ? 'fan_onl' : 'fan_off',
                                id: 'tempcontrol-' + record.get('deviceid'),
                                deviceid: record.get('deviceid'),
                                floor: record.get('floor'),
                                height: 32,
                                style: {
                                    'background': (record.get('state') == 'online') ? '#91F38E' : '#434343',
                                    'color': (record.get('state') == 'online') ? '#098532' : '#91F38E'
                                },
                                text: '<b>' + record.get('devicename') + '</b>',
                                handler: function(button) {
                                    var store = Ext.getStore('TempControlHome');
                                    store.filter('deviceid', button.deviceid);
                                    if (Ext.getCmp('tip')) {
                                        Ext.getCmp('tip').destroy();
                                    }
                                    var html = '<b><font size = 3>' + button.getText() + '</font></b>' +
                                        '<br><b>&#9679 Status: </b>' + store.getAt(0).get('state') +
                                        '<br><b>&#9679 Floor: </b>' + button.floor;
                                    if (store.getAt(0).get('state') == 'online') {
                                        var storeControl = Ext.getStore('TempControl');
                                        storeControl.filter('deviceid', button.deviceid);
                                        var controlRecord = storeControl.getAt(0);
                                        html += '<br><b>&#9679 Mode: </b>' + controlRecord.get('modestr');
                                        if (controlRecord.get('modestr') == 'auto') {
                                            html += '<br><b>&#9679 Sensor: </b>' + controlRecord.get('sensor')
                                        } else {
                                            var fanspeed = controlRecord.get('fanspeed');
                                            html += '<br><b>&#9679 Fan speed: </b>' + (fanspeed == 0 ? 'Off' : (fanspeed == 1 ? 'Low' : fanspeed == 2 ? 'Medium' : 'High')) +
                                                '<br><b>&#9679 Mist pump: </b>' + (controlRecord.get('mist') == 0 ? 'Off' : 'On');
                                            storeControl.clearFilter();
                                        }
                                    }
                                    var tip = new Ext.tip.ToolTip({
                                        id: 'tip',
                                    });
                                    tip.setHtml(html);
                                    store.clearFilter();
                                    tip.showBy(button);
                                }
                            });
                            btn.el.setY(130 * (floorNum - i) - 32 * (devCnt - devCntTmp));
                            devCnt++;
                            devCntTmp++;
                            floor1.add(btn);
                        }
                    });
                    storeLight.each(function(record) {
                        if (record.get('floor') == i) {
                            var btn = Ext.create('Ext.Button', {
                                id: 'light-' + record.get('deviceid'),
                                deviceid: record.get('deviceid'),
                                floor: record.get('floor'),
                                iconCls: (record.get('onlinestatus') == 'online') ? 'light_onl' : 'light_off',
                                height: 32,
                                style: {
                                    'background': (record.get('onlinestatus') == 'online') ? 'cyan' : '#434343',
                                    'color': (record.get('onlinestatus') == 'online') ? '#097381' : 'cyan'
                                },
                                text: '<b>' + record.get('devicename') + '</b>',
                                handler: function(button) {
                                    var store = Ext.getStore('LightbulbHome');
                                    store.filter('deviceid', button.deviceid);
                                    if (Ext.getCmp('tip')) {
                                        Ext.getCmp('tip').destroy();
                                    }
                                    var tip = new Ext.tip.ToolTip({
                                        id: 'tip',
                                        html: '<b><font size = 3>' + button.getText() + '</font></b>' +
                                            '<br><b>Status: </b>' + store.getAt(0).get('onlinestatus') +
                                            '<br><b>Floor: </b>' + button.floor
                                    });
                                    store.clearFilter();
                                    tip.showBy(button);
                                }
                            });
                            btn.el.setY(130 * (floorNum - i) - 32 * (devCnt - devCntTmp));
                            devCnt++;
                            devCntTmp++;
                            floor1.add(btn);
                        }

                    });
                    var img = Ext.create('Ext.Img', {
                        src: 'resources/img/birdhouse.PNG',
                        height: 130,
                        width: 500
                    });
                    floor2.add(img);
                    img.show();
                }
            } else {
                Ext.getCmp('floorsNumber').setValue(Ext.getStore('Floor').getAt(0).get('num'));
            }
        }
    },
    /*
     * add device
     */
    tempSensorAdd: function(button) {
        var msgbox = Ext.create('myApp.view.Home_tempSensorAddDialog');
        Ext.getCmp('homeView').add(msgbox);
        msgbox.show();
    },
    tempControlAdd: function(button) {
        var msgbox = Ext.create('myApp.view.Home_tempControlAddDialog');
        Ext.getCmp('homeView').add(msgbox);
        msgbox.show();
    },
    lightbulbAdd: function(button) {
        var msgbox = Ext.create('myApp.view.Home_lightbulbAddDialog');
        Ext.getCmp('homeView').add(msgbox);
        msgbox.show();
    },

    /*
     * tap device list
     */
    tempSensorListTap: function(list, index, target, record, e, eOpts) {
        var msgbox = Ext.create('myApp.view.Home_tempSensorAddDialog');
        Ext.getCmp('homeView').add(msgbox);
        msgbox.setTitle('Edit device');
        msgbox.show();
        msgbox.down('formpanel').setRecord(record);
    },
    tempControlListTap: function(list, index, target, record, e, eOpts) {
        var msgbox = Ext.create('myApp.view.Home_tempControlAddDialog');
        Ext.getCmp('homeView').add(msgbox);
        msgbox.setTitle('Edit device');
        msgbox.show();
        msgbox.down('formpanel').setRecord(record);
    },
    lightbulbHomeListTap: function(list, index, target, record, e, eOpts) {
        var msgbox = Ext.create('myApp.view.Home_lightbulbAddDialog');
        Ext.getCmp('homeView').add(msgbox);
        msgbox.setTitle('Edit lightbulb');
        msgbox.show();
        msgbox.down('formpanel').setRecord(record);
        Ext.getStore('LightbulbAuto').filter('deviceid', record.get('deviceid'));
    },

    /*
     * save device
     */
    saveTempSensor: function(button) {
        var store = Ext.getStore('TempSensorHome'),
            record = Ext.create('myApp.model.TempSensorHome'),
            check = 0,
            id = 1;
        if (Ext.getCmp('tempSensorAddDialog').down('formpanel').getRecord() != null) {
            record = Ext.getCmp('tempSensorAddDialog').down('formpanel').getRecord();
            id = record.get('id');
        } else {
            if (store.getCount() > 0) {
                id = store.getAt(store.getCount() - 1).get('id') + 1
            }
        }

        store.each(function(rec) {
            if (rec.get('deviceid') == Ext.getCmp('tempSensorAddDialog').down('formpanel').getFields('deviceid').getValue() && rec.get('id') != record.get('id')) {
                Ext.Msg.alert('Device already exists', 'Please pick another ID');
                check = 1;
                return;
            } else if (rec.get('devicename') == Ext.getCmp('tempSensorAddDialog').down('formpanel').getFields('devicename').getValue() && rec.get('id') != record.get('id')) {
                Ext.Msg.alert('Duplicate name', 'Please pick another name');
                check = 1;
                return;
            }
        });
        if (check == 0) {
            record.set({
                id: id,
                devicename: Ext.getCmp('tempSensorAddDialog').down('formpanel').getFields('devicename').getValue(),
                deviceid: Ext.getCmp('tempSensorAddDialog').down('formpanel').getFields('deviceid').getValue(),
                state: 'offline',
                color: 'red',
                floor: Ext.getCmp('tempSensorAddDialog').down('formpanel').getFields('floor').getValue(),
            });
            store.add(record);
            store.sync();
            Ext.getCmp('tempSensorAddDialog').hide();
        }
    },
    saveTempControl: function(button) {
        var store = Ext.getStore('TempControlHome'),
            record = Ext.create('myApp.model.TempControlHome'),
            check = 0,
            id = 1,
            tempControlRecord = Ext.create('myApp.model.TempControl'),
            tempControlStore = Ext.getStore('TempControl');
        if (Ext.getCmp('tempControlAddDialog').down('formpanel').getRecord() != null) {
            record = Ext.getCmp('tempControlAddDialog').down('formpanel').getRecord();
            id = record.get('id');
            tempControlRecord = tempControlStore.getById(id);
            tempControlRecord.set({
                deviceid: Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('deviceid').getValue(),
                devicename: Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('devicename').getValue(),
            });
            tempControlStore.add(tempControlRecord);
            tempControlStore.sync();
        } else {
            if (store.getCount() > 0) {
                id = store.getAt(store.getCount() - 1).get('id') + 1
            }
            tempControlRecord.set({
                id: id,
                devicename: Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('devicename').getValue(),
                deviceid: Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('deviceid').getValue(),
                modestr: 'Manual',
                tempset: 30,
                humidset: 60,
                mist: 0,
                fanspeed: 0,
            });
            tempControlStore.add(tempControlRecord);
            tempControlStore.sync();

        }
        console.log(tempControlRecord);
        store.each(function(rec) {
            if (rec.get('deviceid') == Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('deviceid').getValue() && rec.get('id') != record.get('id')) {
                Ext.Msg.alert('Device already exists', 'Please pick another ID');
                check = 1;
                return;
            } else if (rec.get('devicename') == Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('devicename').getValue() && rec.get('id') != record.get('id')) {
                Ext.Msg.alert('Duplicate name', 'Please pick another name');
                check = 1;
                return;
            }
        });
        if (check == 0) {
            record.set({
                id: id,
                devicename: Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('devicename').getValue(),
                deviceid: Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('deviceid').getValue(),
                state: 'offline',
                color: 'red',
                floor: Ext.getCmp('tempControlAddDialog').down('formpanel').getFields('floor').getValue(),
            });
            store.add(record);
            store.sync();
            Ext.getCmp('tempControlAddDialog').hide();
        }
    },
    saveLightbulb: function(button) {
        var store = Ext.getStore('LightbulbHome'),
            storeAuto = Ext.getStore('LightbulbAuto'),
            record = Ext.create('myApp.model.LightbulbHome'),
            check = 0,
            state = 0,
            modestr = 'manual',
            id = 1;
        if (Ext.getCmp('lightbulbAddDialog').down('formpanel').getRecord() != null) {
            record = Ext.getCmp('lightbulbAddDialog').down('formpanel').getRecord();
            id = record.get('id');
            modestr = record.get('modestr');
            state = record.get('state')
        } else {
            if (store.getCount() > 0) {
                id = store.getAt(store.getCount() - 1).get('id') + 1
            }
        }

        store.each(function(rec) {
            if (rec.get('deviceid') == Ext.getCmp('lightbulbAddDialog').down('formpanel').getFields('deviceid').getValue() && rec.get('id') != record.get('id')) {
                Ext.Msg.alert('Device already exists', 'Please pick another ID');
                check = 1;
                return;
            } else if (rec.get('devicename') == Ext.getCmp('lightbulbAddDialog').down('formpanel').getFields('devicename').getValue() && rec.get('id') != record.get('id')) {
                Ext.Msg.alert('Duplicate name', 'Please pick another name');
                check = 1;
                return;
            }
        });
        if (check == 0) {
            if (storeAuto.getCount() > 0) {
                storeAuto.each(function(rec) {
                    rec.set({
                        devicename: Ext.getCmp('lightbulbAddDialog').down('formpanel').getFields('devicename').getValue(),
                        deviceid: Ext.getCmp('lightbulbAddDialog').down('formpanel').getFields('deviceid').getValue(),
                    })
                });
                storeAuto.sync();
            }
            record.set({
                id: id,
                devicename: Ext.getCmp('lightbulbAddDialog').down('formpanel').getFields('devicename').getValue(),
                deviceid: Ext.getCmp('lightbulbAddDialog').down('formpanel').getFields('deviceid').getValue(),
                modestr: modestr,
                onlinestatus: 'offline',
                color: 'red',
                state: state,
                floor: Ext.getCmp('lightbulbAddDialog').down('formpanel').getFields('floor').getValue(),
            });
            store.add(record);
            store.sync();
            Ext.getCmp('lightbulbAddDialog').hide();
        }
    }
})