Ext.define('myApp.controller.StatController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.stat',

    representTap: function(list) {
        if (list.getLastSelected().get('name') == 'Chart') {
            list.up('navigationview').push(Ext.create('myApp.view.StatPushView'))
        } else {
            var sheet = Ext.create('myApp.view.StatSheetGrid');
            Ext.Viewport.add(sheet);
            sheet.show();
        }
    },
    onSelect: function(selectfield, newval) {
        var store = Ext.getStore('TempSensor');
        store.filter('deviceid', newval.get('deviceid'));
        selectfield.up('toolbar').up('sheet').down('cartesian').setStore(store);
    },
    onReset: function(button) {
        var store = Ext.getStore('TempSensor'),
            selectfield = button.up('toolbar').down('selectfield');
        store.filter('deviceid', selectfield.getValue());
        store.removeAll();
        store.sync();
    },
    onSelectGrid: function(selectfield, newval) {
        var store = Ext.getStore('TempSensor');
        store.filter('deviceid', newval.get('deviceid'));
        selectfield.up('toolbar').up('sheet').down('grid').setStore(store);
    },
    onResetGrid: function(button) {
        var store = Ext.getStore('TempSensor'),
            selectfield = button.up('toolbar').down('selectfield');
        store.filter('deviceid', selectfield.getValue());
        store.removeAll();
        store.sync();
    },
    quantityTap: function(list) {
        list.setDisableSelection(true);
        var sheet;
        if (Ext.getCmp('representList').getLastSelected().get('name') == 'Chart') {
            if (list.getLastSelected().get('name') == 'Temperature') {
                sheet = Ext.create('myApp.view.StatSheetTemp')
            } else {
                sheet = Ext.create('myApp.view.StatSheetHumid')
            }
        } else {
            if (list.getLastSelected().get('name') == 'Temperature') {
                sheet = Ext.create('myApp.view.StatSheetGridTemp')
            } else {
                sheet = Ext.create('myApp.view.StatSheetGridHumid')
            }
        }
        Ext.Viewport.add(sheet);
        sheet.show();
    }
})