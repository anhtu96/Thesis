Ext.define('myApp.store.TempDisplay', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.TempDisplay',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/tempdisplay',
            writer: {
                writeAllFields: true
            }
        },
    }
})