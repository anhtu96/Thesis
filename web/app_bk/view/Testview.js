Ext.define('myApp.view.Testview', {
    extend: 'Ext.chart.CartesianChart',
    xtype: 'testview',
    requires: [
        'Ext.chart.CartesianChart',
        'Ext.chart.interactions.ItemEdit',
        'Ext.chart.series.Bar',
        'Ext.chart.axis.Numeric',
        'Ext.chart.axis.Category',
        'Ext.chart.interactions.PanZoom'
    ],

    width: 600,
    height: 400,
    store: 'TempSensor',
    interactions: [{
        type: 'crosszoom',
        zoomOnPanGesture: false
    }],
    series: [{
        type: 'line',
        xField: 'temp',
        yField: 'deviceid',
        fill: true,
        style: {
            fillOpacity: 0.4,
            miterLimit: 3,
            lineCap: 'miter',
            lineWidth: 2
        }
    }],
    axes: [{
            type: 'numeric',
            position: 'left',
            fields: ['deviceid'],
            title: {
                text: 'USD to Euro',
                fontSize: 20
            }
        }, {
            type: 'numeric',
            position: 'bottom',
            fields: 'temp',
            title: {
                text: 'Date',
                fontSize: 20
            }
        }]
        // initialize: function() {
        //     this.callParent();
        //     var toolbar = Ext.ComponentQuery.query('toolbar', this)[0],
        //         interaction = Ext.ComponentQuery.query('interaction', this)[0];
        //     if (toolbar && interaction) {
        //         toolbar.add(interaction.getUndoButton());
        //     }
        // }
})