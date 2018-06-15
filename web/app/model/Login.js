Ext.define("myApp.model.Login", {
    extend: "Ext.data.Model",
    fields: [{
        name: 'id',
        type: 'int'
    }, {
        name: 'user',
        type: 'string'
    }, {
        name: 'password',
        type: 'string'
    }]
});