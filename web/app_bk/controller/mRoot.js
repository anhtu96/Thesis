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
        var initNum = 0;
        var editRecord = 2;
        var audio = Ext.create('Ext.Audio', {
            url: 'audio/alarm/fire.mp3',
            id: 'fireaudio',
        });
        Ext.Viewport.add([{
            // xtype: 'tabpanel',
            // animation: {
            //   type: 'slide',
            //   direction: 'right',
            //   duration: 500
            // },
            // items: [{
            //   title: 'Home',
            //   items: [{
            //     xtype: 'button',
            //     text: 'HEllo',
            //   }]
            // }, {
            //   title: 'Dashboard',
            //   items: [{
            //     xtype: 'button',
            //     text: 'WORld',
            //   }]
            // }]
            xtype: 'mainView',
        }, audio]);
        // Ext.Viewport.add({
        //     xtype: 'list',
        //     data: [{
        //         a: '1',
        //         b: 0
        //     }, {
        //         a: '2',
        //         b: 1
        //     }],
        //     useComponent: true,
        //     defaultType: 'mydataitem'
        // });
    }
})