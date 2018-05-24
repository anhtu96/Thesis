Ext.define('myApp.view.Music_dataItem', {
    extend: 'Ext.dataview.component.ListItem',
    xtype: 'mydataitem',
    config: {
        data: [{
            name: 'Channel 1',
        }, {
            name: 'Channel 2'
        }],
    },

});