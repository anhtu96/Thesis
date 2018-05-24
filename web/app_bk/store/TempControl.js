Ext.define('myApp.store.TempControl', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.TempControl',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/tempcontrol',
            writer: {
                writeAllFields: true
            }
        }
    }
})