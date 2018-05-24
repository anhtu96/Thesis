Ext.define("myApp.model.FlameHome", {
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
        name: 'state',
        type: 'int'
    }, {
        name: 'onlinestatus',
        type: 'string'
    }, {
        name: 'color',
        type: 'string'
    }, {
        name: 'floor',
        type: 'int'
    }]
});