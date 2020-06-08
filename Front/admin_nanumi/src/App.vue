// @author jsw
<template>
  <v-app>
    <v-app-bar
      color="#03A9F4"
      dark
      max-height=64px
    >
      <v-app-bar-nav-icon @click="open"></v-app-bar-nav-icon>
      
      <v-toolbar-title>나누미</v-toolbar-title>
        <v-spacer></v-spacer>
                  
          <div v-if="isAuthenticated">
            <span>{{username}}님 환영합니다. </span>
            <v-btn class="ml-4" color="primary" @click="logout()">로그아웃</v-btn>
          </div>

         <div v-else class="text-center">
            <v-menu
              v-model="loginMenu"
              :close-on-content-click="false"
              :nudge-width="200"
              :close-delay="5000"
              :open-on-hover="false"
              offset-y
            >
              <template v-slot:activator="{ on }">
                <v-btn color="primary" v-on="on"
              @click="errors = []; credential2.username='';  credential2.password='';">
                  <span>로그인</span>
                </v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">로그인</span>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field label="ID" v-model="credential2.username" required style="padding:0px;margin:0px;"></v-text-field>
                        <v-text-field label="Password" v-model="credential2.password" type="password" required class="p-0 m-0" style="padding:0px;margin:0px;" @keydown.enter="login()"></v-text-field>
                      </v-col>
                    </v-row>
                    <div v-if="errors.length">
                      <div v-for="(error, idx) in errors" :key="idx" class="mx-auto text-danger">
                          {{error}}
                      </div>
                    </div>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="loginMenu = false;">취소</v-btn>
                  <v-btn color="blue darken-1" text @click="login();">확인</v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </div>
    </v-app-bar>

 <v-navigation-drawer
    v-model="drawer"
      absolute
      temporary>
      <v-list dense nav>
        <v-list-item link>
            <v-list-item-avatar>
            <v-icon>account_circle</v-icon>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="title">tmddn1304</v-list-item-title>
            <v-list-item-subtitle>com1304@daou.com</v-list-item-subtitle>
          </v-list-item-content>

          <!-- <v-list-item-action>
            <v-icon>mdi-menu-down</v-icon>
          </v-list-item-action> -->
        </v-list-item>
      </v-list>
      <v-divider></v-divider>
        
      <v-list
        nav
        dense
      >

        <v-list-item-group v-model="selected" color="primary">
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
          >
            <v-list-item-icon>
              <v-icon v-text="item.icon"></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title @click="switchPage(item.to)" v-text="item.text"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-content height="100%">
      <router-view :key="$route.fullPath"></router-view>
    </v-content>
  </v-app>
</template>


<script>
import axios_common from './axios_common';
import { mapGetters } from 'vuex';
export default {
  name: 'App',
  components: {
  },
  computed: {
    ...mapGetters([
      'isAuthenticated',
      'requestHeader',
      'userId',
      'username'
    ])
  },

  data: () => ({
    drawer:false,
    selected: 0,
    signupMenu: false,
    loginMenu: false,
    // loadingStatus: false,
    password2: '',
    credential: {
      username: '',
      password: ''
    },
    credential2: {
      username: '',
      password: ''
    },
    errors: [],
    items: [
        { text: '대시보드', icon: 'mdi-home',to:"test"},
        { text: '사용자 인증 관리', icon: 'mdi-account-multiple',to:"authuser"},
        { text: '수신번호관리', icon: 'mdi-history', to:"receive"},
        { text: '번호대역관리', icon: 'mdi-home', to:"rgbandwidth"},
    ],
  }),
   methods: {
     switchPage(page) {
       if (page == "home") {
         page = "";
      }
      this.$router.push(`/${page}`).catch(error => {
      if (error.name != "NavigationDuplicated") {
      throw error;
     }})
      this.drawer = false;
    },
    login(){
      if (this.checkForm()){
        console.log('로그인 시도')
        console.log(this.credential2)
   
        axios_common.post('/api/auth/login', this.credential2)
          .then((res)=>{
            console.log('로그인 성공')
            this.$store.dispatch('login', res.data)
            this.loginMenu = false;
          })
          .catch((e)=>{
            console.log(e)
            this.errors.push('로그인 실패')
          })
        }
    },
    checkForm(){
        this.errors = []
        if (!this.credential2.username) {this.errors.push('아이디를 입력해주세요.')}
        if (this.errors.length === 0) {return true}
    },
    open(){
      if(this.isAuthenticated){
        this.drawer = !this.drawer ;
      }
      else{
        alert("로그인 후 이용해주세요")
      }
    },
    
    logout(){
      this.$store.dispatch('logout')
      this.$router.push('/')
    },



  },
};
</script>
