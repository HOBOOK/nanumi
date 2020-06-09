// @author jsw
<template>
  <v-card height="100%">
   <v-data-table
    :headers="headers"
    :items="items"
    class="elevation-1"
    :search="search"
    :loading="loading"
  >
    <template v-slot:top>
      <v-toolbar flat color="white">
        <v-toolbar-title>사용자 인증 관리</v-toolbar-title>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>
    
        <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
        <v-dialog v-model="dialog" max-width="500px">
          <template v-slot:activator="{ on }">
            <v-btn color="primary" dark class="mb-2" v-on="on">사용자 추가</v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.id" label="사용자ID"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4" v-if="editedIndex===-1">
                    <v-text-field v-model="editedItem.pwd" type="password" label="비밀번호"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.svcNm" label="업체"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-select
                          :items="roleList"
                          v-model="editedItem.roleCd"
                          label="권한"
                          dense
                          outlined>
                          </v-select>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">취소</v-btn>
              <v-btn color="blue darken-1" text @click="save" v-if="editedIndex!==-1">저장</v-btn>
              <v-btn color="blue darken-1" text @click="add" v-if="editedIndex===-1">추가</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon
        small
        class="mr-2"
        @click="editItem(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        small
        @click="deleteItem(item)"
      >
        mdi-delete
      </v-icon>
    </template>
  </v-data-table>
  </v-card>
<!-- </v-container> -->
</template>
<script>
import axios_common from "../axios_common"
import { mapGetters } from 'vuex';

  export default {
    mounted () {
       axios_common.get('/api/admin/account', this.requestHeader)
          .then((res)=>{
            this.items = res.data
            this.loading = false
          })
          .catch((e)=>{
            console.log(e)
          })
    },
    data: () => ({
      dialog: false,
      loading: true,
      search: '',
      headers: [
        { text: '사용자ID', value: 'id' },
        { text: '업체', value: 'svcNm'},
        { text: '권한', value: 'roleCd' },
        { text: '생성일', value: 'create_dt' },
        { text: '', value: 'actions', sortable: false },
      ],
      items: [],
      editedIndex: -1,
      editedItem: {
        id: '',
        roleCd: 'USER',
      },
      defaultItem: {
        id: '',
        roleCd: "USER",
      },
      roleList:[
        {text: "USER", value: "USER"},
        {text: "ADMIN", value: "ADMIN"}
      ],
    }),

    computed: {
    ...mapGetters([
      'isAuthenticated',
      'requestHeader',
      'userId',
      'username'
    ])
  ,
      formTitle () {
        return this.editedIndex === -1 ? '새로운 계정 생성' : '사용자 정보 수정'
      },
    },

    watch: {
      dialog (val) {
        val || this.close()
      },
    },



    methods: {
      editItem (item) {
        this.editedIndex = this.items.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteItem (item) {
        const index = this.items.indexOf(item)

        if(confirm('정말로 지우시겠습니까?')){
          this.editedItem = Object.assign({}, item)
          axios_common.delete(
            '/api/admin/account',
            {
              headers: {
                Authorization: this.requestHeader.headers.Authorization
              },
              data: this.editedItem
            }
          ).then((res)=>{
            console.log(res)
            this.items.splice(index, 1)
            this.editedItem = this.defaultItem
          })
          .catch((res)=>{
            this.editedItem = this.defaultItem
            console.log('error > ' + res)
          })
        }
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      save () {
          axios_common.put('/api/admin/account',this.editedItem ,this.requestHeader)
          .then((res)=>{
            console.log(res)
            if (this.editedIndex > -1) {
           Object.assign(this.items[this.editedIndex], this.editedItem)
         } else {
            this.items.push(this.editedItem)
         }
        this.close()
          })
          .catch((e)=>{
            console.log(e)
          })
      },

      add (){
        axios_common.post('/api/admin/account',this.editedItem ,this.requestHeader)
          .then((res)=>{
            console.log(res.data)
            this.editedItem = res.data;
            this.editedItem.pwd = '';
            this.items.push(this.editedItem)
            this.close()
          })
          .catch((e)=>{
            console.log(e)
          })
      },
    },
  }
</script>