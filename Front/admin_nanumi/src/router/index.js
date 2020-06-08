// @author jsw

import Vue from 'vue';
import VueRouter from 'vue-router';
import AuthUser from '../components/AuthUser';
import Receive from '../components/Receive';
import Home from '../components/Home';
import RgBandWidth from '../components/RgBandWidth';
import store from '../store/index';

Vue.use(VueRouter)

const requireAuth = () => (to, from, next) => {
  if (store.state.token !== null) {
    return next();
  }
  else{
    alert("로그인을 해주세요")
    next('/');
  }
};

export default new VueRouter({
  mode: "history",
  routes : [
  {
      path: '/',
      name: 'home',
      component: Home
  },
  {
    path: '/authuser',
    name: 'authuser',
    component: AuthUser,
    beforeEnter: requireAuth()
  },
  {
    path: '/receive',
    name: 'receive',
    component: Receive,
    beforeEnter: requireAuth()
  },
  {
    path: '/rgbandwidth',
    name: 'rgbandwidth',
    component: RgBandWidth,
    beforeEnter: requireAuth()
  },
]
})
