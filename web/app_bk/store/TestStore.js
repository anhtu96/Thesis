Ext.define('myApp.store.TestStore', {
    extend: 'Ext.data.Store',
    config: {
        data: [{
            val1: 'A Button',
            val2: 1
        }, {
            val1: 'The Button',
            val2: 0
        }],
        model: 'myApp.model.TestModel',
        storeId: 'TestStore'
    }
});