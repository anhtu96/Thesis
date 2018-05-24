Ext.define('myApp.store.TempSensorHome', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.TempSensorHome',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/tempsensorhome',
            writer: {
                writeAllFields: true
            }
        }
    }
})