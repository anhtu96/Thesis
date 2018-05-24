Ext.define("myApp.model.LightbulbAuto", {
    extend: "Ext.data.Model",
    fields: [{
        name: 'id',
        type: 'int'
    }, {
        name: 'deviceid',
        type: 'int'
    }, {
        name: 'devicename',
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
        name: 'switchtype',
        type: 'string'
    }, {
        name: 'state',
        type: 'int'
    }]
});