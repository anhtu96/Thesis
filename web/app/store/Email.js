Ext.define('myApp.store.Email', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.Email',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/email',
            writer: {
                writeAllFields: true
            }
        }
    }
})