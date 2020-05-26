<template>
  <div>
    <v-container fluid pa-0>
      <v-row>
        <v-col class="pa-0">
          <v-parallax :src="getImgUrl" height="754"  align-center justify-center 
            data-aos="zoom-out"
            data-aos-duration="2000"
            data-aos-delay="50"
          >
            <v-row align="start" justify="center" no-gutters
        style="height: 1px; padding: 120px;" >
              <v-col class="text-center" cols="12" >
        <h1 style="color:#FF80AB;
        font-size:70px; font-family:Arial;" 
        data-aos="fade-up"
        data-aos-duration="2000"
        data-aos-delay="50">나누미</h1>
        <v-row>
        <v-col cols="3"><v-spacer/></v-col>
        <v-col cols="6"><div id="divider" style="background-color:white; height: 5px; width:100%;" 
        data-aos="zoom-in-right"
        data-aos-duration="1500"
        data-aos-delay="2000"></div></v-col>
            
        <v-col cols="3" lg="3" md="3" xs="2" sm="1"><v-spacer/></v-col>
        </v-row>
        <h2 class="subheading" id="test2"
        data-aos="fade-up"
        data-aos-duration="2000"
        data-aos-delay="2000">Resource Management</h2>
        <div class="m-5 p-5"
        data-aos="fade-up"
        data-aos-duration="2000"
        data-aos-delay="2000">
          <v-btn rounded x-large style="background-color:#EB539E;padding-left:90px;padding-right:90px;" dark> 바로 시작하기 </v-btn>
        </div>
      </v-col>
              <v-col></v-col>
            </v-row>
          </v-parallax>
        </v-col>
      </v-row>
    </v-container>

    <section>
      <v-layout column wrap align-center>
        <v-flex xs12 sm4 class="my-4">
          <div class="text-center">
            <h3 data-aos="fade-down"
            data-aos-duration="1500"
            data-aos-delay="50" 
            style="color:#FF80AB; font-size:40px; font-weight:bold; font-family:noto sans kr; display: inline-block">API키</h3>
            <h3 data-aos="fade-left"
            data-aos-duration="1500"
            data-aos-delay="50"
            style="font-size:35px; font-family:noto sans kr; font-weight:bold; display: inline-block">를 발급받고</h3>
            <h2  class="headline" 
            data-aos="fade-left"
            data-aos-duration="1500"
            data-aos-delay="250"  style="font-size:30px; font-family:noto sans kr;">FAX번호를 자동으로 분배해 줍니다.</h2>
            <br/>
            <p class="subheading"
            data-aos="fade-right"
            data-aos-duration="2000"
            data-aos-delay="350"
            style="font-family:noto sans kr; "
            ><strong>RestFul </strong>API, <strong>DB</strong>변경 유연<br/></p>
          </div>
        </v-flex>
      </v-layout>
    </section>
      <v-row style="height: 150px; "></v-row>
    <company></company>
  </div>
</template>


<script>
import company from './footer'
export default {
  name: 'home',
  components: {
    company
  },
  data(){
    return{
    
    };
  },
  methods: {
  },
  computed: {
    getImgUrl: function() {
      // return require("../assets/img/main.jpg");
      return require("../assets/main.jpg");
    },
  },
}

</script>
