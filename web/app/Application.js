Ext.define('myApp.Application', {
    extend: 'Ext.app.Application',
    name: 'myApp',
    stores: [],
    models: [],
    controllers: ['Root'],
    showError: function(response) {
        var data;
        if (Ext.isString(response)) {
            Ext.Msg.alert('ERROR:', response);
        } else if (response.responseText) {
            data = Ext.decode(response.responseText);
            if (data.details) {
                Ext.Msg.alert('ERROR:', data.details);
            } else {
                Ext.Msg.alert('ERROR:', data.message);
            }
        } else if (response.statusText) {
            Ext.Msg.alert('ERROR:', response.statusText);
        } else {
            Ext.Msg.alert('ERROR:', 'Connection Error !');
        }
    },
    toast: function(message, success) {
        Ext.toast({
            html: message,
            align: 'b',
            bodyStyle: {
                backgroundColor: success ? '#90b962' : '#e44959',
                color: 'white',
                fontWeight: 'bold',
            }

        });
    }
})