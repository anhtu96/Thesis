Ext.define('myApp.controller.Music_autoAddDialogController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.musicautoadddialog',
    picksongRecord: null,
    pickSongAuto: function(list, index, target, record, e, eOpts) {

        for (var i = 0; i < list.getStore().getCount(); i++) {
            list.getItemAt(i).setTpl('<b>{name}</b>');
        }
        target.setTpl('<b>{name}</b><object align="right">&#10004</object>');
        list.refresh();
        setTimeout(function() {
            list.deselectAll();
        }, 100);
        this.pickSongRecord = record;
        var url = record.get('url'),
            audio = list.up('panel').down('audio');
        audio.setUrl(url);
        audio.play();
    },
    onCancel: function(button) {
        button.up('navigationview').up('sheet').hide();
        Ext.getCmp('timepicker').destroy();
    },
    onSave: function(button) {
        var record,
            channel = Ext.getCmp('CHList').getLastSelected().get('name'),
            id = 1,
            state = 1,
            store;
        if (channel == 'Channel 1') {
            store = Ext.getStore('MusicAutoCH1');
        } else {
            store = Ext.getStore('MusicAutoCH2');
        };

        if (store.getCount() > 0) {
            id = store.getAt(store.getCount() - 1).get('id') + 1;
        }
        if (channel == 'Channel 1') {
            record = Ext.create('myApp.model.MusicAutoCH1');
        } else
            record = Ext.create('myApp.model.MusicAutoCH2');

        if (Ext.getCmp('musicDataView') != null) {
            if (Ext.getCmp('musicauto').editRecord != null) {
                record = Ext.getCmp('musicauto').editRecord;
                id = record.get('id');
            }
        }
        record.set({
            id: id
        });
        var value = Ext.getCmp('timepicker').getValues(),
            hour = value.hour,
            min = value.minute,
            period = value.period;
        // var hour = '5',
        //     min = '10',
        //     period = 'AM';
        var time = hour + ':' + min + ' ' + period,
            name = this.pickSongRecord.get('name');
        var existRecord = store.findBy(
            function(rec, id) {
                if (rec.get('name') === name &&
                    rec.get('hour') === hour &&
                    rec.get('min') === min &&
                    rec.get('period') === period &&
                    id != record.get('id')) {
                    return true; // a record with this data exists
                }
                return false; // there is no record in the store with this data
            }
        );
        if (existRecord == -1) {
            record.set({
                name: this.pickSongRecord.get('name'),
                hour: hour,
                min: min,
                period: period,
                state: state,
                // color1: color1,
                // color2: color2
            });
            store.add(record);
            store.sync();
            button.up('navigationview').up('sheet').hide();
            this.fireEvent('loadView');
            Ext.getCmp('timepicker').destroy();
        } else {
            Ext.Msg.alert('Option\'s already existed!');
        };
    },
    hideSheet: function(sheet) {
        if (Ext.getCmp('musicDataView') != null)
            Ext.getCmp('musicDataView').deselectAll();
        Ext.getCmp('musicauto').editRecord = null;
        sheet.destroy();
    },
    showSheet: function(sheet) {
        var picker = Ext.create('myApp.view.TimePicker', {
            id: 'timepicker',
            listeners: {
                pick: function(picker) {
                    console.log(picker.getValues())
                }
            },
        });
        Ext.Viewport.add(picker);
        picker.show();
        picker.setValue({
            hour: '01',
            minute: '00',
            period: 'AM'
        });
        if (Ext.getCmp('musicDataView') != null) {
            if (Ext.getCmp('musicauto').editRecord != null) {
                var record = Ext.getCmp('musicauto').editRecord;
                Ext.getCmp('timepicker').setValue({
                    hour: record.get('hour'),
                    minute: record.get('min'),
                    period: record.get('period')
                });
                // if (record.get('state') == 'Enabled') sheet.down('navigationview').down('container').down('togglefield').setValue(1);
                // else sheet.down('navigationview').down('container').down('togglefield').setValue(0);
                this.pickSongRecord = record;
            }
        }
    },
    selectPickSong: function(list) {
        list.up('navigationview').push(Ext.create('myApp.view.Music_autoPickSong'));
    },
    disclosePickSong: function(list) {
        list.up('navigationview').push(Ext.create('myApp.view.Music_autoPickSong'));
    },
    hidePickSong: function() {
        if (Ext.getCmp('musicAutoAddDialog').down('list') != null) {
            Ext.getCmp('musicAutoAddDialog').down('list').deselectAll();
        }
    },
    pushDialog: function(navigationview) {
        navigationview.down('#cancelBtn').hide();
        navigationview.down('#saveBtn').hide();

    },
    popDialog: function(navigationview) {
        if (navigationview.down('#cancelBtn') != null && navigationview.down('#saveBtn') != null) {
            navigationview.down('#cancelBtn').show();
            navigationview.down('#saveBtn').show();
        }
    },
    paintPickSong: function() {
        var me = this;
        if (Ext.getCmp('musicDataView') != null) {
            if (Ext.getCmp('musicDataView').getSelectionCount() > 0) {
                var task = new Ext.util.DelayedTask(function() {
                    for (var i = 0; i < Ext.getCmp('pickSongAutoList').getStore().getCount(); i++) {
                        Ext.getCmp('pickSongAutoList').getItemAt(i).setTpl('<b>{name}</b>');
                    }
                    if (Ext.getCmp('musicDataView').getSelectionCount() > 0) {
                        var record = Ext.getCmp('musicDataView').getLastSelected();
                        for (var i = 0; i < Ext.getStore('Musics').getCount(); i++) {
                            if (me.pickSongRecord.get('name') == Ext.getStore('Musics').getAt(i).get('name')) {
                                Ext.getCmp('pickSongAutoList').getItemAt(i).setTpl('<b>{name}</b><object align="right">&#10004</object>');
                                // this.pickSongRecord = Ext.getCmp('pickSongAutoList').getItemAt(i).getRecord();
                            }
                        }
                    }
                    Ext.getCmp('pickSongAutoList').refresh();
                });
                task.delay(100);
            }
        }
    }
})