Ext.define('myApp.view.Music_autoPickSong', {
    extend: 'Ext.Panel',
    title: 'Pick a song',
    listeners: {
        hide: 'hidePickSong',
        painted: 'paintPickSong'
    },
    items: [{
        xtype: 'list',
        listeners: {
            itemtap: 'pickSongAuto'
        },
        scrollable: true,
        id: 'pickSongAutoList',
        store: 'Musics',
        itemTpl: new Ext.XTemplate(
            '<b>{name}</b>'
        )
    }, {
        xtype: 'audio',
        hidden: true
    }]
})