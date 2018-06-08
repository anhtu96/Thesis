Ext.define('myApp.view.Music', {
    extend: 'Ext.NavigationView',
    requires: ['myApp.controller.MusicController', 'myApp.view.Music_dataItem'],
    controller: 'music',
    xtype: 'musicView',
    id: 'musicView',
    navigationBar: {
        style: 'background:#2196F3',

    },
    items: [{
        title: '<p style = color:white><span style = "float:center">Music</span></p>',
        items: [{
                xtype: 'toolbar',
                title: 'Playback Mode',
                ui: 'neutral',
                style: 'margin-top:15px'
            }, {
                xtype: 'togglefield',
                id: 'musicModeBtn',
                listeners: {
                    change: 'onMusicMode'
                },
                label: 'Automatic',
            }, {
                xtype: 'toolbar',
                title: 'Channels Config',
            }, {
                xtype: 'list',
                height: '100%',
                id: 'CHList',
                listeners: {
                    select: 'onCHList'
                },
                data: [{
                    name: 'Channel 1',
                }, {
                    name: 'Channel 2'
                }],
                itemTpl: '{name}'

            }

        ]

    }],
    listeners: {
        show: 'musicShow',
        push: function(navigationview) {
            navigationview.getNavigationBar().setBackButton({
                width: 120,
                style: {
                    'background': 'white',
                    'margin-right': '10px'
                },
            });
        },
        pop: 'musicPop'
    }
})