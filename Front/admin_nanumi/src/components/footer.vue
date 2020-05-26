<template>
      <v-container grid-list-xl fluid class="pa-0" style="background: #333;">
        <v-row style="height: 50px; "></v-row>
        <v-row justify="center" class="my-0">
          <v-flex xs12 sm4>
            <v-card flat class="transparent" dark> 
              <v-card-title primary-title class="layout justify-center">
                <div>Team info</div>
              </v-card-title>
              <v-card-text class="cardtext text-center">
              박경호, 송치민, 조승우
              </v-card-text>
            </v-card>
          </v-flex>
          <v-flex xs12 sm4 offset-sm1>
            <v-card flat class="transparent" dark>
              <v-card-title primary-title class="layout justify-center">
                <div>Contact us</div>
              </v-card-title>
              <v-card-text class="cardtext text-center">
                기타 관련 문의 사항은 아래를 참고하세요.
              </v-card-text >
              <v-list class="transparent cardtext text-center">
                <v-list-item>
                  <v-list-item-action>
                    <v-icon style="color:#FF80AB;">mdi-phone</v-icon>
                  </v-list-item-action>
                  <v-list-item-content>
                    <v-list-item-title>010-1234-5678</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-action>
                    <v-icon style="color:#FF80AB;">mdi-map-marker</v-icon>
                  </v-list-item-action>
                  <v-list-item-content>
                    <v-list-item-title>Three brothers</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item>
                  <v-list-item-action>
                    <v-icon style="color:#FF80AB;">mdi-email</v-icon>
                  </v-list-item-action>
                  <v-list-item-content>
                    <v-list-item-title>daou@zoayo.com</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-card>
          </v-flex>
        </v-row>
        <v-row style="height: 70px; "></v-row>
      </v-container>
</template>
