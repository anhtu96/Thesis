Ext.define('myApp.store.TempSensor', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.TempSensor',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/tempsensor',
            writer: {
                writeAllFields: true
            }
        },
    }
})