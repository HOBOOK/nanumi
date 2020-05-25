import Vue from 'vue';
import VueRouter from 'vue-router';
import AuthUser from '../components/AuthUser';
import Receive from '../components/Receive'
import HelloWorld from '../components/HelloWorld';
Vue.use(VueRouter)

export default new VueRouter({
  mode: "history",
  routes : [
  {
      path: '/',
      name: 'home',
      component: HelloWorld
  },
  {
    path: '/authuser',
    name: 'authuser',
    component: AuthUser
  },
  {
    path: '/receive',
    name: 'receive',
    component: Receive
  },

  
]
})
