Ext.define('myApp.view.StatSheetHumid', {
    extend: 'Ext.Sheet',
    requires: ['myApp.controller.StatController'],
    controller: 'stat',
    layout: 'fit',
    height: '60%',
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
                change: 'onSelect'
            }
        }, {
            text: 'reset',
            handler: 'onReset'
        }, {
            xtype: 'spacer'
        }, {
            text: 'close',
            handler: function(button) {
                button.up('toolbar').up('sheet').hide();
                Ext.getCmp('quantityList').setDisableSelection(false);
                Ext.getCmp('quantityList').deselectAll();
                var task = new Ext.util.DelayedTask(function() {
                    button.up('toolbar').up('sheet').destroy();
                });
                task.delay(500);
            }
        }]
    }, {
        xtype: 'cartesian',
        background: 'white',
        interactions: [{
            type: 'crosszoom',
            zoomOnPanGesture: false
        }],
        series: [{
            type: 'line',
            xField: 'sendtime',
            yField: 'humid',
            fill: true,
            style: {
                fillOpacity: 0.4,
                miterLimit: 3,
                lineCap: 'miter',
                lineWidth: 2
            }
        }],
        axes: [{
            type: 'numeric',
            position: 'left',
            fields: 'humid',
            title: {
                text: 'Humidity',
                fontSize: 20
            }
        }, {
            type: 'category',
            position: 'bottom',
            fields: 'sendtime',
            title: {
                text: 'Time',
                fontSize: 20
            }
        }]
    }]
})