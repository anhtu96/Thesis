Ext.define('myApp.view.Music_manual', {
    extend: 'Ext.Panel',
    id: 'music_manual',
    requires: ['myApp.controller.Music_manualController'],
    controller: 'musicmanual',
    listeners: {
        painted: 'musicManualShow'
    },
    items: [{
        xtype: 'list',
        id: 'musicManualList',
        listeners: {
            itemtap: 'musicManualListTap',
        },
        height: '50%',
        store: 'Musics',
        itemTpl: '<b>{name}</b>'
    }, {
        xtype: 'audio',
        hidden: true,
    }]

})