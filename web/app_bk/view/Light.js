Ext.define('myApp.view.Light', {
    extend: 'Ext.NavigationView',
    xtype: 'lightView',
    id: 'lightView',
    requires: ['myApp.controller.LightController'],
    controller: 'light',
    navigationBar: {
        docked: 'top',
        style: 'background:#2196F3;'
    },
    defaultBackButtonText: 'back',
    items: [{
        title: '<p style = color:white>Lighting</p>',
        items: [{
            xtype: 'toolbar',
            title: 'Lightbulbs list',
            style: 'margin-top:15px'
        }, {
            xtype: 'list',
            id: 'lightbulbList',
            store: 'LightbulbHome',
            height: 200,
            itemTpl: new Ext.XTemplate('<b>{devicename}</b><span style = "float:right">{modestr}</span>'),
            listeners: {
                itemtap: 'tapList'
            }
        }]
    }],
    listeners: {
        pop: function() {
            Ext.getCmp('mainPanel').getTabBar().show();
            Ext.getStore('LightbulbAuto').clearFilter();
            Ext.getCmp('lightbulbList').deselectAll();
        },
        push: 'pushLight'
    }
})