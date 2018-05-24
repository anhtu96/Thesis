Ext.define('myApp.view.Light_sheet', {
    extend: 'Ext.Sheet',
    requires: ['myApp.view.TimePicker'],
    modal: true,
    height: '70%',
    width: '100%',
    radioValue: 'On',
    items: [{
        xtype: 'toolbar',
        docked: 'top',
        items: [{
            itemId: 'cancelBtn',
            ui: 'decline',
            text: 'cancel',
            handler: function(button) {
                button.up('toolbar').up('sheet').hide();
                Ext.getCmp('timepicker').destroy();
            }
        }, {
            xtype: 'spacer',
            itemId: 'spacer',
            html: '<center>Add switching time</center>'
        }, {
            itemId: 'saveBtn',
            ui: 'action',
            text: 'save',
            handler: 'onSave'
        }]
    }, {
        xtype: 'fieldset',
        title: 'Switch type',
        defaults: {
            xtype: 'radiofield',
            labelWidth: '35%',
            listeners: {
                check: function(radiofield) {
                    radiofield.up('sheet').radioValue = radiofield.getValue();
                }
            },
        },
        items: [{
            itemId: 'radioOn',
            name: 'type',
            value: 'On',
            label: 'On',
            checked: true
        }, {
            itemId: 'radioOff',
            name: 'type',
            value: 'Off',
            label: 'Off'
        }],
    }],
    listeners: {
        show: 'sheetShow',
        hide: 'sheetHide'
    }
})