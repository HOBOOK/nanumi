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
    login: function(options, tokenData){
      console.log(tokenData.refreshToken)
      options.commit('setToken', tokenData.token)
      localStorage.setItem("token",tokenData.token)
      localStorage.setItem("refreshToken", tokenData.refreshToken);
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
          Authorization: 'Bearer ' + state.token
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
