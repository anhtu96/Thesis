Ext.define('myApp.store.Flame', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.Flame',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/flame',
            writer: {
                writeAllFields: true
            }
        }
    }
})