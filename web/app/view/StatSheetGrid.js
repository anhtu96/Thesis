Ext.define('myApp.view.StatSheetGrid', {
    extend: 'Ext.Sheet',
    requires: ['myApp.controller.StatController'],
    controller: 'stat',
    layout: 'fit',
    height: '80%',
    width: '80%',
    items: [{
        xtype: 'toolbar',
        docked: 'top',
        items: [{
            xtype: 'selectfield',
            store: 'TempSensorHome',
            label: 'Sensor',
            displayField: 'devicename',
            valueField: 'deviceid',
            autoSelect: false,
            listeners: {
                change: 'onSelectGrid'
            }
        }, {
            text: 'reset',
            handler: 'onResetGrid'
        }, {
            xtype: 'spacer'
        }, {
            text: 'close',
            handler: function(button) {
                button.up('toolbar').up('sheet').hide();
                Ext.getCmp('representList').setDisableSelection(false);
                Ext.getCmp('representList').deselectAll();
                var task = new Ext.util.DelayedTask(function() {
                    button.up('toolbar').up('sheet').destroy();
                });
                task.delay(500);
            }
        }]
    }, {
        xtype: 'grid',
        id: 'sheetgrid',
        columns: [{
            text: 'Name',
            dataIndex: 'devicename',
            flex: 1.5,
            renderer: function(value, meta) {
                if (value == 'Sensor 1') {
                    value = '<b>Sensor 1</b>'
                }
                return value;
            },
            cell: {
                xtype: 'textcell',
                encodeHtml: true,
                align: 'center'
            }
        }, {
            text: 'Time',
            dataIndex: 'sendtime',
            flex: 1.5
        }, {
            text: 'Temperature',
            dataIndex: 'temp',
            flex: 1
        }, {
            text: 'Humidity',
            dataIndex: 'humid',
            flex: 1
        }],
    }]
})