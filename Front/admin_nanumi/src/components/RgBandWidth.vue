<template>
    <!-- <v-container fluid ma-0 pa-0> -->
    <v-card height="100%">
        <v-container fluid="fluid">
            <v-row>
                <v-col cols="2">
                    <v-row class="pa-3">
                        <v-select
                            :items="countryNumberList"
                            v-model="countryNumberFilterValue"
                            label="국제번호"
                            dense="dense"
                            outlined="outlined"></v-select>
                    </v-row>
                </v-col>
                <v-col cols="2">
                    <v-row class="pa-3">
                        <v-select
                            :items="localNumberList"
                            v-model="localNumberFilterValue"
                            label="지역번호"
                            dense="dense"
                            outlined="outlined"></v-select>
                    </v-row>
                </v-col>

                <v-col cols="2">
                    <v-row class="pa-3">
                        <v-select
                            :items="baseNumberList"
                            v-model="baseNumberFilterValue"
                            label="국번"
                            dense="dense"
                            outlined="outlined"></v-select>
                    </v-row>
                </v-col>
                <v-col cols="2">
                    <v-row class="pa-3">
                        <v-btn @click="filteredItems">검색</v-btn>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
        <v-data-table
            :headers="headers"
            :items="filteritems"
            item-key="serialNo"
            class="elevation-1">
            
            <template v-slot:top>
                <v-toolbar flat="flat" color="white">
                    <v-toolbar-title>번호대역 등록</v-toolbar-title>
                    <v-divider class="mx-4" inset="inset" vertical="vertical"></v-divider>
                    <v-spacer>
                    </v-spacer>

                    <!-- 신규 대역 입력 다이얼로그 부분 시작 -->
                    <v-dialog v-model="dialogInput" max-width="500px">
                            <template v-slot:activator="{ on }">
                                <v-btn color="primary" dark class="mb-2" v-on="on">신규등록</v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                <span class="headline">신규대역 등록</span>
                                </v-card-title>

                                <v-card-text>
                                <v-container>
                                    <v-row align="center" justify="center">
                                        <v-col cols="3" class="pa-0">
                                            <v-text-field v-model="countryNo" label="국가번호" required minlength="2" maxlength="2"></v-text-field>
                                        </v-col>
                                        <v-col cols="1"  class="pr-0 pl-4"> - </v-col>
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field v-model="localNo" label="지역번호" required minlength="4" maxlength="4"></v-text-field>
                                        </v-col>                       
                                        <v-col cols="1"  class="pr-0 pl-4"> - </v-col>
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field v-model="baseNo" label="대역" required minlength="4" maxlength="4"></v-text-field>
                                        </v-col>
                                    </v-row>
                                    <v-row align="center" justify="center">
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field v-model="startNo" label="시작" required minlength="4" maxlength="4"></v-text-field>
                                        </v-col>
                                        <v-col cols="1"  class="pr-0 pl-4"> ~ </v-col>
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field v-model="endNo" label="끝" required minlength="4" maxlength="4"></v-text-field>
                                        </v-col>

                                        <v-col cols="3"  class="pa-0">
                                            <v-select
                                                :items="categoryList"
                                                v-model="category"
                                                label="카테고리"
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
                    <!-- 신규 대역 입력 다이얼로그 부분 끝 -->    

                    <!-- 다이얼로그 부분 시작 -->
                    <v-dialog v-model="dialog" max-width="1500px">
                        <v-card>
                            <v-card-title>
                                <span class="headline">번호 대역 등록 
                                    {{curSerialNo}}( 할당 가능 범위 {{curStartNo}} ~ {{curEndNo}} ) </span>
                                    
                            </v-card-title>
                            <v-card-subtitle>
                                <v-row>
                                    <v-col cols="2">
                                        <v-row class="pa-3">
                                            <!-- 텍스트 필드로 내리고 주석 -->
                                            <v-text-field
                                                v-model="assignmentForm.startNo"
                                                label="시작번호 4자리"
                                                single-line="single-line"
                                                hide-details="hide-details"
                                                outlined="outlined"
                                                dense="dense"></v-text-field>
                                        </v-row>
                                    </v-col>
                                    <v-col cols="2">
                                        <v-row class="pa-3">
                                            <v-text-field
                                                v-model="assignmentForm.endNo"
                                                label="끝번호 4자리"
                                                single-line="single-line"
                                                hide-details="hide-details"
                                                outlined="outlined"
                                                dense="dense"></v-text-field>
                                        </v-row>
                                    </v-col>

                                    <v-col cols="2">
                                        <v-row class="pa-3">
                                            <v-text-field
                                                v-model="assignmentForm.svcId"
                                                label="사용자ID"
                                                single-line="single-line"
                                                hide-details="hide-details"
                                                outlined="outlined"
                                                dense="dense"
                                                @keydown.enter="postAssignments()"
                                                ></v-text-field>
                                        </v-row>
                                    </v-col>

                                    <v-col cols="2">
                                        <v-row class="pa-3">
                                            <v-btn @click="postAssignments()">대역할당</v-btn>
                                        </v-row>
                                    </v-col>

                                </v-row>
                            </v-card-subtitle>
                            <v-card-text>
                                <v-data-table
                                    :headers="assignmenHeaders"
                                    :items="assignmentItems"
                                    item-key="seqNo"
                                    class="elevation-1"></v-data-table>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="blue darken-1" text="text" @click="close">Cancel</v-btn>
                                <v-btn color="blue darken-1" text="text" @click="save">Save</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                    <!-- 다이얼로그 부분 끝 -->
                </v-toolbar>
            </template>
            <template v-slot:body="{ items }">
              <tbody>
                <tr v-for="item in items" :key="item.serialNo" @click="detail(item.serialNo, item.startNo, item.endNo)">
                  <td>{{ item.category }}</td>
                  <td>{{ item.serialNo }}</td>
                  <td>{{ item.startNo }}</td>
                  <td>{{ item.endNo }}</td>
                  <td>{{ item.status }}</td>
                </tr>
              </tbody>
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
    import {mapGetters} from 'vuex';

    export default {
        mounted() {
            axios
                .get('http://localhost:8080/api/band', this.requestHeader)
                .then((res) => {
                    // console.log(res.data)
                    this.items = res.data
                    this.filteritems = res.data
                    // console.log(this.items)/
                })
                .catch((e) => {
                    console.log(e)
                })
            },
        data: () => ({
            dialog: false,
            dialogInput: false,
            countryNo:'',
            localNo:'',
            baseNo:'',
            startNo:'',
            endNo:'',
            category:'',

            assignmentItems:[],
            assignmentForm:{
              serialNo:"",
              startNo:"",
              endNo:"",
              svcId:"",
            }
            ,
            countryNumberList: [
                {
                    text: '82',
                    value: "82"
                }
            ],
            localNumberList: [
                {
                    text: "031",
                    value: "031"
                }, {
                    text: "0303",
                    value: "0303"
                }, {
                    text: "02",
                    value: "02"
                }
            ],
            baseNumberList: [
                {
                    text: "3441",
                    value: "3441"
                }, {
                    text: "3442",
                    value: "3442"
                }, {
                    text: "3443",
                    value: "3443"
                }, {
                    text: "3444",
                    value: "3444"
                }, {
                    text: "671",
                    value: "671"
                }
            ],
            bandForm:{
              serialNo:"",
              countryNo:"",
              localNo:"",
              baseNo:"",
              startNo:"",
              endNo:"",
              category:"",
            },
            categoryList:[
                {text: "NONE", value: "NONE"},
                {text: "FAX", value: "FAX"},
                {text: "MO", value: "MO"}
            ],

            countryNumberFilterValue: "82",
            localNumberFilterValue: 'none',
            baseNumberFilterValue: 'none',
            categoryFilterValue: 'none',
            curSerialNo:'',
            curStartNo:'',
            curEndNo:'',
            headers: [
                {
                    text: '카테고리',
                    value: 'category'
                }, {
                    text: '대역번호',
                    value: 'serialNo'
                }, {
                    text: '대역시작지점',
                    value: 'startNo'
                }, {
                    text: '대역끝지점',
                    value: 'endNo'
                }, {
                    text: '상태',
                    value: 'status'
                },
            ],
            assignmenHeaders: [
              {
                text: 'Seq',
                value: 'seqNo'
              },
                {
                    text: '대역번호',
                    value: 'serialNo'
                }, {
                    text: '대역시작지점',
                    value: 'startNo'
                }, {
                    text: '대역끝지점',
                    value: 'endNo'
                }, {
                    text: '할당일',
                    value: 'assignDt'
                }, {
                    text: '만료일',
                    value: 'expireDt'
                }, {
                    text: '사용자ID',
                    value: 'svcId'
                }, {
                    text: '상태',
                    value: 'status'
                },
            ],
            expanded: [],
            items: [],
            showresult: false,
            filteritems: [],
            filters: {
                category: [],
                serialNo: []
            },
            editedIndex: -1,
            editedItem: {
                category: 'none',
                bandNumber: '82-0303-671',
                startNumber: '0000',
                endNumber: '9999',
                service: 'none'
            },
            defaultItem: {
                category: 'none',
                bandNumber: '82-0303-671',
                startNumber: '0000',
                endNumber: '9999',
                service: 'none'
            }
        }),

        computed: {
            ...mapGetters(['isAuthenticated', 'requestHeader', 'userId', 'username']),
            formTitle() {
                return this.editedIndex === -1? 'New Item' : 'Edit Item'
            }
        },

        watch: {
            dialog(val) {
                val || this.close()
            }
        },

        created() {
            this.initialize()
        },

        methods: {
          detail(serialNo, startNo, endNo){
            this.dialog = true
            this.curSerialNo= serialNo
            this.curStartNo= startNo
            this.curEndNo= endNo
            this.assignmentForm.serialNo = serialNo
            axios.get("http://localhost:8080/api/assignments/"+serialNo, this.requestHeader)
            .then((res) => {
                    // console.log(res.data)
                    if(res.data !== ""){
                        this.assignmentItems = res.data  
                    }
                    else{
                      this.assignmentItems = []
                    }
                    
                })
                .catch((e) => {
                    console.log(e)
                })
          },
          postAssignments(){
            axios.post('http://localhost:8080/api/assignments', this.assignmentForm, this.requestHeader)
                .then((res) => {
                   this.assignmentForm={
                    serialNo:"",
                    startNo:"",
                    endNo:"",
                    svcId:"",
                  }
                  this.assignmentItems.push(res.data)
                  this.console.log(res)
                })
                .catch((e) => {
                    console.log(e)
                    console.log(this.assignmentForm)
                })
            },
            filteredItems() {
                if (this.localNumberFilterValue !== "none" && this.baseNumberFilterValue !== "none") {
                    this.showresult = true

                    var temp = ""

                    //fix me 좀더 생각해보고 수정이 필요.... 일단 작성
                    if (this.countryNumberFilterValue != "none") {
                        temp += this.countryNumberFilterValue + "-"
                    }
                    if (this.localNumberFilterValue != "none") {
                        temp += this.localNumberFilterValue + "-"
                    }
                    if (this.baseNumberFilterValue != "none") {
                        temp += this.baseNumberFilterValue
                    }
                    if (temp === "") 
                        temp = "none"

                    this.filters.serialNo = temp
                    console.log(this.filters.serialNo)
                    //d는 현재 테이블에 있는 값
                    this.filteritems = this
                        .items
                        .filter(d => {
                            // console.log(d) f는 필터 목록들
                            return Object
                                .keys(this.filters)
                                .every(f => {

                                    // console.log(f)
                                    return this
                                        .filters[f]
                                        .length < 1 || this
                                        .filters[f] == "none" || d[f]
                                        .includes(this.filters[f])
                                })
                        })
                } else {
                    alert('지역번호와 국번이 선택되어야합니다.')
                }

            },

            initialize() {
                this.items = [
                    {
                        category: 'FAX',
                        bandNumber: '82-0303-671',
                        startNumber: '0000',
                        endNumber: '3000',
                        service: '뿌리오'
                    }, {
                        category: 'FAX',
                        bandNumber: '82-0303-671',
                        startNumber: '3001',
                        endNumber: '7000',
                        service: '뿌리오'
                    }, {
                        category: 'FAX',
                        bandNumber: '82-0303-671',
                        startNumber: '7001',
                        endNumber: '9999',
                        service: '엔팩스'
                    }, {
                        category: 'MO',
                        bandNumber: '82-0303-3444',
                        startNumber: '0000',
                        endNumber: '3000',
                        service: '엔팩스'
                    }, {
                        category: 'MO',
                        bandNumber: '82-0303-3444',
                        startNumber: '3001',
                        endNumber: '6000',
                        service: '엔팩스'
                    }, {
                        category: 'MO',
                        bandNumber: '82-0303-3444',
                        startNumber: '6001',
                        endNumber: '9999',
                        service: '엔팩스'
                    }
                ],
                this.filteritems = this.items
            },
            editItem(item) {
                this.editedIndex = this
                    .items
                    .indexOf(item)
                this.editedItem = Object.assign({}, item)
                this.dialog = true
            },

            deleteItem(item) {
                const index = this
                    .items
                    .indexOf(item)
                confirm('정말로 지우시겠습니까?') && this
                    .items
                    .splice(index, 1)
            },

            close() {
                this.dialog = false
                this.assignmentForm ={
                  serialNo:"",
                  startNo:"",
                  endNo:"",
                  svcId:"",
                };
                this.$nextTick(() => {
                    this.editedItem = Object.assign({}, this.defaultItem)
                    this.editedIndex = -1
                })
            },

            save() {
                if (this.editedIndex > -1) {
                    Object.assign(this.items[this.editedIndex], this.editedItem)
                } else {
                    this
                        .items
                        .push(this.editedItem)
                }
                this.close()
            },
            add (){
                this.bandForm={
                    serialNo:this.countryNo+"-"+this.localNo+"-"+this.baseNo,
                    countryNo:this.countryNo,
                    localNo:this.localNo,
                    baseNo:this.baseNo,
                    startNo:this.startNo,
                    endNo:this.endNo,
                    category:this.category
                  }

                axios.post('http://localhost:8080/api/band',this.bandForm ,this.requestHeader)
                .then((res)=>{
                    this.serialNo="",
                    this.countryNo="",
                    this.localNo="",
                    this.baseNo="",
                    this.startNo="",
                    this.endNo="",
                    this.category="",
                    this.console.log(res)
                })
                .catch((e) => {
                    console.log(e)
                    console.log(this.bandForm)
                })
            },
        }
    }

</script>


// bandForm:{
//               serialNo:"",
//               countryNo:"",
//               localNo:"",
//               baseNo:"",
//               startNo:"",
//               endNo:"",
//               category:""
//             },