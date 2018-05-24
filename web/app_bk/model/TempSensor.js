Ext.define("myApp.model.TempSensor", {
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
            name: 'sendtime',
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