  
import axios from "axios";

const axios_common = axios.create({
  baseURL: "http://localhost:8080",  
  // baseURL: "http://123.2.134.114:8081/nanumi",
  headers: {
    "Content-type": "application/json",
  }
});

axios_common.defaults.timeout = 2500;

axios_common.interceptors.response.use(function (response) {
  return response;
}, async function (error) {
  console.log('에러일 경우', error.config);
  const errorAPI = error.config;
  if(!error.response){
    // alert("서버가 응답하지 않습니다. time out")

    console.log("---------------------")

    
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
  else{
    alert(error.response.status)
  }
  return Promise.reject(error);
});

export default axios_common

// export default axios.create({
//   baseURL: "http://localhost:8080",  
//   // baseURL: "http://123.2.134.114:8081/nanumi",
//   headers: {
//     "Content-type": "application/json",
//   }
// });