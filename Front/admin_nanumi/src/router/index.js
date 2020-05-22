import Vue from 'vue';
import VueRouter from 'vue-router';
import test from '../components/test';

Vue.use(VueRouter)

export default new VueRouter({
  mode: "history",
  routes : [
  {
    path: '/test',
    name: 'test',
    component: test
  },
  
]
})
