<template>
  <v-app>
    <v-app-bar
      color="deep-purple"
      dark
      max-height=64px
    >
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Title</v-toolbar-title>
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

          <v-list-group>
               <template v-slot:activator>
           <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>         
          <v-list-item-content>
            <v-list-item-title>번호대역관리</v-list-item-title>
          </v-list-item-content>
        </template>

         <v-list-item>
            <v-list-item-icon>
              <v-icon></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title @click="switchPage('rgbandwidth')">번호대역등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        <v-list-item>
            <v-list-item-icon>
              <v-icon></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title @click="switchPage('test')">번호대역 할당/변경</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          </v-list-group>
    
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
    <v-content height="100%">
      <router-view :key="$route.fullPath"></router-view>
    </v-content>
  </v-app>
</template>


<script>

export default {
  name: 'App',

  components: {
  },

  data: () => ({
    drawer:false,
     selected: 0,
      items: [
        { text: '대시보드', icon: 'mdi-home',to:"test"},
        { text: '사용자 인증 관리', icon: 'mdi-account-multiple',to:"authuser"},
        { text: '수신번호관리', icon: 'mdi-history', to:"receive"},
      ],
  }),
   methods: {
     switchPage(page) {
      if (page == "home") {
        page = "";
      }
      this.drawer = false;
      this.$router.push(`/${page}`);
    },

  },
};
</script>
