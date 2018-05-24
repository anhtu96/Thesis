Ext.define('myApp.model.MusicAutoCH1', {
    extend: 'Ext.data.Model',

    config: {
        identifier: {
            type: 'sequential',
        },
        fields: [{
            name: 'id',
            type: 'int'
        }, {
            name: 'name',
            type: 'string'
        }, {
            name: 'hour',
            type: 'string'
        }, {
            name: 'min',
            type: 'string'
        }, {
            name: 'period',
            type: 'string'
        }, {
            name: 'state',
            type: 'int'
        }]
    }
})