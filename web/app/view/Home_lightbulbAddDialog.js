Ext.define('myApp.view.Home_lightbulbAddDialog', {
    extend: 'Ext.MessageBox',
    id: 'lightbulbAddDialog',
    requires: ['myApp.controller.HomeController'],
    controller: 'home',
    scrollable: true,

    title: 'Add a lightbulb',
    items: [{
        xtype: 'formpanel',
        height: 200,
        width: 700,
        scrollable: false,
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
        handler: 'saveLightbulb'
    }, {
        text: 'Cancel',
        handler: function() {
            Ext.getCmp('lightbulbAddDialog').hide();
        }
    }],
    listeners: {
        hide: function(messagebox) {
            messagebox.destroy();
            if (Ext.getCmp('lightbulbHomeList').getSelectionCount() > 0) {
                Ext.getCmp('lightbulbHomeList').deselectAll();
            }
        }
    }

})