   <template>
<!-- <v-container fluid ma-0 pa-0> -->
  <v-card height="100%">
   <v-data-table
    :headers="headers"
    :items="filteritems"
    item-key="Seq"
    class="elevation-1"
  > 
    <template v-slot:top>
       <v-container fluid>
                <v-row>
                   <v-col cols="2">
                        <v-row class="pa-3">
                            <v-select
                                    :items="countryNumberList"
                                    v-model="countryNumberFilterValue"
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
                                    v-model="localNumberFilterValue"
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
                                    v-model="baseNumberFilterValue"
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
                                    v-model="filters.category"
                                    label="Fax/Mo"
                                    dense
                                    outlined
                            ></v-select>
                        </v-row>
                    </v-col>
                    <v-col cols="2">
                      <v-row class="pa-3">
                        <v-btn @click="filteredItems">검색</v-btn>
                      </v-row>
                    </v-col>
                </v-row>
            </v-container>

      <v-toolbar flat color="white">
        <v-toolbar-title>번호대역 할당/변경</v-toolbar-title>
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
// import axios from "axios"
  export default {
    data: () => ({
      dialog: false,
      countryNumberList:[
        {text: '82',value:"82"}
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
          {text: "671", value: "671"}
        ],
      categoryList:[
        {text: "All", value: "none"},
        {text: "FAX", value: "FAX"},
        {text: "MO", value: "MO"},
      ],
      serviceList:[
        {text: "All", value: "none"},
        {text: "뿌리오", value: "뿌리오"},
        {text: "엔팩스", value: "엔팩스"},
      ],
      countryNumberFilterValue : 'none',
      localNumberFilterValue : 'none',
      baseNumberFilterValue : 'none',
     headers: [
        { text: '카테고리', value: 'category'},
        { text: '대역번호', value: 'bandNumber' },
        { text: '대역시작지점', value: 'startNumber' },
        { text: '대역끝지점', value: 'endNumber' },
        { text: '서비스', value: 'service'},
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      items: [],
      filteritems:[],
      filters:{
        category: [],
        // service: [],
        bandNumber: [],
      },
      editedIndex: -1,
      editedItem: {
        category: 'none',
        bandNumber: '82-0303-671',
        startNumber: '0000',
        endNumber: '9999',
        service: 'none',
      },
      defaultItem: {
        category: 'none',
        bandNumber: '82-0303-671',
        startNumber: '0000',
        endNumber: '9999',
        service: 'none',
      },
    }),

    computed: {
      // headers(){
      //   return[
      //   {
      //     text: 'Seq',
      //     align: 'start',
      //     sortable: false,
      //     value: 'seq',
      //   },
      //   { text: '대역번호', value: 'bandNumber' },
      //   { text: '수신번호', value: 'receiveNumber' },
      //   { text: '서비스', value: 'service', filter: this.serviceFilter},
      //   { text: '카테고리', value: 'category', filter: this.categoryFilter},
      //   { text: 'Actions', value: 'actions', sortable: false },
      //   ]
      // },
      
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
      filteredItems() {

        var temp =""

        //fix me
        //좀더 생각해보고 수정이 필요.... 일단 작성
        if(this.countryNumberFilterValue != "none")
        {
          temp += this.countryNumberFilterValue + "-"
        }
        if(this.localNumberFilterValue != "none"){
            temp += this.localNumberFilterValue + "-"
        }
        if(this.baseNumberFilterValue != "none"){
            temp += this.baseNumberFilterValue
        }
        if(temp==="")
        temp="none"
        
        
        this.filters.bandNumber =temp
        console.log(this.filters.bandNumber)
        //d는 현재 테이블에 있는 값
        this.filteritems = this.items.filter(d => 
      {
        // console.log(d)

        //f는 필터 목록들
        return Object.keys(this.filters).every(f => 
        {

          // console.log(f)
          return this.filters[f].length < 1|| this.filters[f] == "none" || d[f].includes(this.filters[f])
        } )
      })
    },

      initialize () {
        this.items = [
          {
            category: 'FAX',
            bandNumber: '82-0303-671',
            startNumber: '0000',
            endNumber: '3000',
            service: '뿌리오',
          },
          {
           category: 'FAX',
            bandNumber: '82-0303-671',
            startNumber: '3001',
            endNumber: '7000',
            service: '뿌리오',
          },
          {
            category: 'FAX',
            bandNumber: '82-0303-671',
            startNumber: '7001',
            endNumber: '9999',
            service: '엔팩스',
          },
          {
            category: 'MO',
            bandNumber: '82-0303-3444',
            startNumber: '0000',
            endNumber: '3000',
            service: '엔팩스',
          },{
            category: 'MO',
            bandNumber: '82-0303-3444',
            startNumber: '3001',
            endNumber: '6000',
            service: '엔팩스',
          },
          {
            category: 'MO',
            bandNumber: '82-0303-3444',
            startNumber: '6001',
            endNumber: '9999',
            service: '엔팩스',
          },
        
        ],
        this.filteritems = this.items
      },
    //   categoryFilter(value) {
    //     // If this filter has no value we just skip the entire filter.
    //     console.log(value + "," + this.categoryFilterValue)
    //     if (!this.categoryFilterValue) {
    //       return true;
    //     }
    //     // Check if the current loop value (The calories value)
    //     // equals to the selected value at the <v-select>.
    //     return value === this.categoryFilterValue;
    //   },

    //  serviceFilter(value) {
    //     // If this filter has no value we just skip the entire filter.
    //     console.log(value + "," + this.serviceFilterValue)
    //     if (!this.serviceFilterValue) {
    //       return true;
    //     }
    //     // Check if the current loop value (The calories value)
    //     // equals to the selected value at the <v-select>.
    //     return value === this.serviceFilterValue;
    //   },

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