Ext.define("myApp.model.TempControlHome", {
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
        type: 'string'
    }, {
        name: 'color',
        type: 'string'
    }, {
        name: 'floor',
        type: 'int'
    }]
});