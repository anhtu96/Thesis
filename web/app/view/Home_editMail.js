Ext.define('myApp.view.Home_editMail', {
    extend: 'Ext.MessageBox',
    id: 'editMailDialog',
    requires: ['myApp.controller.HomeController'],
    controller: 'home',
    scrollable: true,
    width: 700,
    title: 'Edit email accounts',
    items: [{
        xtype: 'formpanel',
        scrollable: true,
        items: [{
            xtype: 'emailfield',
            name: 'sender',
            label: 'Sender\'s email',
        }, {
            xtype: 'passwordfield',
            name: 'password',
            label: 'Sender\'s password'
        }, {
            xtype: 'emailfield',
            name: 'recipient',
            label: 'Recipient\'s mail'
        }],
    }],
    buttons: [{
        text: 'Save',
        ui: 'confirm',
        handler: 'saveMail'
    }, {
        text: 'Cancel',
        handler: function() {
            Ext.getCmp('editMailDialog').hide();
        }
    }],
    listeners: {
        hide: function(msgbox) {
            msgbox.destroy();
        }
    }
})