Ext.define('myApp.store.LocalSession', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.LocalSession',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'localstorage',
            writer: {
                writeAllFields: true
            }
        }
    }
})