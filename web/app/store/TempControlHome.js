Ext.define('myApp.store.TempControlHome', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.TempControlHome',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/tempcontrolhome',
            writer: {
                writeAllFields: true
            }
        }
    }
})