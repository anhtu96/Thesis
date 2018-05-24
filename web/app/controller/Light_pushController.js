Ext.define('myApp.controller.Light_pushController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.lightpush',
    requires: ['myApp.view.Light_addButton', 'myApp.view.Light_dataitem'],
    config: {
        listen: {
            controller: {
                '*': {
                    loadViewLight: 'loadViewLight',
                }
            },
        }
    },
    editRecord: null,
    loadViewLight: function(pushview, selectValue, lightbulbListRecord) {
        if (Ext.getCmp('lightDataview') != null) {
            Ext.getCmp('lightDataview').destroy();
        }
        var panel = pushview.down('panel'),
            toolbar = pushview.down('toolbar'),
            store = Ext.getStore('LightbulbAuto');
        panel.removeAll();
        if (selectValue == 0) {
            var toggle = Ext.create('myApp.view.Light_toggle', {
                id: 'toggleManual',
                listeners: {
                    change: function(selectfield) {
                        var record, store = Ext.getStore('LightbulbHome');
                        if (lightbulbListRecord != null)
                            record = lightbulbListRecord;
                        else
                            record = Ext.getCmp('lightbulbList').getLastSelected();
                        record.set({
                            state: +selectfield.getValue()
                        });
                        console.log(record);
                        store.add(record);
                        store.sync()
                    }
                }
            });
            if (lightbulbListRecord != null)
                toggle.setValue(lightbulbListRecord.get('state'));
            else
                toggle.setValue(Ext.getCmp('lightbulbList').getLastSelected().get('state'));
            panel.add(toggle);
            if (toolbar.down('#addButton') != null) {
                toolbar.down('#addButton').destroy()
            }
        } else {
            var button = Ext.create('myApp.view.Light_addButton'),
                deviceid;
            if (lightbulbListRecord != null)
                deviceid = lightbulbListRecord.get('deviceid');
            else deviceid = Ext.getCmp('lightbulbList').getLastSelected().get('deviceid');
            store.filter('deviceid', deviceid);
            store.load();
            if (toolbar.down('#addButton') == null)
                toolbar.add(button);
            if (store.getCount() == 0) {
                panel.setItems({
                    html: '<font size="5" color="grey"><b><center>Nothing here yet :(</b>' +
                        '<br><br>Tap "<b>+</b>" to add your first item</center></font>',
                    style: {
                        'margin-top': '15%'
                    }
                });
            } else {
                var dataview = Ext.create('myApp.view.Light_dataview', {
                    // itemTpl: new Ext.XTemplate('<b><font size=5>{hour}:{min} {period}</font></b><br>',
                    //     '<p align="right"><b>{state}</b></p>',
                    //     // '<div class="x-button x-button-action button-cls1" ><span class="x-button-label" id="accept">Accept</span></div>'
                    // ),
                    useComponent: true,
                    defaultType: 'lightdataitem',
                    height: '90%',
                    store: store,
                });
                dataview.refresh();
                panel.add(dataview);
                dataview.show();
            }
        }
    },
    changeMode: function(togglefield, newValue) {
        var pushview = togglefield.up('toolbar').up('panel'),
            store = Ext.getStore('LightbulbHome'),
            record = Ext.getCmp('lightbulbList').getLastSelected();
        if (record != null) {
            record.set({
                modestr: newValue ? 'auto' : 'manual',
            });
            store.add(record);
            store.sync();
            this.fireEvent('loadViewLight', pushview, newValue)
        }
    },
    lightbulbAutoAdd: function(button) {
        var sheet = Ext.create('myApp.view.Light_sheet');
        Ext.getCmp('lightpush').add(sheet);
        sheet.show()
    },
    sheetShow: function(sheet) {
        var picker;
        if (Ext.getCmp('timepicker') == null) {
            var picker = Ext.create('myApp.view.TimePicker', {
                id: 'timepicker',
            });
            Ext.Viewport.add(picker);
            picker.setValue({
                hour: '01',
                minute: '00',
                period: 'AM'
            });
        }
        picker = Ext.getCmp('timepicker');
        picker.show();
    },
    sheetHide: function(sheet) {
        this.editRecord = null;
        var pushview = Ext.getCmp('lightpush');
        sheet.destroy();
    },
    onSave: function(button) {
        var record = Ext.create('myApp.model.LightbulbAuto'),
            store = Ext.getStore('LightbulbAuto'),
            lightbulbListRecord = Ext.getCmp('lightbulbList').getLastSelected(),
            value = Ext.getCmp('timepicker').getValues(),
            id = 1,
            state = 1,
            pushview = Ext.getCmp('lightpush');
        store.clearFilter();
        if (this.editRecord != null) {
            id = this.editRecord.get('id');
            state = this.editRecord.get('state');
            record = this.editRecord;
        } else {
            if (store.getCount() == 0)
                id = 1;
            else {
                id = store.getAt(store.getCount() - 1).get('id') + 1;
            }
            state = 1;
        }
        record.set({
            id: id,
            deviceid: lightbulbListRecord.get('deviceid'),
            devicename: lightbulbListRecord.get('devicename'),
            hour: value.hour,
            min: value.minute,
            period: value.period,
            state: state,
            switchtype: button.up('toolbar').up('sheet').radioValue,
        });
        store.add(record);
        store.sync();
        button.up('toolbar').up('sheet').hide();
        this.fireEvent('loadViewLight', pushview, pushview.down('toolbar').down('togglefield').getValue(), Ext.getCmp('lightbulbList').getLastSelected());
        Ext.getCmp('timepicker').destroy();
    },
    toggleDataitem: function(togglefield) {
        var record = togglefield.getParent().getParent().getRecord(),
            store = Ext.getStore('LightbulbAuto'),
            pushview = Ext.getCmp('lightpush');
        record.set({
            state: +togglefield.getValue()
        });
        store.add(record);
        store.sync();
        this.fireEvent('loadViewLight', pushview, pushview.down('toolbar').down('togglefield').getValue(), Ext.getCmp('lightbulbList').getLastSelected());
    },
    deleteItem: function(button) {
        var me = this;
        console.log(me);
        Ext.Msg.confirm('Delete item', 'Are you sure you want to delete this item?', function(value) {
            if (value == 'yes') {
                var record = button.up('panel').up('component').getRecord(),
                    store = Ext.getStore('LightbulbAuto'),
                    pushview = Ext.getCmp('lightpush');
                store.remove(record);
                store.sync();
                me.fireEvent('loadViewLight', pushview, pushview.down('toolbar').down('togglefield').getValue(), Ext.getCmp('lightbulbList').getLastSelected());
            }
        })
    },
    editItem: function(button) {
        var sheet = Ext.create('myApp.view.Light_sheet'),
            record = button.up('panel').up('component').getRecord();
        this.editRecord = record;
        var picker = Ext.create('myApp.view.TimePicker', {
            id: 'timepicker',
        });
        picker.setValue({
            hour: record.get('hour'),
            minute: record.get('min'),
            period: record.get('period')
        });
        Ext.getCmp('lightpush').add(sheet);
        sheet.show();

        if (record.get('switchtype') == 'Off')
            sheet.down('#radioOff').setChecked(true);
        else
            sheet.down('#radioOn').setChecked(true);
    },

})