const axios= require('axios');

axios.defaults.baseURL = 'http://backend:8080/api/v1';
axios.defaults.headers.common['Authorization'] =  process.env.ACCESS_TOKEN;

const GET = (endpoint, responseType) =>{
   return axios.get(endpoint, {
        responseType: responseType
    });
}

const DELETE = (endpoint, responseType) =>{
    return axios.delete(endpoint, {
        responseType: responseType
    });
}

const POST = (endpoint, data) => {
    return axios.post(endpoint, data);
}

module.exports = { GET, DELETE, POST }