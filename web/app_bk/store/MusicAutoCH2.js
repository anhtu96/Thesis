Ext.define('myApp.store.MusicAutoCH2', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.MusicAutoCH2',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/musicauto2',
            writer: {
                writeAllFields: true
            }

        }
    }
})