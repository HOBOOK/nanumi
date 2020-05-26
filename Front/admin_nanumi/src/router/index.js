import Vue from 'vue';
import VueRouter from 'vue-router';
import AuthUser from '../components/AuthUser';
import Receive from '../components/Receive';
import HelloWorld from '../components/HelloWorld';
import RgBandWidth from '../components/RgBandWidth';
import BandWidth from '../components/BandWidth';
// import test from '../components/test'
// import test2 from '../components/test2'
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
  {
    path: '/bandwidth',
    name: 'bandwidth',
    component: BandWidth
  },
  {
    path: '/rgbandwidth',
    name: 'rgbandwidth',
    component: RgBandWidth
  },

  
]
})
