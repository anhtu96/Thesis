Ext.define("myApp.model.Flame", {
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
        name: 'sendtime',
        type: 'string'
    }]
});