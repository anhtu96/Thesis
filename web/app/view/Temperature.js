Ext.define('myApp.view.Temperature', {
    extend: 'Ext.Container',
    requires: ['myApp.controller.TempController', 'myApp.global.Vars'],
    controller: 'temperature',
    xtype: 'temperatureView',
    id: 'tempView',
    height: '100%',
    layout: 'vbox',
    items: [{
            xtype: 'titlebar',
            title: 'Temperature',
            docked: 'top',
        }, {
            xtype: 'toolbar',
            title: 'Monitoring',
            height: 50,
        }, {
            xtype: 'grid',
            store: 'TempDisplay',
            height: 200,
            columns: [{
                text: '<b>Device\'s name</b>',
                dataIndex: 'devicename',
                flex: 3
            }, {
                text: '<b>Temperature (&#8451)</b>',
                dataIndex: 'temp',
                flex: 2
            }, {
                text: '<b>Humidity (%)</b>',
                dataIndex: 'humid',
                flex: 2
            }]
        }, {
            xtype: 'toolbar',
            title: 'Controlling',
            height: 50,
        }, {
            //     xtype: 'grid',
            //     store: 'TempControl',
            //     height: 300,
            //     columns: [{
            //         text: 'Name',
            //         dataIndex: 'devicename',
            //         flex: 1.5,
            //         editable: true
            //     }, {
            //         text: 'Auto',
            //         dataIndex: 'auto',
            //         flex: 0.5,
            //         cell: {
            //             xtype: 'widgetcell',
            //             widget: {
            //                 xtype: 'togglefield',
            //                 ui: 'action',
            //                 listeners: {
            //                     change: 'onToggleAuto',
            //                     dragchange: 'onAutoDrag'
            //                 }
            //             }
            //         }
            //     }, {
            //         text: 'Temp',
            //         dataIndex: 'tempset',
            //         flex: 1,
            //         cell: {
            //             xtype: 'widgetcell',
            //             widget: {
            //                 xtype: 'spinnerfield',
            //                 disabled: true,
            //                 value: 30,
            //                 minValue: 25,
            //                 maxValue: 35,
            //                 stepValue: 1,
            //                 width: '100%',
            //                 listeners: {
            //                     spin: 'spinTemp'
            //                 }
            //             }
            //         }
            //     }, {
            //         text: 'Humid',
            //         dataIndex: 'humidset',
            //         flex: 1,
            //         cell: {
            //             xtype: 'widgetcell',
            //             widget: {
            //                 xtype: 'spinnerfield',
            //                 disabled: true,
            //                 width: '100%',
            //                 value: 65,
            //                 minValue: 60,
            //                 maxValue: 75,
            //                 stepValue: 1,
            //                 listeners: {
            //                     spin: 'spinHumid'
            //                 }
            //             }
            //         }
            //     }, {
            //         text: 'Fan',
            //         flex: 0.5,
            //         dataIndex: 'fan',
            //         cell: {
            //             xtype: 'widgetcell',
            //             widget: {
            //                 xtype: 'togglefield',
            //                 ui: 'action',
            //                 listeners: {
            //                     dragchange: 'toggleFanDrag',
            //                     change: 'toggleFanChange'
            //                 }
            //             }
            //         }
            //     }, {
            //         text: 'Fan speed',
            //         flex: 1.5,
            //         dataIndex: 'fanspeed',
            //         cell: {
            //             xtype: 'widgetcell',
            //             widget: {
            //                 xtype: 'sliderfield',
            //                 disabled: true,
            //                 width: '80%',
            //                 ui: 'action',
            //                 minValue: 1,
            //                 maxValue: 3,
            //                 listeners: {
            //                     dragchange: 'onFanSpeed'
            //                 }
            //             }
            //         }
            //     }, {
            //         text: 'Mist',
            //         flex: 0.5,
            //         dataIndex: 'mist',
            //         cell: {
            //             xtype: 'widgetcell',
            //             widget: {
            //                 xtype: 'togglefield',
            //                 ui: 'action',
            //                 listeners: {
            //                     dragchange: 'toggleMistDrag',
            //                     change: 'toggleMistChange'
            //                 }
            //             }
            //         }
            //     }, {
            //         text: 'Mist level',
            //         flex: 1.5,
            //         dataIndex: 'mistlevel',
            //         cell: {
            //             xtype: 'widgetcell',
            //             widget: {
            //                 xtype: 'sliderfield',
            //                 disabled: true,
            //                 width: '80%',
            //                 ui: 'action',
            //                 minValue: 1,
            //                 maxValue: 3,
            //                 listeners: {
            //                     dragchange: 'onMistLevel',
            //                 }
            //             }
            //         }
            //     }, ]
            //
            xtype: 'grid',
            rowLines: false,
            height: 200,
            store: 'TempControl',
            id: 'controlList',
            columns: [{
                text: '<b>Device</b>',
                dataIndex: 'devicename',
                flex: 1
            }, {
                text: '<b>Mode</b>',
                dataIndex: 'modestr',
                align: 'right',
                flex: 1
            }],
            listeners: {
                itemtap: 'controlTap'
            }
        },
        // {
        //     xtype: 'list',
        //     id: 'controlList',
        //     store: 'TempControl',
        //     itemTpl: new Ext.XTemplate('{devicename}',
        //         '<span style = "float:right">{modestr}</span>'),
        //     listeners: {
        //         itemtap: 'controlTap'
        //     }
        // }
    ],
})