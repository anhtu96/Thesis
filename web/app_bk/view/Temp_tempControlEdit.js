Ext.define('myApp.view.Temp_tempControlEdit', {
    extend: 'Ext.Sheet',
    id: 'tempControlEdit',
    requires: ['myApp.controller.TempController'],
    controller: 'temperature',
    tools: [{
        xtype: 'button',
        text: 'close',
        handler: function(button) {
            button.up('sheet').hide();
            var task = new Ext.util.DelayedTask(function() {
                button.up('sheet').destroy();
            });
            Ext.getCmp('controlList').deselectAll();
            task.delay(500);
        }
    }],
    items: [{
        xtype: 'formpanel',
        scrollable: false,
        height: 400,
        width: 300,
        store: 'TempDisplay',
        items: [{
            xtype: 'togglefield',
            name: 'auto',
            label: 'Auto',
            listeners: {
                change: 'onToggleAuto',
                dragchange: 'onAutoDrag'
            }
        }, {
            xtype: 'selectfield',
            disabled: true,
            name: 'sensor',
            label: 'Sensor',
            store: 'TempSensorHome',
            displayField: 'devicename',
            valueField: 'devicename',
            listeners: {
                change: 'onSelectSensor'
            }
        }, {
            xtype: 'spinnerfield',
            disabled: true,
            name: 'tempset',
            label: 'Temp set',
            minValue: 25,
            maxValue: 35,
            stepValue: 1,
            listeners: {
                spin: 'spinTemp'
            }
        }, {
            xtype: 'spinnerfield',
            disabled: true,
            name: 'humidset',
            label: 'Humid set',
            minValue: 60,
            maxValue: 70,
            stepValue: 1,
            listeners: {
                spin: 'spinHumid'
            }
        }, {
            xtype: 'sliderfield',
            label: 'Fan speed',
            name: 'fanspeed',
            minValue: 0,
            maxValue: 3,
            listeners: {
                dragchange: 'onFanSpeed'
            }
        }, {
            xtype: 'togglefield',
            name: 'mist',
            label: 'Mist',
            listeners: {
                dragchange: 'onMist'
            }
        }],
    }],
    // buttons: [{
    //     text: 'Save',
    //     ui: 'confirm',
    //     handler: 'saveTempControl'
    // }, {
    //     text: 'Cancel',
    //     handler: function() {
    //         Ext.getCmp('tempControlAddDialog').hide();
    //     }
    // }],
    // listeners: {
    //     hide: function(messagebox) {
    //         messagebox.destroy();
    //         if (Ext.getCmp('tempControlList').getSelectionCount() > 0) {
    //             Ext.getCmp('tempControlList').deselectAll();
    //         }
    //     }
    // }

})