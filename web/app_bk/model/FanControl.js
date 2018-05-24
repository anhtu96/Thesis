Ext.define('myApp.model.FanControl', {
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