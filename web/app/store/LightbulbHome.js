Ext.define('myApp.store.LightbulbHome', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.LightbulbHome',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/lightbulbhome',
            writer: {
                writeAllFields: true
            }
        }
    }
})