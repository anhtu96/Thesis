Ext.define('myApp.controller.Music_autoController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.musicauto',
    requires: ['myApp.view.Music_autoDataItem'],

    // config: {
    //     refs: {
    //         mainView: '#musicauto'
    //     },
    //     control: {
    //         'button[action=onAddTap]': {
    //             tap: 'onAddTap'
    //         },
    //         'panel[action=musicAuto]': {
    //             loadView: 'loadView'
    //         },
    //         'list[action=onMusicDataView]': {
    //             select: 'onMusicDataView',
    //         }

    //     }
    // },
    config: {
        listen: {
            controller: {
                '*': {
                    loadView: 'loadView',
                }
            }
        }
    },
    loadView: function() {
        Ext.getCmp('musicauto').removeAll();
        var store;
        if (Ext.getCmp('CHList').getLastSelected().get('name') == 'Channel 1')
            store = Ext.getStore('MusicAutoCH1');
        else store = Ext.getStore('MusicAutoCH2');
        if (store.getCount() == 0) {
            Ext.getCmp('musicauto').setItems({
                html: '<font size="5" color="grey"><b><center>Nothing here yet :(</b>' +
                    '<br><br>Tap "<b>+</b>" to add your first item</center></font>',
                style: {
                    'margin-top': '25%'
                }
            });
        } else {
            var dataview = Ext.create('myApp.view.Music_dataview', {
                store: store,
                useComponent: true,
                defaultType: 'musicautodataitem',
                height: '90%',
                // itemTpl: new Ext.XTemplate('<b><font size=5 color="{color1}">{hour}:{min} {period}</font></b><br>',
                //     '<p align="right"><font color="{color2}"><b>{state}</font></b></p>',
                //     '<p><font color="{color1}">{name}</font></p>',
                //     // '<div class="x-button x-button-action button-cls1" ><span class="x-button-label" id="accept">Accept</span></div>'
                // ),

            });
            // var html = Ext.create('Ext.Container', {
            //     html: '<font size="3" color ="grey"><center>*Swipe item to the left to delete it*</center></font>'
            // });
            Ext.getCmp('musicauto').add(dataview);
            dataview.show();
        }

    },

    editItem: function(button) {
        var sheet = Ext.create('myApp.view.Music_autoAddDialog');
        Ext.Viewport.add(sheet);
        Ext.getCmp('musicauto').editRecord = button.up('panel').up('component').getRecord();
        sheet.show();
    },
    deleteItem: function(button) {
        var me = this;
        console.log(me);
        Ext.Msg.confirm('Delete item', 'Are you sure you want to delete this item?', function(value) {
            if (value == 'yes') {
                var record = button.up('panel').up('component').getRecord(),
                    store;
                if (Ext.getCmp('CHList').getLastSelected().get('name') == 'Channel 1')
                    store = Ext.getStore('MusicAutoCH1');
                else store = Ext.getStore('MusicAutoCH2');
                console.log(record);
                store.remove(record);
                store.sync();
                me.fireEvent('loadView');
            }
        })
    },
    toggleDataitem: function(togglefield) {
        var record = togglefield.getParent().getParent().getRecord(),
            store;
        if (Ext.getCmp('CHList').getLastSelected().get('name') == 'Channel 1')
            store = Ext.getStore('MusicAutoCH1');
        else store = Ext.getStore('MusicAutoCH2');
        record.set({
            state: +togglefield.getValue()
        });
        store.add(record);
        store.sync();
        this.fireEvent('loadView');
    },
})