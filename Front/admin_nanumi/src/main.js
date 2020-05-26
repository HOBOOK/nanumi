import Vue from 'vue'
import App from './App.vue'
import './plugins/element.js'
import vuetify from './plugins/vuetify';
import router from './router'
import AOS from "aos"
import "aos/dist/aos.css"



Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  created(){
    AOS.init();
  },
  
  render: h => h(App)
}).$mount('#app')
