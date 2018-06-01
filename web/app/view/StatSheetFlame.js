Ext.define('myApp.view.StatSheetFlame', {
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
            store: 'FlameHome',
            label: 'Sensor',
            displayField: 'devicename',
            valueField: 'deviceid',
            autoSelect: false,
            listeners: {
                change: 'onSelectFlame'
            }
        }, {
            text: 'reset',
            handler: 'onResetFlame'
        }, {
            xtype: 'spacer'
        }, {
            text: 'close',
            handler: function(button) {
                button.up('toolbar').up('sheet').hide();
                Ext.getCmp('flameList').setDisableSelection(false);
                Ext.getCmp('flameList').deselectAll();
                var task = new Ext.util.DelayedTask(function() {
                    button.up('toolbar').up('sheet').destroy();
                });
                task.delay(500);
            }
        }]
    }, {
        xtype: 'toolbar',
        title: 'Danger signals report'
    }, {
        xtype: 'grid',
        columns: [{
            text: 'Name',
            dataIndex: 'devicename',
            flex: 1.5,
        }, {
            text: 'Time',
            dataIndex: 'sendtime',
            flex: 1.5
        }]
    }]
})