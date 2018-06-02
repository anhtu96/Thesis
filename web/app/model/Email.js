Ext.define("myApp.model.Email", {
    extend: "Ext.data.Model",
    fields: [{
        name: 'id',
        type: 'int'
    }, {
        name: 'sender',
        type: 'string'
    }, {
        name: 'password',
        type: 'string'
    }, {
        name: 'recipient',
        type: 'string'
    }]
});