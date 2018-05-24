Ext.define('myApp.Touch', {
    extend: 'Ext.app.Application',
    name: 'myApp',
    models: [
        'UserModel',
        'MusicAutoCH1',
        'MusicAutoCH2',
        'MusicManualCH1',
        'MusicManualCH2',
        'Music',
        'MusicMode',
        'TestModel',
        'TempSensorHome',
        'TempSensor',
        'TempControl',
        'TempControlHome',
        'TempDisplay',
        'Grid',
        'User',
        'LightbulbHome',
        'LightbulbAuto',
        'Floor',
        'FlameHome'
    ],
    stores: [
        'UserStore',
        'MusicAutoCH1',
        'MusicAutoCH2',
        'MusicManualCH1',
        'MusicManualCH2',
        'Musics',
        'MusicMode',
        'TestStore',
        'TempSensorHome',
        'TempSensor',
        'TempControl',
        'TempControlHome',
        'TempDisplay',
        'Grid',
        'USD2EUR',
        'LightbulbHome',
        'LightbulbAuto',
        'Floor',
        'FlameHome'
    ],
    controllers: ['mRoot'],
    views: ['Main',
        'Home',
        'Home_light',
        'Temperature',
        'Music',
        'Music_manual',
        'Music_auto',
        'Light',
        'Stats',
        'Gridtest',
    ],
    fullscreen: true,
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
})