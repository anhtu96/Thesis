Ext.define('myApp.store.MusicAutoCH1', {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.MusicAutoCH1',
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'rest',
            url: 'api/musicauto1',
            writer: {
                writeAllFields: true
            }
        }
    }
})