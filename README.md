# nanumi

### [백엔드 환경 설정]
(IntelliJ 기반)
> 1. 프로젝트 Nanumi폴더 Maven 프로젝트 임포트

> 2. Settings => Plugins에서 'lombok' 인스톨 및 IDE 재실행

> 3. nanumi-api/src/main/resources/application.properties에서 local DB부분을 주석처리학 intern DB 부분을 주석 제거 해주세요.

> 4. nanumi-api/src/main/java/com/daou/NanumiAPIApplication.java파일을 실행해주세요.


### [사용자 토큰 발급]
> /api/auth/login URL에 POST로
```
{
 	"username" : "nanumi"
 	"password" : "nanumi"
}
```
> JSON형식으로 요청하면 token과 refreshToken이 발급됩니다.


### [API 서비스 요청방법]
> 서비스 요청시 Header정보에 'Authorization'에 'Bearer {토큰정보}'를 포함하여 필요한 URL에 서비스를 요청합니다.
> 각 서비스의 URI 정보는 Swagger-ui에서 확인할 수 있습니다.


### [API 서비스 메뉴얼(SWAGGER)]
> NanumiAPIApplication 실행후 /swagger-ui.html URL에서 확인할 수 있습니다.



프론트 설정
node.js , visual studio code 설치

비쥬얼 스튜디오 코드에서 폴더 nanumi front에서 열기

터미널 -> 디폴트를 git bash로 변경
cd admin_nanumi

Npm install -g npm

Npm install -g @vue/cli

Npm install axios --save

Npm install vue-router --save

Npm install vuex --save

Npm install d3 --save

Vue add element
Y full ko 선택

Npm run serve(실행방법)
-> 실행하면 npm -ㅁㅁㅁㅁ 들이 나오는데 그거 명령어 
npm install -ㅁㅁ 이런식으로 설치 후

될때까지  npm run serve
