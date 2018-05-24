Ext.define('myApp.view.Light_pushView', {
    extend: 'Ext.Panel',
    requires: ['myApp.controller.Light_pushController'],
    controller: 'lightpush',
    id: 'lightpush',
    items: [{
        xtype: 'toolbar',
        items: [{
            xtype: 'togglefield',
            label: 'Auto/Manual',
            listeners: {
                change: 'changeMode'
            }
        }, {
            xtype: 'spacer'
        }]
    }, {
        xtype: 'panel',
        scrollable: true,
        height: '90%'
    }],
})