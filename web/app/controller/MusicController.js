Ext.define('myApp.controller.MusicController', {
    extend: 'Ext.app.ViewController',
    requires: ['myApp.view.Music_addBtn', 'myApp.controller.Music_autoController'],
    alias: 'controller.music',
    onMusicMode: function(togglefield) {
        var store = Ext.getStore('MusicMode'),
            record = store.getAt(0),
            state;
        if (togglefield.getValue() == 0)
            state = 'off';
        else state = 'on';
        Ext.getStore('MusicManualCH1').removeAll();
        Ext.getStore('MusicManualCH1').sync();
        Ext.getStore('MusicManualCH2').removeAll();
        Ext.getStore('MusicManualCH2').sync();
        record.set({
            state: state
        });
        store.add(record);
        store.sync();
        console.log(store);
    },
    onCHList: function(list) {
        var user = Ext.getStore('LocalSession').getAt(0).get('username');
        if (user == 'admin') {
            Ext.getCmp('mainPanel').getTabBar().hide();
            if (Ext.getCmp('musicModeBtn').getValue() == 0) {
                Ext.getCmp('musicView').push(Ext.create('myApp.view.Music_manual'));
                Ext.getCmp('musicView').getNavigationBar().setTitle('<p style = color:white>Manual Mode - ' + Ext.getCmp('CHList').getLastSelected().get('name') + '</p>');
            } else {
                var musicAddBtn = Ext.create('myApp.view.Music_addBtn', {
                    id: 'musicAddBtn',
                    iconCls: 'x-fa fa-plus',
                    align: 'right'
                });
                Ext.getCmp('musicView').push(Ext.create('myApp.view.Music_auto'));
                Ext.getCmp('musicView').getNavigationBar().add([musicAddBtn]);
                Ext.getCmp('musicView').getNavigationBar().setTitle('<p style = color:white>Automatic Mode - ' + Ext.getCmp('CHList').getLastSelected().get('name') + '</p>');
                this.fireEvent('loadView');
            }
        } else {
            var task = new Ext.util.DelayedTask(function() {
                list.deselectAll();
            });
            task.delay(100);
            Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
        }
    },

    musicPop: function() {
        console.log(Ext.getCmp('musicView'));
        var store = Ext.getStore('MusicMode');
        if (store.getAt(0).get('state') == 'on') Ext.getCmp('musicModeBtn').setValue(1);
        else Ext.getCmp('musicModeBtn').setValue(0);
        Ext.getCmp('mainPanel').getTabBar().show();
        if (Ext.getCmp('musicAddBtn') != null) {
            Ext.getCmp('musicAddBtn').destroy();
        }
        Ext.getCmp('CHList').deselectAll();


    },
    musicShow: function() {
        var store = Ext.getStore('MusicMode');
        if (store.getAt(0).get('state') == 'on') Ext.getCmp('musicModeBtn').setValue(1);
        else Ext.getCmp('musicModeBtn').setValue(0);
    },
    onAddTap: function() {
        var sheet = Ext.create('myApp.view.Music_autoAddDialog');
        Ext.Viewport.add(sheet);
        sheet.show();
    },
});