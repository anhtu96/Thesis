Ext.define('myApp.mobile.Login', {
  extend: 'Ext.form.Panel',
  xtype: 'login',
  requires: ['myApp.mobile.LoginController'],
  controller: 'login',
  layout: {
    type: 'vbox',
    pack: 'center',
    align: 'center',
  },
  items: [{
    xtype: 'label',
    html: `<i style="font-size:60px;color:#000000;text-align:center" class="fa fa-graduation-cap"></i>`,
  }, {
    xtype: 'label',
    html: `<div style="font-weight:bold">L V T N</div>`,
    //margin: '0 0 30 0',
  }, {
    xtype: 'titlebar',
    docked: 'top',
    titleAlign: 'center',
    title: 'Let\'s Login'
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
    }]
  }, {
    xtype: 'label',
    hidden: true,
    itemId: 'loginfailtext',
    html: 'Login fail ! Please enter correct credentials',
    style: 'color:#990000;margin:5px 0px;'
  }, {
    //margin: '40 0 0 0',
    xtype: 'button',
    text: 'Login',
    padding: 10,
    ui: 'action',
    width: 150,
    listeners: {
      tap: 'login'
    }
  }],


})