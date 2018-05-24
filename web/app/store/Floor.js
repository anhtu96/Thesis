Ext.define('myApp.store.Floor', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.Floor',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/floor',
            writer: {
                writeAllFields: true
            }
        }
    }
})