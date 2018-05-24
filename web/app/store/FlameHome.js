Ext.define('myApp.store.FlameHome', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.FlameHome',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/flamehome',
            writer: {
                writeAllFields: true
            }
        }
    }
})