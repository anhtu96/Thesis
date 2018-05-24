Ext.define('myApp.model.MusicMode', {
    extend: 'Ext.data.Model',

    config: {
        identifier: {
            type: 'sequential',
        },
        fields: [{
            name: 'state',
            type: 'string'
        }]
    }
})