Ext.define('myApp.view.Music_autoAddDialog', {
    extend: 'Ext.Sheet',
    requires: ['myApp.view.TimePicker',
        'myApp.controller.Music_autoAddDialogController'
    ],
    controller: 'musicautoadddialog',
    layout: 'fit',
    modal: true,
    height: '100%',
    width: '100%',
    listeners: {
        hide: 'hideSheet',
        show: 'showSheet'
    },
    items: [{
        xtype: 'navigationview',
        id: 'musicAutoAddDialog',
        listeners: {
            push: 'pushDialog',
            pop: 'popDialog'
        },
        navigationBar: {
            items: [{
                xtype: 'button',
                itemId: 'cancelBtn',
                ui: 'decline',
                text: 'Cancel',
                handler: 'onCancel'
            }, {
                xtype: 'button',
                itemId: 'saveBtn',
                ui: 'action',
                text: 'Save',
                handler: 'onSave',
                align: 'right'
            }]
        },
        items: [{
            xtype: 'panel',
            id: 'configPanel',
            style: {
                'background-color': '#F7F7F7',
            },
            items: [{
                    xtype: 'list',
                    scrollable: false,
                    height: '10%',
                    data: [{
                        name: '<b>Pick a song</b>'
                    }],
                    itemTpl: '{name}',
                    onItemDisclosure: true,
                    listeners: {
                        select: 'selectPickSong',
                        disclose: 'disclosePickSong'
                    },
                },
                // {
                //     xtype: 'togglefield',
                //     label: '<b>Enable</b>',
                //     value: 1
                // }, 
                {
                    xtype: 'audio',
                    id: 'autoPreview',
                    hidden: true,
                }
            ]
        }]
    }]

})