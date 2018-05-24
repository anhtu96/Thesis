Ext.define('myApp.mobile.LoginController', {
  extend: 'Ext.app.ViewController',
  alias: 'controller.login',
  init: function (view) {
    if (window.localStorage.getItem('user') != null) {
      this.login()
    }
  },
  login: function () {
    let form = this.getView()
    let user = window.localStorage.getItem('user')
    if (user != null) {
      Ext.Viewport.remove(form)
    }
    else {
      Ext.Ajax.request({
        scope: this,
        method: 'POST',
        url: '/session',
        params: form.getValues(),
        callback: function (opt, success, response) {
          if (success) {
            myApp.app.user = Ext.decode(response.responseText, true);
            window.localStorage.setItem('user', myApp.app.user.username)
            Ext.Viewport.remove(form)
          } else {
            myApp.app.showError(response)
          }
        }
      })
    }
  }
})