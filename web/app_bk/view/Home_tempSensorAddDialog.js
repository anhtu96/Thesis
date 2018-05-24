Ext.define('myApp.view.Home_tempSensorAddDialog', {
    extend: 'Ext.MessageBox',
    id: 'tempSensorAddDialog',
    requires: ['myApp.controller.HomeController'],
    controller: 'home',
    title: 'Add a sensor',
    scrollable: true,
    items: [{
        xtype: 'formpanel',
        scrollable: false,
        height: 200,
        width: 700,
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
        handler: 'saveTempSensor'
    }, {
        text: 'Cancel',
        handler: function() {
            Ext.getCmp('tempSensorAddDialog').hide();
        }
    }],
    listeners: {
        hide: function(messagebox) {
            messagebox.destroy();
            if (Ext.getCmp('tempSensorList').getSelectionCount() > 0) {
                Ext.getCmp('tempSensorList').deselectAll();
            }
        }
    }

})