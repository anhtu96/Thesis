Ext.define('myApp.view.Music_dataview', {
    id: 'musicDataView',
    extend: 'Ext.List',
    config: {
        height: '95%',
        listeners: {
            itemswipe: function(list, index, target, record, e, eOpts) {
                if (e.direction == "left") {
                    Ext.Msg.confirm('Changing Status', 'Are you sure you want to delete this ?', function(value) {
                        if (value == 'yes') {
                            var store;
                            if (Ext.getCmp('CHList').getLastSelected().get('name') == 'Channel 1')
                                store = Ext.getStore('MusicAutoCH1');
                            else store = Ext.getStore('MusicAutoCH2');
                            store.remove(record);
                            store.sync();
                            if (store.getCount() == 0) {
                                myApp.getApplication().fireEvent('loadView');
                            }
                        }
                    })
                }

            },
            // select: 'onMusicDataView'
        },
    }
})