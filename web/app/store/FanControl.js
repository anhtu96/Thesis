Ext.define('myApp.store.FanControl', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.FanControl',
        autoLoad: true,
        proxy: {
            type: 'rest',
            url: 'api/fancontrol',
            writer: {
                writeAllFields: true
            }
        }
    }
})