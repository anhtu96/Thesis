Ext.define('myApp.store.MusicManualCH2', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.MusicManualCH2',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/musicmanual2',
            writer: {
                writeAllFields: true
            }
        }
    }
})