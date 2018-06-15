Ext.define('myApp.controller.mRoot', {
    extend: 'Ext.app.Controller',
    requires: ['myApp.mobile.Login'],
    id: 'mroot',
    test: 0,
    init: function() {
        Ext.get('spinner').remove();
        /* if (window.localStorage.getItem('user') == null) {
           Ext.Viewport.add({
             xtype: 'login'
           })
         }*/
        var storeSession = Ext.getStore('ServerSession'),
            storeLocalSession = Ext.getStore('LocalSession'),
            check = 0;
        var task = new Ext.util.DelayedTask(function() {
            if (storeLocalSession.getCount() > 0) {
                storeSession.each(function(record) {
                    if (record.get('id') == storeLocalSession.getAt(0).get('loginid')) {
                        check = 1;
                        return;
                    }
                });
            }

            if (check == 0) {
                storeLocalSession.removeAll();
                storeLocalSession.sync();
                Ext.Viewport.add({
                    xtype: 'login'
                })
            } else {
                Ext.Viewport.add({
                    xtype: 'mainView',
                });
                var user = Ext.getStore('LocalSession').getAt(0).get('username');
                if (user == 'user') {
                    Ext.getCmp('musicModeBtn').setDisabled(true);
                }
            }
            var initNum = 0;
            var editRecord = 2;
        });
        task.delay(1000);

    }
})