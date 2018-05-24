Ext.define('myApp.store.LightbulbAuto', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.LightbulbAuto',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/lightbulbauto',
            writer: {
                writeAllFields: true
            }
        }
    }
})