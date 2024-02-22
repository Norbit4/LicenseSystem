const axios= require('axios');

axios.defaults.baseURL = 'http://localhost:8080/api/v1';
axios.defaults.headers.common['token'] = 'admin-secret-token';

const testValue = process.env.test;

const GET = (endpoint) =>{
    return axios.get(endpoint);
}
GET('/token/get/all')
    .then((response) => {
        console.log(response.data);
    })
    .catch((error) => {
        console.error(error);
    });

