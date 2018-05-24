Ext.define('myApp.view.MyDataView', {
    extend: 'Ext.dataview.List',
    requires: ['myApp.view.Music_dataItem'],
    xtype: 'mydataview',
    config: {
        // data: [{
        //     name: 'Channel 1',
        // }, {
        //     name: 'Channel 2'
        // }],
        itemTpl: '{name}',
        defaultType: 'mydataitem',
        height: '50%'
    }
});