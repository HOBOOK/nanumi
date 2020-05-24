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
         
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.seq" label="Seq"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.id" label="사용자ID"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.role" label="권한"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.authKey" label="인증키"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.createDT" label="생성일"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.expireDT" label="만료일"></v-text-field>
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
  export default {
    data: () => ({
      dialog: false,
      search: '',
      headers: [
        {
          text: 'Seq',
          align: 'start',
          sortable: false,
          value: 'seq',
        },
        { text: '사용자ID', value: 'id' },
        { text: '권한', value: 'role' },
        { text: '인증키', value: 'authKey' },
        { text: '생성일', value: 'createDT' },
        { text: '만료일', value: 'expireDT' },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      items: [],
      editedIndex: -1,
      editedItem: {
        seq:1,
        id: '',
        role: 0,
        authKey: 0,
        createDT: 0,
        expireDT: 0,
      },
      defaultItem: {
        seq:1,
        id: '',
        role: 0,
        authKey: 0,
        createDT: 0,
        expireDT: 0,
      },
    }),

    computed: {
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
        this.items = [
          {
            seq: 1,
            id: 'tmddn',
            role: 'admin',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 2,
            id: 'rudgh',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 3,
            id: 'clals',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 4,
            id: 'jsp',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 5,
            id: 'daou',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 6,
            id: 'ppurio',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 7,
            id: 'sabangnet',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 8,
            id: 'dothebest',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 9,
            id: 'wonth',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
          {
            seq: 10,
            id: 'clclek',
            role: 'user',
            authKey: "p%B@?g6dm8zK,^2~9D`O2xK=Jvsv9",
            createDT: "2020-05-20" ,
            expireDT: "2030-05-20" ,
          },
        ]
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
        if (this.editedIndex > -1) {
          Object.assign(this.items[this.editedIndex], this.editedItem)
        } else {
          this.items.push(this.editedItem)
        }
        this.close()
      },
    },
  }
</script>