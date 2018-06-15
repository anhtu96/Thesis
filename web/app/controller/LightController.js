Ext.define('myApp.controller.LightController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.light',
    tapList: function(list, index, target, record) {
        var user = Ext.getStore('LocalSession').getAt(0).get('username');
        if (user == 'admin') {
            Ext.getCmp('mainPanel').getTabBar().hide();
            var pushview = Ext.create('myApp.view.Light_pushView');
            Ext.getCmp('lightView').push(pushview);
            Ext.getCmp('lightView').getNavigationBar().setTitle('<p style = color:white>' + record.get('devicename') + '</p>');
            pushview.down('toolbar').down('togglefield').setValue(record.get('modestr') == 'auto' ? 1 : 0);
            this.fireEvent('loadViewLight', pushview, pushview.down('toolbar').down('togglefield').getValue(), record);
        } else {
            var task = new Ext.util.DelayedTask(function() {
                list.deselectAll();
            });
            task.delay(100);
            Ext.Msg.alert('Restricted action', 'This action can be performed by admin only.');
        }
    },
    pushLight: function(navigationview) {
        console.log(Ext.getCmp('lightpush').down('toolbar').down('togglefield'));
        navigationview.getNavigationBar().setBackButton({
            width: 150,
            style: {
                'background': 'white',
                'margin-right': '10px'
            },
        });
        var task = new Ext.util.DelayedTask(function() {
            var record = Ext.getCmp('lightbulbList').getLastSelected();
            Ext.getCmp('lightpush').down('toolbar').down('togglefield').setValue(record.get('modestr') == 'auto' ? 1 : 0)
        });
        task.delay(50);
    }
})