   <template>
<!-- <v-container fluid ma-0 pa-0> -->
  <v-card height="100%">
   <v-data-table
    :headers="headers"
    :items="items"
    class="elevation-1"
  > 
    <template v-slot:top>
       <v-container fluid>
                <v-row>
                   <v-col cols="2">
                        <v-row class="pa-3">
                            <v-select
                                    :items="countryNumberList"
                                    v-model="countryNumberFilter"
                                    label="국제번호"
                                    dense
                                    outlined
                            ></v-select>
                        </v-row>
                    </v-col>
                    <v-col cols="2">
                        <v-row class="pa-3">
                            <v-select
                                    :items="localNumberList"
                                    v-model="localNumberFilter"
                                    label="지역번호"
                                    dense
                                    outlined
                            ></v-select>
                        </v-row>
                    </v-col>

                    <v-col cols="2">
                        <v-row class="pa-3">
                            <!-- Filter for calories -->
                             <v-select
                                    :items="baseNumberList"
                                    v-model="baseNumberFilter"
                                    label="국번"
                                    dense
                                    outlined
                            ></v-select>
                        </v-row>
                    </v-col>
                    <v-col cols="2">
                        <v-row class="pa-3">
                            <!-- Filter for calories -->
                             <v-select
                                    :items="categoryList"
                                    v-model="categoryFilter"
                                    label="Fax/Mo"
                                    dense
                                    outlined
                            ></v-select>
                        </v-row>
                    </v-col>
                    <v-col cols="2">
                        <v-row class="pa-3">
                            <!-- Filter for calories -->
                             <v-select
                                    :items="serviceList"
                                    v-model="serviceFilter"
                                    label="뿌리오/엔팩스"
                                    dense
                                    outlined
                            ></v-select>
                        </v-row>
                    </v-col>
                    <v-col cols="2">
                      <v-row class="pa-3">
                        <v-btn>검색</v-btn>
                      </v-row>
                    </v-col>
                </v-row>
            </v-container>

      <v-toolbar flat color="white">
        <v-toolbar-title>수신 번호 관리</v-toolbar-title>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>

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
                    <v-text-field v-model="editedItem.bandNumber" label="대역번호"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.receiveNumber" label="수신번호"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.service" label="서비스업체"></v-text-field>
                  </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field v-model="editedItem.category" label="카테고리"></v-text-field>
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
      countryNumberList:[
        {text: '+82',value:82}
      ],
      localNumberList:[
        {text: "031", value: "031"},
        {text: "0303", value: "0303"},
        {text: "02", value: "02"},
      ],
      baseNumberList: [
          {text: "3441", value: "3441"},
          {text: "3442", value: "3442"},
          {text: "3443", value: "3443"},
          {text: "3444", value: "3444"},
        ],
      categoryList:[
        {text: "FAX", value: "FAX"},
        {text: "MO", value: "MO"},
      ],
      serviceList:[
        {text: "뿌리오", value: "ppurio"},
        {text: "엔팩스", value: "enfax"},
      ],
      baseNumberFilter: null,
      categoryFilter : null,
      serviceFilter : null,
      headers: [
        {
          text: 'Seq',
          align: 'start',
          sortable: false,
          value: 'seq',
        },
        { text: '대역번호', value: 'bandNumber' },
        { text: '수신번호', value: 'receiveNumber' },
        { text: '서비스', value: 'service' },
        { text: '카테고리', value: 'category' },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      items: [],
      editedIndex: -1,
      editedItem: {
        seq:1,
        bandNumber: '82-0303-671',
        receiveNumber: '0000',
        service: 'none',
        category: 'none',
      },
      defaultItem: {
        seq:1,
        bandNumber: '82-0303-671',
        receiveNumber: '0000',
        service: 'none',
        category: 'none',
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
            seq:1,
            bandNumber: '82-0303-671',
            receiveNumber: '0000',
            service: '엔팩스',
            category: 'FAX',
          },
          {
            seq:2,
            bandNumber: '82-0303-671',
            receiveNumber: '0001',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq:3,
            bandNumber: '82-0303-671',
            receiveNumber: '0002',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq:4,
            bandNumber: '82-0303-671',
            receiveNumber: '0003',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq:5,
            bandNumber: '82-0303-671',
            receiveNumber: '0004',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq:6,
            bandNumber: '82-0303-671',
            receiveNumber: '0005',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq: 7,
            bandNumber: '82-0303-671',
            receiveNumber: '0006',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq: 8,
            bandNumber: '82-0303-671',
            receiveNumber: '0007',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq: 9,
            bandNumber: '82-0303-671',
            receiveNumber: '0008',
            service: '뿌리오',
            category: 'MO',
          },
          {
            seq: 10,
            bandNumber: '82-0303-671',
            receiveNumber: '0009',
            service: '뿌리오',
            category: 'MO',
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