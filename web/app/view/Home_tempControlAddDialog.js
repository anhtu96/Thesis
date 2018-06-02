Ext.define('myApp.view.Home_tempControlAddDialog', {
    extend: 'Ext.MessageBox',
    id: 'tempControlAddDialog',
    requires: ['myApp.controller.HomeController'],
    controller: 'home',
    title: 'Add a device',
    scrollable: true,
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
        handler: 'saveTempControl'
    }, {
        text: 'Cancel',
        handler: function() {
            Ext.getCmp('tempControlAddDialog').hide();
        }
    }],
    listeners: {
        hide: function(messagebox) {
            messagebox.destroy();
            if (Ext.getCmp('tempControlList').getSelectionCount() > 0) {
                Ext.getCmp('tempControlList').deselectAll();
            }
        }
    }

})