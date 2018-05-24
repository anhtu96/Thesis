Ext.define('myApp.view.Main', {
    extend: 'Ext.tab.Panel',
    xtype: 'mainView',
    requires: ['myApp.view.Testview'],
    id: 'mainPanel',
    tabBarPosition: 'bottom',
    height: '100%',
    scrollable: false,
    // tabBar: {
    //     docked: 'bottom'
    // },
    items: [{
        title: 'Home',
        iconCls: 'home-icon',
        xtype: 'homeView'
    }, {
        title: 'Temp',
        iconCls: 'temperature-icon',
        xtype: 'temperatureView'
    }, {
        title: 'Lighting',
        iconCls: 'light-icon',
        xtype: 'lightView'
    }, {
        title: 'Music',
        iconCls: 'music-icon',
        xtype: 'musicView'
    }, {
        title: 'Statistics',
        iconCls: 'stat-icon',
        xtype: 'stats'
    }]
});