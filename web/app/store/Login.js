Ext.define('myApp.store.Login', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.Login',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/login',
            writer: {
                writeAllFields: true
            }
        }
    }
})