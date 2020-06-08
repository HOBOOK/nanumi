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
                            outlined="outlined"
                            v-on:change="changeLocalNumber"
                            ></v-select>
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
                                <span class="headline">{{formTitle}}</span>
                                </v-card-title>

                                <v-card-text>
                                <v-container>
                                    <v-row align="center" justify="center">
                                        <v-col cols="3" class="pa-0">
                                            <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.countryNo" label="국가번호" required minlength="2" maxlength="2"></v-text-field>
                                        </v-col>
                                        <v-col cols="1"  class="pr-0 pl-4"> - </v-col>
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.localNo" label="지역번호" required minlength="3" maxlength="4"></v-text-field>
                                        </v-col>                       
                                        <v-col cols="1"  class="pr-0 pl-4"> - </v-col>
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.baseNo" label="대역" required minlength="3" maxlength="4"></v-text-field>
                                        </v-col>
                                    </v-row>
                                    <v-row align="center" justify="center">
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field v-model="editedItem.startNo" label="시작" required minlength="4" maxlength="4"></v-text-field>
                                        </v-col>
                                        <v-col cols="1"  class="pr-0 pl-4"> ~ </v-col>
                                        <v-col cols="3"  class="pa-0">
                                            <v-text-field v-model="editedItem.endNo" label="끝" required minlength="4" maxlength="4"></v-text-field>
                                        </v-col>

                                        <v-col cols="3"  class="pa-0">
                                            <v-select
                                                :items="categoryList"
                                                v-model="editedItem.category"
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

              

                    <!-- 상세 다이얼로그 부분 시작 -->
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
                                            <v-text-field
                                                v-model="assignmentForm.startNo"
                                                required minlength="4" maxlength="4"
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
                                                required minlength="4" maxlength="4"
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
                                <v-btn color="blue darken-1" text="text" @click="complete">완료</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                    <!-- 상세 다이얼로그 부분 끝 -->
                </v-toolbar>
            </template>
            <template v-slot:body="{ items }">
              <tbody>
                <tr v-for="item in items" :key="item.serialNo" >
                  <td @click="detail(item.serialNo, item.startNo, item.endNo)">{{ item.category }}</td>
                  <td @click="detail(item.serialNo, item.startNo, item.endNo)">{{ item.serialNo }}</td>
                  <td @click="detail(item.serialNo, item.startNo, item.endNo)">{{ item.startNo }}</td>
                  <td @click="detail(item.serialNo, item.startNo, item.endNo)">{{ item.endNo }}</td>
                  <td @click="detail(item.serialNo, item.startNo, item.endNo)">{{ item.status }}</td>
                  <td>
                    <v-icon small="small" class="mr-2" @click="editItem(item)">
                    mdi-pencil
                </v-icon>
                <v-icon small="small" @click="deleteItem(item)">
                    mdi-delete
                </v-icon>
                  </td>
                </tr>
              </tbody>
            </template>
            
        </v-data-table>
    </v-card>
    <!-- </v-container> -->
</template>
<script>
    import axios_common from '../axios_common';
    import {mapGetters} from 'vuex';

    export default {
        mounted() {
            axios_common.get('/api/band', this.requestHeader)
                .then((res) => {
                    console.log(res.data)
                    this.items = res.data
                    this.filteritems = res.data
                })
                .catch((e) => {
                    console.log(e)
                })
            axios_common.get("/api/band/local", this.requestHeader)
                .then((res) => {
                    this.localNumberList = res.data
                }).catch((e)=> {
                    console.log(e)
                })
            },
        data: () => ({
            dialog: false,
            dialogInput: false,
            editedIndex: -1,

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
                }, {
                    text: 'Actions',
                    value: 'actions',
                    sortable: false
                }
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
            editedItem: {
                countryNo: '',
                localNo: '',
                baseNo: '',
                startNo: '',
                endNo: '',
                category : '',
                seqNo: '',
                status: '',
            },
        }),

        computed: {
            ...mapGetters(['isAuthenticated', 'requestHeader', 'userId', 'username']),
            formTitle() {
                return this.editedIndex === -1? '신규대역 등록' : '번호대역 수정'
            },
        },

        watch: {
            dialog(val) {
                val || this.close()
            }
        },

        created() {
        },

        methods: {
            changeLocalNumber(){
                this.baseNumberFilterValue =""
                axios_common.get("/api/band/base/"+this.localNumberFilterValue, this.requestHeader)
                .then((res) => {
                    this.baseNumberList = res.data
                }).catch((e)=> {
                    console.log(e)
                })
            },
          detail(serialNo, startNo, endNo){
            this.dialog = true
            this.curSerialNo= serialNo
            this.curStartNo= startNo
            this.curEndNo= endNo
            this.assignmentForm.serialNo = serialNo
            axios_common.get("/api/assignments/"+serialNo, this.requestHeader)
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
            axios_common.post('/api/assignments', this.assignmentForm, this.requestHeader)
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
                    this.filteritems = this.items.filter(d => {
                            // console.log(d) f는 필터 목록들
                            return Object.keys(this.filters).every(f => {
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
            editItem(item) {
                this.editedIndex = this.items.indexOf(item)
                this.editedItem = Object.assign({}, item)
                this.dialogInput = true
            },

            deleteItem(item) {
                const index = this.items.indexOf(item)
                const temp = this.editedItem.countryNo+"-"+this.editedItem.localNo+"-"+this.editedItem.baseNo
               
                if(confirm('정말로 지우시겠습니까?')){
                    this.editedItem = Object.assign({}, item)
                    axios_common.delete('/api/band/' + temp ,
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
                // confirm('정말로 지우시겠습니까?') && this.items.splice(index, 1)
            },

            close() {
                this.dialogInput = false
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
            complete(){
                this.dialog = false
            },
            save() {
                this.bandForm={
                    baseNo:this.editedItem.baseNo,
                    category:this.editedItem.category,
                    countryNo:this.editedItem.countryNo,
                    endNo:this.editedItem.endNo,
                    localNo:this.editedItem.localNo,
                    seqNo: this.editedItem.seqNo,
                    serialNo:this.editedItem.countryNo+"-"+this.editedItem.localNo+"-"+this.editedItem.baseNo,
                    startNo:this.editedItem.startNo,
                    status: this.editedItem.status,
                  }
                axios_common.put('/api/band/'+this.bandForm.serialNo,this.bandForm ,this.requestHeader)
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
                if (this.editedIndex > -1) {
                    Object.assign(this.items[this.editedIndex], this.editedItem)
                } else {
                    this.items.push(this.editedItem)
                }
                this.close()
            },
            add (){
                this.bandForm={
                    serialNo:this.editedItem.countryNo+"-"+this.editedItem.localNo+"-"+this.editedItem.baseNo,
                    countryNo:this.editedItem.countryNo,
                    localNo:this.editedItem.localNo,
                    baseNo:this.editedItem.baseNo,
                    startNo:this.editedItem.startNo,
                    endNo:this.editedItem.endNo,
                    category:this.editedItem.category
                  }

                axios_common.post('/api/band',this.bandForm ,this.requestHeader)
                .then((res)=>{
                    this.bandForm = res.data;
                    this.items.push(this.bandForm)
                    this.close()
                })
                .catch((e) => {
                    console.log(e)
                    console.log(this.bandForm)
                })
            },
        }
    }

</script>