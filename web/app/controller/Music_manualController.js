Ext.define('myApp.controller.Music_manualController', {
    extend: 'Ext.app.ViewController',
    requires: ['myApp.view.Music_addBtn'],
    alias: 'controller.musicmanual',
    musicManualListTap: function(list, index, target, record, e, eOpts) {
        var store, selectRecord;
        if (Ext.getCmp('CHList').getLastSelected().get('name') == 'Channel 1') {
            store = Ext.getStore('MusicManualCH1');
            selectRecord = Ext.create('myApp.model.MusicManualCH1')
        } else {
            store = Ext.getStore('MusicManualCH2');
            selectRecord = Ext.create('myApp.model.MusicManualCH2')
        }

        for (var i = 0; i < list.getStore().getCount(); i++) {
            list.getItemAt(i).setTpl('<b>{name}</b>');
        }


        if (store.getCount() == 0) {
            selectRecord.set({
                id: 1,
                name: record.get('name')
            });
            target.setTpl('<b>{name}</b><object align="right">&#10004</object>');
            store.add(selectRecord);
            store.sync();
            // list.up('panel').down('audio').setUrl(record.get('url'));
            list.up('panel').down('audio').setUrl('audio/alarm/fire.mp3');
            list.up('panel').down('audio').play();
        } else {
            if (record.get('name') == store.getAt(0).get('name')) {
                store.removeAll();
                store.sync();
                target.setTpl('<b>{name}</b>');
                list.up('panel').down('audio').stop();
            } else {
                store.getAt(0).set({
                    name: record.get('name')
                });
                store.sync();
                target.setTpl('<b>{name}</b><object align="right">&#10004</object>');
                list.up('panel').down('audio').setUrl(record.get('url'));
                list.up('panel').down('audio').play();
            }
        }

        list.refresh();
        setTimeout(function() {
            list.deselectAll();
        }, 100);
    },
    musicManualShow: function(panel) {
        var store;
        if (Ext.getCmp('CHList').getLastSelected().get('name') == 'Channel 1') {
            store = Ext.getStore('MusicManualCH1');
        } else {
            store = Ext.getStore('MusicManualCH2');
        }
        if (store.getCount() > 0) {
            var task = new Ext.util.DelayedTask(function() {
                for (var i = 0; i < Ext.getStore('Musics').getCount(); i++) {
                    if (Ext.getCmp('musicManualList').getStore().getAt(i).get('name') == store.getAt(0).get('name')) {
                        // console.log(Ext.getCmp(''))
                        Ext.getCmp('musicManualList').getItemAt(i).setTpl('<b>{name}</b><object align="right">&#10004</object>');
                        Ext.getCmp('musicManualList').refresh();
                    }
                }
            });
            task.delay()


        }
    },

});