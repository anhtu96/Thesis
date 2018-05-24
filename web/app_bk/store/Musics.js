Ext.define("myApp.store.Musics", {
    extend: 'Ext.data.Store',
    config: {
        model: "myApp.model.Music",
        autoLoad: true,
        sorters: [{
            property: 'id',
            direction: 'ASC'
        }],
        proxy: {
            type: 'ajax',
            url: 'music.json',
            reader: {
                type: 'json',
            }
        }
    }
});