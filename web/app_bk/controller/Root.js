Ext.define('myApp.controller.Root', {
    extend: 'Ext.app.Controller',
    requires: ['myApp.view.Main', 'myApp.view.authentication.Login', 'Ext.ux.state.RemoteProvider'],
    init: function() {
        //Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        Ext.Ajax.request({
            scope: this,
            method: 'GET',
            url: '/session',
            callback: function(opt, success, response) {
                if (success) {
                    Ext.state.Manager.setProvider(Ext.create('Ext.ux.state.RemoteProvider'))
                    myApp.app.user = Ext.decode(response.responseText, true)
                    Ext.getStore('States').load(() => {
                        this.loadApp()
                    })
                } else {
                    this.login = Ext.create('widget.login', {
                        listeners: {
                            scope: this,
                            login: this.onLogin
                        }
                    })

                }
            }
        });
    },

    onLogin: function() {
        this.login.close()
        Ext.state.Manager.setProvider(Ext.create('Ext.ux.state.RemoteProvider'))
        Ext.getStore('States').load(() => {
            this.loadApp()
        })
    },

    loadApp: function() {
        let mqttClient = mqtt.connect({
            host: location.hostname,
            port: location.port,
            username: '@websocket',
            password: `${myApp.app.user.username}@${myApp.app.user.apikey}`
        })
        console.log(mqttClient)
        mqttClient.on('message', (topic, message) => {
            console.log(topic)
            console.log(new TextDecoder('utf-8').decode(message))
        })
        mqttClient.on('connect', () => {
            console.log('mqtt connected')
            mqttClient.subscribe(`${myApp.app.user.apikey}/#`)

        })
        mqttClient.on('error', (err) => {
            console.log(err)
        })
        setInterval(() => {
            Ext.getStore('States').sync()
        }, 500)
        Ext.getStore('States').load(function() {
                console.log('asd')
            })
            //myApp.app.socket = io('/' + myApp.app.user.username)
            // myApp.app.socket.on('message', console.log)
        Ext.getStore('Devices').load()
        let mainView = Ext.create('widget.main');
    },


})