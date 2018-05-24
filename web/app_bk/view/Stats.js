Ext.define('myApp.view.Stats', {
    extend: 'Ext.NavigationView',
    requires: ['myApp.controller.StatController'],
    controller: 'stat',
    xtype: 'stats',
    id: 'stat',
    navigationBar: {
        docked: 'top',
        style: 'background:#2196F3;'
    },
    items: [{
        title: '<p style = color:white>Statistics</p>',
        items: [{
            xtype: 'toolbar',
            title: 'Types of data representation',
            style: 'margin-top:15px'
        }, {
            xtype: 'list',
            id: 'representList',
            data: [{
                'name': 'Table'
            }, {
                'name': 'Chart'
            }],
            itemTpl: '{name}',
            listeners: {
                select: 'representTap'
            }
        }]
    }],
    listeners: {
        push: function(navigationview) {
            Ext.getCmp('mainPanel').getTabBar().hide();
            navigationview.getNavigationBar().setBackButton({
                width: 100,
                style: {
                    'background': 'white',
                    'margin-right': '10px'
                },
            });
            navigationview.getNavigationBar().setTitle('<p style = color:white>Chart representation</p>');
        },
        pop: function() {
            Ext.getCmp('mainPanel').getTabBar().show();
            Ext.getCmp('representList').deselectAll();
        }
    }
})