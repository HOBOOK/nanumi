  
import axios from "axios";

/**
 * @author JSW
 */

const axios_common = axios.create({

  //로컬 서버용
  baseURL: "http://localhost:8080",  
  // baseURL: "http://123.2.134.114:8081/nanumi",
  headers: {
    "Content-type": "application/json",
  }
});

//초기 timout 대기시간 2.5초
axios_common.defaults.timeout = 2500;

axios_common.interceptors.response.use(function (response) {
  return response;
}, async function (error) {
  console.log('에러일 경우', error.config);
  const errorAPI = error.config;

  //타임아웃일 경우
  if(!error.response){
    console.log("---------------------")

    //3번 시도 후 Alert로 표시
    if(errorAPI.timeout <10000){
      setTimeout(function () {
        errorAPI.timeout *=2;
        return axios_common(errorAPI);
      }.bind(this), errorAPI.timeout);
    }
    else{
      alert("서버가 응답하지 않습니다. timeout")
    }
  }

  //인증처리(401) 에러
  else if(error.response.data.status==='UNAUTHORIZED' && errorAPI.retry===undefined){
    errorAPI.retry = true;
    console.log('만료된 토큰');
    await axios_common.get('/api/auth/token', {
      headers:{
        Authorization: 'Bearer ' + localStorage.getItem("refreshToken")
      }
    }).then((res)=>{
      console.log("토큰 갱신을 하였습니다.")
      console.log(res.data.token)
      console.log("-----------------------------")

      localStorage.setItem('token', res.data.token);
      errorAPI.headers.Authorization = "Bearer " + res.data.token
      // console.log(res);
    }).catch((e)=>{
      console.log(e)
    });
    return await axios_common(errorAPI);
  }
  //그 외의 오류
  else{
    //404에러 아무것도 값이 없을 경우는 경고창 표시X
    if(error.response.status !==404)
      alert(error.response.status)
  }


  return Promise.reject(error);
});

export default axios_common