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
        var user = Ext.getStore('LocalSession').getAt(0).get('username');
        if (user == 'admin') {
            var store = Ext.getStore('TempSensor'),
                selectfield = button.up('toolbar').down('selectfield');
            store.filter('deviceid', selectfield.getValue());
            store.removeAll();
            store.sync();
        } else {
            Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
        }
    },
    onSelectGrid: function(selectfield, newval) {
        var store = Ext.getStore('TempSensor');
        store.filter('deviceid', newval.get('deviceid'));
        selectfield.up('toolbar').up('sheet').down('grid').setStore(store);
    },
    onResetGrid: function(button) {
        var user = Ext.getStore('LocalSession').getAt(0).get('username');
        if (user == 'admin') {
            var store = Ext.getStore('TempSensor'),
                selectfield = button.up('toolbar').down('selectfield');
            store.filter('deviceid', selectfield.getValue());
            store.removeAll();
            store.sync();
        } else {
            Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
        }
    },
    onSelectFlame: function(selectfield, newval) {
        var store = Ext.getStore('Flame');
        store.filter('deviceid', newval.get('deviceid'));
        selectfield.up('toolbar').up('sheet').down('grid').setStore(store);
    },
    onResetFlame: function(button) {
        var user = Ext.getStore('LocalSession').getAt(0).get('username');
        if (user == 'admin') {
            var store = Ext.getStore('Flame'),
                selectfield = button.up('toolbar').down('selectfield');
            store.filter('deviceid', selectfield.getValue());
            store.removeAll();
            store.sync();
        } else {
            Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
        }
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