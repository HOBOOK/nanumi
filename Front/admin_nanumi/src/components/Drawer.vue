// @author jsw
<template>
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
            <v-list-item-title class="title">{{this.userId}}</v-list-item-title>
            <v-list-item-subtitle>admin4@daou.com</v-list-item-subtitle>
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
              <v-list-item-title v-text="item.text"></v-list-item-title>
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
              <v-list-item-title>번호대역등록</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        <v-list-item>
            <v-list-item-icon>
              <v-icon></v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>번호대역 할당/변경</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          </v-list-group>
    
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
</template>
<script>
import {mapGetters} from 'vuex';
export default {
    name: 'DashboardCoreDrawer',
    
    data: () => ({
      selected: 0,
      items: [
        { text: '대시보드', icon: 'mdi-home'},
        { text: '사용자 인증 관리', icon: 'mdi-account-multiple'},
        { text: '수신번호관리', icon: 'mdi-history'},
        { text: '번호대역관리', icon: 'mdi-home'}
      ],
    }),

    computed: {
      ...mapGetters(['isAuthenticated', 'requestHeader', 'userId', 'username']),
    },

    methods: {
      
    },
    

  }
</script>
