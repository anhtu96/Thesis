Ext.define('myApp.store.USD2EUR', {
    alias: 'store.USD2EUR',
    extend: 'Ext.data.Store',
    // fields: [{
    //         name: 'time',
    //         type: 'date',
    //         // dateFormat: 'time'
    //     },
    //     'value'
    // ],
    // data: [{
    //     'time': '2018-04-07T05:17:40.000+0000',
    //     'value': 5
    // }, {
    //     'time': '2018-04-07T05:17:42.000+0000',
    //     'value': 1
    // }]
    fields: [{
        name: 'deviceid',
        type: 'int'
    }, {
        name: 'devicename',
        type: 'string'
    }, {
        name: 'sendtime',
        type: 'date',
    }, {
        name: 'temp',
        type: 'float'
    }, {
        name: 'humid',
        type: 'float'
    }],
    data: [{
        'deviceid': 12,
        'devicename': 'anhtu',
        'humid': 66.4,
        'sendtime': Ext.Date.format(new Date('1/10/2007 03:05:01 PM GMT-0600'), 'F j, Y, g:i a'),
        'temp': 20.3
    }, {
        'deviceid': 11,
        'devicename': 'a',
        'humid': 44.4,
        'sendtime': Ext.Date.format(new Date('1/10/2007 04:05:01 PM GMT-0600'), 'F j, Y, g:i a'),
        'temp': 33.3
    }, {

        'deviceid': 13,
        'devicename': 'a',
        'humid': 100.4,
        'sendtime': Ext.Date.format(new Date('1/10/2007 07:05:01 PM GMT-0600'), 'F j, Y, g:i a'),
        'temp': 55.3

    }]
})