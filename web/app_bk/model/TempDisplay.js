Ext.define("myApp.model.TempDisplay", {
    extend: "Ext.data.Model",
    config: {
        identifier: {
            type: 'sequential',
        },
        fields: [{
            name: 'deviceid',
            type: 'int'
        }, {
            name: 'devicename',
            type: 'string'
        }, {
            name: 'temp',
            type: 'float'
        }, {
            name: 'humid',
            type: 'float'
        }]
    }
});