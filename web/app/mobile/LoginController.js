Ext.define('myApp.mobile.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',
    // init: function(view) {
    //     if (window.localStorage.getItem('user') != null) {
    //         console.log(window.localStorage.getItem('user'))
    //         this.login()
    //     }
    // },
    login: function(button) {
        var store = Ext.getStore('Login'),
            username = button.up('formpanel').getFields('username').getValue(),
            password = button.up('formpanel').getFields('password').getValue(),
            checkLogin = 0;
        store.each(function(record) {
            if (record.get('username') == username && record.get('password') == password) {
                Ext.Viewport.removeAll();
                Ext.Viewport.add([{
                    xtype: 'mainView'
                }]);
                checkLogin = 1;
                var sessionStore = Ext.getStore('LocalSession'),
                    serverSessionStore = Ext.getStore('ServerSession'),
                    record = Ext.create('myApp.model.LocalSession'),
                    recordServerSession = Ext.create('myApp.model.ServerSession'),
                    id = 1;
                if (serverSessionStore.getCount() > 0) {
                    id = serverSessionStore.getAt(serverSessionStore.getCount() - 1).get('id') + 1
                }
                record.set({
                    loginid: id,
                    username: username
                });
                recordServerSession.set({
                    id: record.get('loginid')
                });
                sessionStore.add(record);
                sessionStore.sync();
                serverSessionStore.add(recordServerSession);
                serverSessionStore.sync();
                return;
            }
        });
        if (checkLogin == 0) {
            var tip = new Ext.tip.ToolTip({
                html: 'Your username or password was incorrect'
            });
            tip.showBy(button, "tr-br");
        }
        // let form = this.getView()
        // let user = window.localStorage.getItem('user')
        // if (user != null) {
        //     Ext.Viewport.remove(form)
        // } else {
        //     Ext.Ajax.request({
        //         scope: this,
        //         method: 'POST',
        //         url: '/session',
        //         params: form.getValues(),
        //         callback: function(opt, success, response) {
        //             if (success) {
        //                 myApp.app.user = Ext.decode(response.responseText, true);
        //                 window.localStorage.setItem('user', myApp.app.user.username)
        //                 Ext.Viewport.remove(form)
        //             } else {
        //                 myApp.app.showError(response)
        //             }
        //         }
        //     })
        // }
    }
})