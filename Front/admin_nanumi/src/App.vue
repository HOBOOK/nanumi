<template>
  <v-app>
    <v-app-bar
      color="deep-purple"
      dark
    >
      <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

      <v-toolbar-title>Title</v-toolbar-title>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      absolute
      temporary
    >
      <v-list dense nav>
        <v-list-group
        v-for="item in items"
        :key="item.title"
        v-model="item.active"
        :prepend-icon="item.action"
        no-action
      >
    

      <v-list-item-content v-if="item.length == 0">
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>
          
           <template v-slot:activator>
          <v-list-item-content>
            <v-list-item-title v-text="item.title"></v-list-item-title>
          </v-list-item-content>
        </template>

        <v-list-item
          v-for="subItem in item.items"
          :key="subItem.title"

        >
          <v-list-item-content>
            <v-list-item-title v-text="subItem.title"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-group>
        </v-list>
    </v-navigation-drawer>
    <v-content>
      <HelloWorld/>
    </v-content>
  </v-app>
</template>


<script>

import HelloWorld from './components/HelloWorld';

export default {
  name: 'App',

  components: {
    HelloWorld,
  },

  data: () => ({
    drawer:false,
    items: [
          {
            action: 'local_activity',
            title: '대시보드',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'restaurant',
            title: '사용자인증관리',
            active: true,
            items: [],
          },
          {
            action: 'school',
            title: '번호대역관리',
            items: [
              { title: '번호대역 등록' },
              { title: '번호대역 할당/변경'}
            ],
          },
          {
            action: 'directions_run',
            title: '수신번호관리',
            items: [
              { title: '수신번호 할당/변경' },
            ],
          },
          {
            action: 'healing',
            title: 'Health',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'content_cut',
            title: 'Office',
            items: [
              { title: 'List Item' },
            ],
          },
          {
            action: 'local_offer',
            title: 'Promotions',
            items: [
              { title: 'List Item' },
            ],
          },
        ],
  }),
};
</script>
