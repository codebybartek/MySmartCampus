// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/store'
import axios from 'axios'
import VueCookies from 'vue-cookies'

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'



Vue.use(BootstrapVue)

Vue.config.productionTip = false

Vue.use(VueCookies)

axios.interceptors.request.use(function (config) {
	if(window.$cookies.get('token')) {
		config.headers.Authorization =  'Bearer ' + window.$cookies.get('token');
	}

	return config;
});

Vue.directive('add-class-hover', {
  bind(el, binding, vnode) {    
    const { value="" } = binding;
    el.addEventListener('mouseenter',()=> {
        el.classList.add(value)
    });
    el.addEventListener('mouseleave',()=> {
        el.classList.remove(value)
    });
  },
  unbind(el, binding, vnode) {
    el.removeEventListener('mouseenter');
    el.removeEventListener('mouseleave')
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
