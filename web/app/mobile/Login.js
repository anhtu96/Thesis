Ext.define('myApp.mobile.Login', {
    extend: 'Ext.form.Panel',
    xtype: 'login',
    id: 'login',
    requires: ['myApp.mobile.LoginController'],
    controller: 'login',
    layout: {
        type: 'vbox',
        pack: 'center',
        align: 'center',
    },
    items: [{
        xtype: 'label',
        html: `<i style="font-size:60px;color:#000000;text-align:center" class="fa fa-key"></i>`,
    }, {
        xtype: 'label',
        html: `<div style="font-weight:bold">Log in first!</div>`,
        //margin: '0 0 30 0',
    }, {
        xtype: 'titlebar',
        docked: 'top',
        titleAlign: 'center',
        title: 'Welcome to our salanganes farm!'
    }, {
        xtype: 'fieldset',
        items: [{
            xtype: 'textfield',
            width: 300,
            required: true,
            name: 'username',
            label: 'Username',
            labelAlign: 'placeholder',
        }, {
            width: 300,
            xtype: 'passwordfield',
            required: true,
            label: 'Password',
            name: 'password',
            labelAlign: 'placeholder',
        }],
    }, {
        xtype: 'label',
        hidden: true,
        itemId: 'loginfailtext',
        html: 'Login fail ! Please enter correct credentials',
        style: 'color:#990000;margin:5px 0px;'
    }, {
        //margin: '40 0 0 0',
        xtype: 'button',
        text: 'Log in',
        padding: 10,
        ui: 'action',
        width: 150,
        listeners: {
            tap: 'login'
        }
    }],


})