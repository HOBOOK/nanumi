  
import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:8080",  
  // baseURL: "http://123.2.134.114:8081/nanumi",
  headers: {
    "Content-type": "application/json",
  }
});
