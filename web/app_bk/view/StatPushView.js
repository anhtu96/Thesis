Ext.define('myApp.view.StatPushView', {
    extend: 'Ext.Panel',
    requires: ['myApp.controller.StatController'],
    controller: 'stat',
    height: '100%',
    items: [{
        xtype: 'toolbar',
        title: 'Measurement quantities'
    }, {
        xtype: 'list',
        id: 'quantityList',
        data: [{
            name: 'Temperature'
        }, {
            name: 'Humidity'
        }],
        itemTpl: '{name}',
        listeners: {
            select: 'quantityTap'
        }
    }]
})