Ext.define('myApp.store.MusicManualCH1', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.MusicManualCH1',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/musicmanual1',
            writer: {
                writeAllFields: true
            }
        }
    }
})