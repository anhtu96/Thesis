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
    flameTap: function(list) {
        var sheet = Ext.create('myApp.view.StatSheetFlame');
        Ext.Viewport.add(sheet);
        sheet.show();
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
        var task = new Ext.util.DelayedTask(function() {
            Ext.getCmp('sheetgrid').getItemAt(0).setStyle('color:red');
        });
        task.delay(500);
    },
    onResetGrid: function(button) {
        var store = Ext.getStore('TempSensor'),
            selectfield = button.up('toolbar').down('selectfield');
        store.filter('deviceid', selectfield.getValue());
        store.removeAll();
        store.sync();
    },
    onSelectFlame: function(selectfield, newval) {
        // var store = Ext.getStore('Flame');
        // store.filter('deviceid', newval.get('deviceid'));
        // selectfield.up('toolbar').up('sheet').down('grid').setStore(store);
    },
    onResetFlame: function(button) {
        // var store = Ext.getStore('TempSensor'),
        //     selectfield = button.up('toolbar').down('selectfield');
        // store.filter('deviceid', selectfield.getValue());
        // store.removeAll();
        // store.sync();
    },
    quantityTap: function(list) {
        list.setDisableSelection(true);
        var sheet;
        if (list.getLastSelected().get('name') == 'Temperature') {
            sheet = Ext.create('myApp.view.StatSheetTemp')
        } else {
            sheet = Ext.create('myApp.view.StatSheetHumid')
        }
        Ext.Viewport.add(sheet);
        sheet.show();
    }
})