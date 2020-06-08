// @author jsw
<template>
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
                            v-on:change="changeLocalNumber"></v-select>
                    </v-row>
                </v-col>

                <v-col cols="2">
                    <v-row class="pa-3">
                        <v-select
                            :items="baseNumberList"
                            v-model="baseNumberFilterValue"
                            label="국번"
                            dense="dense"
                            outlined="outlined"
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
        <v-data-table
            :headers="headers"
            :items="filteritems"
            item-key="Seq"
            class="elevation-1"
            :loading="loading"
            v-if="showresult=== true"
            items-per-page:10="items-per-page:10"
            :page.sync="page"
            @page-count="pageCount = $event"
            hide-default-footer="hide-default-footer">
            <template v-slot:top>
                <v-toolbar flat="flat" color="white">
                    <v-toolbar-title>수신 번호 관리</v-toolbar-title>
                    <v-divider class="mx-4" inset="inset" vertical="vertical"></v-divider>
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
                                            <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.seqNo" label="Seq"></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="4">
                                            <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.serialNo" label="대역번호"></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="4">
                                            <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.receiveNo" label="수신번호"></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="4">
                                            <v-text-field v-model="editedItem.svcUserId" label="유저아이디"></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="4">
                                            <v-text-field :disabled="editedIndex!==-1" v-model="editedItem.updateDate" label="업데이트날짜"></v-text-field>
                                        </v-col>
                                    </v-row>
                                </v-container>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="blue darken-1" text="text" @click="close">Cancel</v-btn>
                                <v-btn color="blue darken-1" text="text" @click="save(editedItem.seqNo)">Save</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-toolbar>
            </template>
            <template v-slot:item.actions="{ item }">
                <v-icon small="small" class="mr-2" @click="editItem(item)">
                    mdi-pencil
                </v-icon>
                <v-icon small="small" @click="deleteItem(item)">
                    mdi-delete
                </v-icon>
            </template>
        </v-data-table>
        <v-pagination v-model="page" :length="pageCount" v-if="showresult=== true"></v-pagination>
    </v-card>
</template>
<script>
    import axios_common from "../axios_common"
    import {mapGetters} from 'vuex';
    export default {
        data: () => ({
            dialog: false,
            
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
                    text: "671",
                    value: "671"
                }, {
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
                }
            ],
            categoryList: [
                {
                    text: "All",
                    value: "none"
                }, {
                    text: "FAX",
                    value: "FAX"
                }, {
                    text: "MO",
                    value: "MO"
                }
            ],
            serviceList: [
                {
                    text: "All",
                    value: "none"
                }, {
                    text: "뿌리오",
                    value: "뿌리오"
                }, {
                    text: "엔팩스",
                    value: "엔팩스"
                }
            ],
            countryNumberFilterValue: '82',
            localNumberFilterValue: 'none',
            baseNumberFilterValue: 'none',
            headers: [
                {
                    text: 'Seq',
                    align: 'start',
                    sortable: false,
                    value: 'seqNo'
                }, {
                    text: '대역번호',
                    value: 'serialNo'
                }, {
                    text: '수신번호',
                    value: 'receiveNo'
                }, {
                    text: '사용자Id',
                    value: 'svcUserId'
                }, {
                    text: '승인날짜',
                    value: 'updateDate'
                }, {
                    text: 'Actions',
                    value: 'actions',
                    sortable: false
                }
            ],
            showresult: false,
            items: [],
            filteritems: [],
            filters: {
                category: [],
                service: [],
                bandNumber: []
            },
            editedIndex: -1,
            editedItem: {
                seqNo: 1,
                serialNo: '82-0303-671',
                receiveNo: '0000',
                svcUserId: 'id',
                updateDate: 'none'
            },
            defaultItem: {
                seqNo: 1,
                serialNo: '82-0303-671',
                receiveNo: '0000',
                svcUserId: 'id',
                updateDate: 'none'
            },
            loading: true,
            page: 1,
            pageCount: 0
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
        created() {},
        mounted() {
            axios_common.get("/api/band/local", this.requestHeader)
            .then((res) => {
                this.localNumberList = res.data
            }).catch((e)=> {
                console.log(e)
            })
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
            filteredItems() {
                this.loading = true
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

                    console.log(temp)
                    axios_common.get('/api/receptions/' + temp, this.requestHeader)
                        .then((res) => {
                            this.filteritems = res.data
                                console.log(res.data)
                        })
                        .catch((e) => {
                            console.log(e)
                        })
                        . finally(() => this.loading = false);

                    this.filters.bandNumber = temp
                    console.log(this.filters.bandNumber)
                    //d는 현재 테이블에 있는 값
                    this.filteritems = this
                        .items
                        .filter(d => {
                            // console.log(d) f는 필터 목록들
                            return Object.keys(this.filters).every(f => {
                                    // console.log(f)
                                    return this
                                        .filters[f]
                                        .length < 1 || this
                                        .filters[f] ==""
                                        .filters[f] == "none" || d[f]
                                        .includes(this.filters[f])
                                })
                        })
                } else {
                    alert('지역번호와 국번이 선택되어야합니다.')
                }

            },

            editItem(item) {
                this.editedIndex = this.filteritems.indexOf(item)
                this.editedItem = Object.assign({}, item)
                this.dialog = true
            },

            deleteItem(item) {
                var result = confirm('할당을 해제 하시겠습니까?')
                //yes
                if(result) {
                     this.editedIndex = this.filteritems.indexOf(item)
                this.editedItem = Object.assign({}, item)
                this.editedItem= {
                    seqNo: item.seqNo,
                    serialNo: item.serialNo,
                    receiveNo: item.receiveNo,
                    updateDate: item.updateDate,
                    svcUserId: "",
                },


                axios_common.put('/api/receptions/receive/'+item.seqNo, this.editedItem ,this.requestHeader)
                .then((res)=>{
                console.log(res)
                
                if (this.editedIndex > -1) {
                Object.assign(this.filteritems[this.editedIndex], this.editedItem)
                } else {
                this.filteritems.push(this.editedItem)
                }
                this.close()
                })
                .catch((e)=>{
                console.log(e)
                })
            }
               
            },

            close() {
                this.dialog = false
                this.$nextTick(() => {
                    this.editedItem = Object.assign({}, this.defaultItem)
                    this.editedIndex = -1
                })
            },

            save(seqNo) {
                // console.log(this.editedItem)
                
                axios_common.put('/api/receptions/receive/'+seqNo,this.editedItem ,this.requestHeader)
                .then((res)=>{
                this.editedItem=res.data
                
                if (this.editedIndex > -1) {
                Object.assign(this.filteritems[this.editedIndex], this.editedItem)
                } else {
                this.filteritems.push(this.editedItem)
                }
                this.close()
                })
                .catch((e)=>{
                console.log(e)
                })
            }
        }
    }
</script>