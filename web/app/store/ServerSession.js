Ext.define("myApp.store.ServerSession", {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.ServerSession',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/serversession',
            writer: {
                writeAllFields: true
            }
        }

    }
});