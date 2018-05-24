Ext.define('myApp.controller.TempController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.temperature',
    requires: ['myApp.global.Vars'],
    init: function() {
        // setInterval(function() {
        //     Ext.getStore('TempDisplay').load();
        //     Ext.getStore('TempDisplay').removeAll();
        //     Ext.getStore('TempSensorHome').load();
        //     Ext.getStore('TempSensorHome').removeAll();
        //     Ext.getStore('TempSensor').load();
        //     Ext.getStore('TempSensor').removeAll();
        // }, 10000);
    },
    controlTap: function(grid, index, target, record) {
        var msgbox = Ext.create('myApp.view.Temp_tempControlEdit');
        Ext.getCmp('tempView').add(msgbox);
        msgbox.down('formpanel').setRecord(record);
        msgbox.setTitle('Edit device <i>"' + record.get('devicename') + '"</i>');
        msgbox.show();
    },
    onToggleAuto: function(togglefield) {
        var form = togglefield.up('formpanel');
        if (togglefield.getValue() == 1) {
            form.getItems().items[1].enable();
            form.getItems().items[2].enable();
            form.getItems().items[3].enable();
            form.getItems().items[4].disable();
            form.getItems().items[5].disable();
        } else {
            form.getItems().items[1].disable();
            form.getItems().items[2].disable();
            form.getItems().items[3].disable();
            form.getItems().items[4].enable();
            form.getItems().items[5].enable();
        }
    },
    onAutoDrag: function(togglefield) {
        var form = togglefield.up('formpanel'),
            record = Ext.getCmp('controlList').getLastSelected(),
            store = Ext.getStore('TempControl'),
            modestr = 'Manual';
        if (form.getFields('auto').getValue() == 1)
            modestr = 'Automatic'
        record.set({
            auto: +form.getFields('auto').getValue(),
            modestr: modestr,
            tempset: form.getFields('tempset').getValue(),
            humidset: form.getFields('humidset').getValue(),
            fanspeed: form.getFields('fanspeed').getValue(),
            mist: +form.getFields('mist').getValue(),
            sensor: form.getFields('sensor').getValue()
        });
        store.add(record);
        store.sync();
    },
    onSelectSensor: function(selectfield, newval, oldval) {
        var record = Ext.getCmp('controlList').getLastSelected();
        if (record != null) {
            if (newval.get('deviceid') != oldval.get('deviceid')) {
                var form = selectfield.up('formpanel'),
                    store = Ext.getStore('TempControl'),
                    modestr = 'Manual';
                if (form.getFields('auto').getValue() == 1)
                    modestr = 'Automatic'
                console.log(record);
                record.set({
                    auto: +form.getFields('auto').getValue(),
                    modestr: modestr,
                    tempset: form.getFields('tempset').getValue(),
                    humidset: form.getFields('humidset').getValue(),
                    fanspeed: form.getFields('fanspeed').getValue(),
                    mist: +form.getFields('mist').getValue(),
                    sensor: form.getFields('sensor').getValue()
                });
                store.add(record);
                store.sync();
            }
        }

    },
    spinTemp: function(spinnerfield) {
        var form = spinnerfield.up('formpanel'),
            record = Ext.getCmp('controlList').getLastSelected(),
            store = Ext.getStore('TempControl'),
            modestr = 'Manual';
        if (form.getFields('auto').getValue() == 1)
            modestr = 'Automatic'
        record.set({
            auto: +form.getFields('auto').getValue(),
            modestr: modestr,
            tempset: form.getFields('tempset').getValue(),
            humidset: form.getFields('humidset').getValue(),
            fanspeed: form.getFields('fanspeed').getValue(),
            mist: +form.getFields('mist').getValue(),
            sensor: form.getFields('sensor').getValue()
        });
        store.add(record);
        store.sync();
    },
    spinHumid: function(spinnerfield) {
        var form = spinnerfield.up('formpanel'),
            record = Ext.getCmp('controlList').getLastSelected(),
            store = Ext.getStore('TempControl'),
            modestr = 'Manual';
        if (form.getFields('auto').getValue() == 1)
            modestr = 'Automatic'
        record.set({
            auto: +form.getFields('auto').getValue(),
            modestr: modestr,
            tempset: form.getFields('tempset').getValue(),
            humidset: form.getFields('humidset').getValue(),
            fanspeed: form.getFields('fanspeed').getValue(),
            mist: +form.getFields('mist').getValue(),
            sensor: form.getFields('sensor').getValue()
        });
        store.add(record);
        store.sync();
    },
    onFanSpeed: function(sliderfield) {
        var form = sliderfield.up('formpanel'),
            record = Ext.getCmp('controlList').getLastSelected(),
            store = Ext.getStore('TempControl'),
            modestr = 'Manual';
        if (form.getFields('auto').getValue() == 1)
            modestr = 'Automatic'
        record.set({
            auto: +form.getFields('auto').getValue(),
            modestr: modestr,
            tempset: form.getFields('tempset').getValue(),
            humidset: form.getFields('humidset').getValue(),
            fanspeed: form.getFields('fanspeed').getValue(),
            mist: +form.getFields('mist').getValue(),
            sensor: form.getFields('sensor').getValue()
        });
        store.add(record);
        store.sync();
    },
    onMist: function(togglefield) {
            var form = togglefield.up('formpanel'),
                record = Ext.getCmp('controlList').getLastSelected(),
                store = Ext.getStore('TempControl'),
                modestr = 'Manual';
            if (form.getFields('auto').getValue() == 1)
                modestr = 'Automatic'
            record.set({
                auto: +form.getFields('auto').getValue(),
                modestr: modestr,
                tempset: form.getFields('tempset').getValue(),
                humidset: form.getFields('humidset').getValue(),
                fanspeed: form.getFields('fanspeed').getValue(),
                mist: +form.getFields('mist').getValue(),
                sensor: form.getFields('sensor').getValue()
            });
            store.add(record);
            store.sync();
        }
        // onToggleAuto: function(togglefield) {
        //     var cell = togglefield.getParent(),
        //         row = cell.getParent(),
        //         record = row.getRecord();
        //     if (togglefield.getValue() == 1) {
        //         for (var i = 4; i <= 7; i++) {
        //             row.cells[i].getWidget().disable();
        //             if (i == 4 || i == 6)
        //                 row.cells[i].getWidget().setValue(0);
        //         }
        //         row.cells[2].getWidget().enable();
        //         row.cells[3].getWidget().enable();
        //     } else {
        //         row.cells[2].getWidget().disable();
        //         row.cells[3].getWidget().disable();
        //         row.cells[4].getWidget().enable();
        //         row.cells[6].getWidget().enable();
        //     };


    // },
    // onAutoDrag: function(togglefield) {
    //     var cell = togglefield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     record.set({
    //         auto: +row.cells[1].getWidget().getValue(),
    //         tempset: row.cells[2].getWidget().getValue(),
    //         humidset: row.cells[3].getWidget().getValue(),
    //         fan: +row.cells[4].getWidget().getValue(),
    //         fanspeed: row.cells[5].getWidget().getValue(),
    //         mist: +row.cells[6].getWidget().getValue(),
    //         mistlevel: row.cells[7].getWidget().getValue(),
    //     });
    //     store.add(record);
    //     store.sync();
    // },
    // spinTemp: function(spinnerfield) {
    //     var cell = spinnerfield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     record.set({
    //         auto: +row.cells[1].getWidget().getValue(),
    //         tempset: row.cells[2].getWidget().getValue(),
    //         humidset: row.cells[3].getWidget().getValue(),
    //         fan: +row.cells[4].getWidget().getValue(),
    //         fanspeed: row.cells[5].getWidget().getValue(),
    //         mist: +row.cells[6].getWidget().getValue(),
    //         mistlevel: row.cells[7].getWidget().getValue(),
    //     });
    //     store.add(record);
    //     store.sync();
    // },
    // spinHumid: function(spinnerfield) {
    //     var cell = spinnerfield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     record.set({
    //         auto: +row.cells[1].getWidget().getValue(),
    //         tempset: row.cells[2].getWidget().getValue(),
    //         humidset: row.cells[3].getWidget().getValue(),
    //         fan: +row.cells[4].getWidget().getValue(),
    //         fanspeed: row.cells[5].getWidget().getValue(),
    //         mist: +row.cells[6].getWidget().getValue(),
    //         mistlevel: row.cells[7].getWidget().getValue(),
    //     });
    //     store.add(record);
    //     store.sync();
    // },
    // toggleFanDrag: function(togglefield) {
    //     var cell = togglefield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     record.set({
    //         auto: +row.cells[1].getWidget().getValue(),
    //         tempset: row.cells[2].getWidget().getValue(),
    //         humidset: row.cells[3].getWidget().getValue(),
    //         fan: +row.cells[4].getWidget().getValue(),
    //         fanspeed: row.cells[5].getWidget().getValue(),
    //         mist: +row.cells[6].getWidget().getValue(),
    //         mistlevel: row.cells[7].getWidget().getValue(),
    //     });
    //     store.add(record);
    //     store.sync();
    // },
    // toggleFanChange: function(togglefield) {
    //     var cell = togglefield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     if (togglefield.getValue() == 1 && row.cells[1].getWidget().getValue() == 0)
    //         row.cells[5].getWidget().enable();
    //     else
    //         row.cells[5].getWidget().disable();
    // },
    // toggleMistDrag: function(togglefield) {
    //     var cell = togglefield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     if (togglefield.getValue() == 0)
    //         row.cells[7].getWidget().disable();
    //     record.set({
    //         auto: +row.cells[1].getWidget().getValue(),
    //         tempset: row.cells[2].getWidget().getValue(),
    //         humidset: row.cells[3].getWidget().getValue(),
    //         fan: +row.cells[4].getWidget().getValue(),
    //         fanspeed: row.cells[5].getWidget().getValue(),
    //         mist: +row.cells[6].getWidget().getValue(),
    //         mistlevel: row.cells[7].getWidget().getValue(),
    //     });
    //     store.add(record);
    //     store.sync();
    // },
    // toggleMistChange: function(togglefield) {
    //     var cell = togglefield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     if (togglefield.getValue() == 1 && row.cells[1].getWidget().getValue() == 0)
    //         row.cells[7].getWidget().enable();
    //     else
    //         row.cells[7].getWidget().disable();
    // },
    // onFanSpeed: function(sliderfield) {
    //     var cell = sliderfield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     record.set({
    //         auto: +row.cells[1].getWidget().getValue(),
    //         tempset: row.cells[2].getWidget().getValue(),
    //         humidset: row.cells[3].getWidget().getValue(),
    //         fan: +row.cells[4].getWidget().getValue(),
    //         fanspeed: row.cells[5].getWidget().getValue(),
    //         mist: +row.cells[6].getWidget().getValue(),
    //         mistlevel: row.cells[7].getWidget().getValue(),
    //     });
    //     store.add(record);
    //     store.sync();
    // },
    // onMistLevel: function(sliderfield) {
    //     console.log(sliderfield.getValue());
    //     var cell = sliderfield.getParent(),
    //         row = cell.getParent(),
    //         record = row.getRecord(),
    //         store = Ext.getStore('TempControl');
    //     record.set({
    //         auto: +row.cells[1].getWidget().getValue(),
    //         tempset: row.cells[2].getWidget().getValue(),
    //         humidset: row.cells[3].getWidget().getValue(),
    //         fan: +row.cells[4].getWidget().getValue(),
    //         fanspeed: row.cells[5].getWidget().getValue(),
    //         mist: +row.cells[6].getWidget().getValue(),
    //         mistlevel: row.cells[7].getWidget().getValue(),
    //     });
    //     store.add(record);
    //     store.sync();
    // },

});