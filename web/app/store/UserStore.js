Ext.define("myApp.store.UserStore", {
    extend: 'Ext.data.Store',
    storeId: "usersStore",
    config: {
        model: "myApp.model.UserModel",
        autoLoad: true,
        header: {
            "Content-Type": "application/json"
        },
        proxy: {
            type: 'ajax',
            url: 'users.json',
            reader: {
                type: 'json',
                rootProperty: 'users'
            }
        }
    }
});