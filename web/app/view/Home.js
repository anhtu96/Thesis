Ext.define('myApp.view.Home', {
    extend: 'Ext.Panel',
    xtype: 'homeView',
    id: 'homeView',
    requires: ['myApp.controller.HomeController'],
    controller: 'home',
    layout: 'card',
    listeners: {
        activeItemchange: 'activeItemChange'
    },
    items: [{
        xtype: 'panel',
        scrollable: true,
        items: [{
            docked: 'top',
            xtype: 'titlebar',
            title: 'Home',
            items: [{
                iconCls: 'x-fa fa-bars',
                align: 'right',
                handler: 'showMenu'
            }]
        }, {
            xtype: 'titlebar',
            title: 'Temperature & Humidity setup',
        }, {
            xtype: 'toolbar',
            title: 'Sensors',
            style: 'margin-top:10px',
            items: [{
                iconCls: 'x-fa fa-plus',
                handler: 'tempSensorAdd'
            }]
        }, {
            xtype: 'list',
            id: 'tempSensorList',
            // height: 100,
            store: 'TempSensorHome',
            itemTpl: new Ext.XTemplate(
                '<b>{devicename}</b>',
                '<span style = "float:right"><font color = "{color}">{state}</font></span>'
            ),
            listeners: {
                itemtap: 'tempSensorListTap',
                itemswipe: function(list, index, target, record, e, eOpts) {
                    var user = Ext.getStore('LocalSession').getAt(0).get('username');
                    if (user == 'admin') {
                        if (e.direction == "left") {
                            Ext.Msg.confirm('Delete item', 'Are you sure you want to delete this item?', function(value) {
                                if (value == 'yes') {
                                    var devicerecord = record;
                                    var store = Ext.getStore('TempSensorHome');
                                    store.remove(record);
                                    store.sync();
                                    var storesensor = Ext.getStore('TempSensor'),
                                        storedisplay = Ext.getStore('TempDisplay');
                                    storesensor.filter('deviceid', devicerecord.get('deviceid'));
                                    storesensor.removeAll();
                                    storedisplay.filter('deviceid', devicerecord.get('deviceid'));
                                    storedisplay.removeAll();
                                    storesensor.sync();
                                    storedisplay.sync();
                                    storesensor.clearFilter();
                                    storedisplay.clearFilter();
                                    // storesensor.each(function(record, id) {
                                    //     if (record.get('deviceid') == devicerecord.get('deviceid')) {
                                    //         storesensor.remove(record);
                                    //         storesensor.sync();
                                    //     }
                                    // });
                                }
                            })
                        }
                    } else {
                        Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
                    }
                }
            },
        }, {
            xtype: 'toolbar',
            title: 'Controlling devices',
            style: 'margin-top:5px',
            items: [{
                iconCls: 'x-fa fa-plus',
                handler: 'tempControlAdd'
            }]
        }, {
            xtype: 'list',
            id: 'tempControlList',
            // height: 100,
            style: 'margin-bottom:15px',
            store: 'TempControlHome',
            itemTpl: new Ext.XTemplate(
                '<b>{devicename}</b>',
                '<span style = "float:right"><font color = "{color}">{state}</font></span>'
            ),
            listeners: {
                itemtap: 'tempControlListTap',
                itemswipe: function(list, index, target, record, e, eOpts) {
                    var user = Ext.getStore('LocalSession').getAt(0).get('username');
                    if (user == 'admin') {
                        if (e.direction == "left") {
                            Ext.Msg.confirm('Delete item', 'Are you sure you want to delete this item?', function(value) {
                                if (value == 'yes') {
                                    var devicerecord = record;
                                    var store = Ext.getStore('TempControlHome');
                                    store.remove(record);
                                    store.sync();
                                    var storecontrol = Ext.getStore('TempControl');
                                    storecontrol.each(function(record, id) {
                                        if (record.get('deviceid') == devicerecord.get('deviceid')) {
                                            storecontrol.remove(record);
                                            storecontrol.sync();
                                        }
                                    });
                                }
                            })
                        }
                    } else {
                        Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
                    }
                }
            },
        }, {
            xtype: 'titlebar',
            title: 'Lighting setup',
            // height: 50
        }, {
            xtype: 'toolbar',
            title: 'Light bulbs',
            style: 'margin-top:10px',
            items: [{
                iconCls: 'x-fa fa-plus',
                handler: 'lightbulbAdd'
            }]
        }, {
            xtype: 'list',
            id: 'lightbulbHomeList',
            style: 'margin-bottom:15px',
            store: 'LightbulbHome',
            itemTpl: new Ext.XTemplate(
                '<b>{devicename}</b>',
                '<span style = "float:right"><font color = "{color}">{onlinestatus}</font></span>'
            ),
            listeners: {
                itemtap: 'lightbulbHomeListTap',
                itemswipe: function(list, index, target, record, e, eOpts) {
                    var user = Ext.getStore('LocalSession').getAt(0).get('username');
                    if (user == 'admin') {
                        if (e.direction == "left") {
                            Ext.Msg.confirm('Delete item', 'Are you sure you want to delete this item?', function(value) {
                                if (value == 'yes') {
                                    var devicerecord = record;
                                    var store = Ext.getStore('LightbulbHome'),
                                        storeAuto = Ext.getStore('LightbulbAuto');
                                    storeAuto.filter('deviceid', record.get('deviceid'));
                                    if (storeAuto.getCount() > 0) {
                                        storeAuto.removeAll();
                                        storeAuto.sync();
                                    };
                                    storeAuto.clearFilter();
                                    store.remove(record);
                                    store.sync();
                                    // var storecontrol = Ext.getStore('TempControl');
                                    // storecontrol.each(function(record, id) {
                                    //     if (record.get('deviceid') == devicerecord.get('deviceid')) {
                                    //         storecontrol.remove(record);
                                    //         storecontrol.sync();
                                    //     }
                                    // });
                                }
                            })
                        }
                    } else {
                        Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
                    }
                }

            }
        }, {
            xtype: 'titlebar',
            title: 'Flame detector setup',
            // height: 50
        }, {
            xtype: 'toolbar',
            title: 'Sensors',
            style: 'margin-top:10px',
            items: [{
                iconCls: 'x-fa fa-plus',
                handler: 'flameAdd'
            }]
        }, {
            xtype: 'list',
            id: 'flameHomeList',
            style: 'margin-bottom:15px',
            store: 'FlameHome',
            itemTpl: new Ext.XTemplate(
                '<b>{devicename}</b>',
                '<span style = "float:right"><font color = "{color}">{onlinestatus}</font></span>'
            ),
            listeners: {
                itemtap: 'flameHomeTap',
                itemswipe: function(list, index, target, record, e, eOpts) {
                    var user = Ext.getStore('LocalSession').getAt(0).get('username');
                    if (user == 'admin') {
                        if (e.direction == "left") {
                            Ext.Msg.confirm('Delete item', 'Are you sure you want to delete this item?', function(value) {
                                if (value == 'yes') {
                                    var devicerecord = record;
                                    var store = Ext.getStore('FlameHome');
                                    store.remove(record);
                                    store.sync();
                                }
                            })
                        }
                    } else {
                        Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
                    }
                }

            }
        }, {
            xtype: 'audio',
            id: 'fireaudio',
            hidden: true,
            loop: true
        }, {
            xtype: 'toolbar',
            title: 'E-mail accounts',
            style: 'margin-top:10px',
            items: [{
                iconCls: 'x-fa fa-cog',
                handler: 'mailAdd'
            }, {
                iconCls: 'x-fa fa-retweet',
                ui: 'decline',
                handler: function(button) {
                    var user = Ext.getStore('LocalSession').getAt(0).get('username');
                    if (user == 'admin') {
                        Ext.getStore('Email').removeAll();
                        Ext.getStore('Email').sync();
                        Ext.Msg.show({
                            title: 'Reset accounts',
                            message: 'All accounts have been removed!'
                        });
                    } else {
                        Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
                    }
                }
            }]
        }]
    }, {
        xtype: 'panel',
        id: 'floor',
        layout: 'vbox',
        items: [{
            docked: 'top',
            xtype: 'titlebar',
            title: 'Home',
            items: [{
                iconCls: 'x-fa fa-bars',
                align: 'right',
                handler: 'showMenu'
            }]
        }, {
            xtype: 'titlebar',
            title: 'Overview'
        }, {
            xtype: 'panel',
            layout: 'hbox',
            height: '100%',
            items: [{
                xtype: 'container',
                id: 'floor1',
                width: 0,
            }, {
                xtype: 'container',
                id: 'floor2',
                flex: 2.5
            }, {
                xtype: 'toolbar',
                layout: 'vbox',
                items: [{
                    docked: 'top',
                    xtype: 'titlebar',
                    title: '<font color=white>Diagram\'s properties</font>'
                }, {
                    xtype: 'container',
                    items: [{
                        xtype: 'selectfield',
                        id: 'floorsNumber',
                        label: 'Number of floors',
                        value: 0,
                        options: [{
                            text: '1',
                            value: 1
                        }, {
                            text: '2',
                            value: 2
                        }, {
                            text: '3',
                            value: 3
                        }],
                        listeners: {
                            change: 'changeFloorsNum'
                        }
                    }]
                }, {
                    xtype: 'container',
                    style: 'margin-top:10px',
                    items: [{
                        xtype: 'titlebar',
                        title: '<font color=white>Legends</font>',
                    }],
                    html: '<br><img src = "resources/img/tempsensor.png" style="width:20px"> Temperature sensor' +
                        '<br><img src = "resources/img/tempcontrol.png" style="width:20px"> Temperature controlling device' +
                        '<br><img src = "resources/img/light.png" style="width:20px"> Light bulb' +
                        '<br><img src = "resources/img/flame.png" style="width:20px"> Flame detector' +
                        '<br><br><img src = "resources/img/offline.png" style="width:20px"> Offline device'
                }],
                height: '80%',
                flex: 1
            }]
        }]
    }]
})