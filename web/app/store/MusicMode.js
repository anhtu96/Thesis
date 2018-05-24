Ext.define("myApp.store.MusicMode", {
    extend: 'Ext.data.Store',
    config: {
        model: 'myApp.model.MusicMode',
        autoLoad: true,

        proxy: {
            type: 'rest',
            url: 'api/musicmode',
            writer: {
                writeAllFields: true
            }
        }

    }
});