Ext.define('myApp.view.Music_autoDataItem', {
    extend: 'Ext.dataview.component.DataItem',
    alias: 'widget.musicautodataitem',
    items: [{
        xtype: 'panel',
        layout: 'hbox',
        items: [{
            xtype: 'component',
            itemId: 'time',
        }, {
            xtype: 'spacer'
        }, {
            xtype: 'button',
            iconCls: 'trash-icon',
            height: 25,
            handler: 'deleteItem'
        }, {
            xtype: 'button',
            iconCls: 'edit-icon',
            height: 25,
            style: 'margin-right:15px',
            handler: 'editItem'
        }, {
            xtype: 'togglefield',
            itemId: 'toggle',
            listeners: {
                dragchange: 'toggleDataitem'
            }
        }]
    }, {
        xtype: 'panel',
        items: [{
            xtype: 'component',
            itemId: 'name',
            style: 'margin-bottom:10px'
        }]
    }],
    updateRecord: function(record) {
        if (record != null) {
            var timeHtml = '<b><font size=5>' + record.get('hour') + ':' +
                record.get('min') + ' ' + record.get('period') + '</font></b>';
            this.down('panel').down('#time').setHtml(timeHtml);
            this.down('panel').down('#toggle').setValue(record.get('state'));
            this.down('#name').setHtml('<font size = 2>' + record.get('name') + '</b></font>');
        }
    },
})