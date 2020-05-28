import Vue from 'vue'
import Vuex from 'vuex'
import jwtDecode from 'jwt-decode'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem("token")
  },
  mutations: {
    setToken: function(state, token){
      state.token = token;
    },
  },
  actions: {
    login: function(options, token){
      options.commit('setToken', token)
      localStorage.setItem("token",token);
    },
    logout: function(options){
      options.commit('setToken', null)
      localStorage.removeItem("token");
    },
  },
  getters: {
    isAuthenticated: function(state){
      return state.token ? true : false
    },
    requestHeader: function(state){
      return {
        headers: {
          Authorization: 'JWT ' + state.token
        }
      }
    },
    userId: function(state){
      return state.token ? jwtDecode(state.token).sub : null
    },
    username: function(state){
      return state.token ? jwtDecode(state.token).sub : null
    },
  }

  
})
