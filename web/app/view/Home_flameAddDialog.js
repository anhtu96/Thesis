Ext.define('myApp.view.Home_flameAddDialog', {
    extend: 'Ext.MessageBox',
    id: 'flameAddDialog',
    requires: ['myApp.controller.HomeController'],
    controller: 'home',
    scrollable: true,

    title: 'Add a flame sensor',
    items: [{
        xtype: 'formpanel',
        scrollable: true,
        items: [{
            xtype: 'textfield',
            name: 'deviceid',
            label: 'Device\'s ID',
        }, {
            xtype: 'textfield',
            name: 'devicename',
            label: 'Device\'s name'
        }, {
            xtype: 'selectfield',
            name: 'floor',
            label: 'Floor',
            options: [{
                text: '1',
                value: 1
            }, {
                text: '2',
                value: 2
            }, {
                text: '3',
                value: 3
            }]
        }],
    }],
    buttons: [{
        text: 'Save',
        ui: 'confirm',
        handler: 'saveFlame'
    }, {
        text: 'Cancel',
        handler: function() {
            Ext.getCmp('flameAddDialog').hide();
        }
    }],
    listeners: {
        hide: function(messagebox) {
            messagebox.destroy();
            if (Ext.getCmp('flameHomeList').getSelectionCount() > 0) {
                Ext.getCmp('flameHomeList').deselectAll();
            }
        }
    }

})