   <template>
<!-- <v-container fluid ma-0 pa-0> -->
  <v-card height="100%">
   <v-data-table
    :headers="headers"
    :items="items"
    class="elevation-1"
    :search="search"
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
                    <v-text-field v-model="editedItem.id" label="사용자ID"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.roleCd" label="권한"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.create_dt" label="생성일"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.expireDt" label="만료일"></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="save">Save</v-btn>
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
    <template v-slot:no-data>
      <v-btn color="primary" @click="initialize">Reset</v-btn>
    </template>
  </v-data-table>
  </v-card>
<!-- </v-container> -->
</template>
<script>
import axios from "axios"
import { mapGetters } from 'vuex';
  export default {
    mounted () {
       axios.get('http://localhost:8080/api/admin/account', this.requestHeader)
          .then((res)=>{
            this.items = res.data
            console.log(this.items)
          })
          .catch((e)=>{
            console.log(e)
          })
    },
    data: () => ({
      dialog: false,
      search: '',
      headers: [
        { text: '사용자ID', value: 'id' },
        { text: '권한', value: 'roleCd' },
        { text: '인증키', value: 'accessToken' },
        { text: '생성일', value: 'create_dt' },
        { text: '만료일', value: 'expireDt' },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      items: [],
      editedIndex: -1,
      editedItem: {
        id: '',
        roleCd: 0,
        create_dt: 0,
        expireDt: 0,
      },
      defaultItem: {
        id: '',
        roleCd: "user",
        create_dt: 0,
        expireDt: 0,
      },
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
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      },
    },

    watch: {
      dialog (val) {
        val || this.close()
      },
    },

    created () {
      this.initialize()
    },

    methods: {
      initialize () {
        // this.items = [
        //   {
        //     seq: 1,
        //     id: 'tmddn',
        //     role: 'admin',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 2,
        //     id: 'rudgh',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 3,
        //     id: 'clals',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 4,
        //     id: 'jsp',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 5,
        //     id: 'daou',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 6,
        //     id: 'ppurio',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 7,
        //     id: 'sabangnet',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 8,
        //     id: 'dothebest',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 9,
        //     id: 'wonth',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        //   {
        //     seq: 10,
        //     id: 'clclek',
        //     role: 'user',
        //     authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
        //     createDT: "2020-05-20" ,
        //     expireDT: "2030-05-20" ,
        //   },
        // ]
      },

      editItem (item) {
        this.editedIndex = this.items.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      deleteItem (item) {
        const index = this.items.indexOf(item)
        confirm('정말로 지우시겠습니까?') && this.items.splice(index, 1)
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      save () {
          axios.post('http://localhost:8080/api/admin/account',this.editedItem ,this.requestHeader)
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
    },
  }
</script>