Ext.define("myApp.model.UserModel", {
    extend: "Ext.data.Model",
    config: {
        fields: [{
            name: "Name",
            type: "string"
        }, {
            name: "City",
            type: "string"
        }, {
            name: "Points",
            type: "int"
        }]
    }
});