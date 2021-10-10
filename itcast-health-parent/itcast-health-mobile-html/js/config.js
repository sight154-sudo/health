function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    }

    return null;
}

// axios.defaults.baseURL = 'http://127.0.0.1:80';
axios.interceptors.request.use(function (config){
    let token = window.localStorage.getItem("user")
    if (token){
        config.headers.Authorization = token;
    }
    return config;
},function (error){
    return Promise.reject(error);
});

axios.defaults.baseURL = 'http://127.0.0.1:9100';
axios.defaults.withCredentials = true;


let telephone = getCookie("login_member_telephone");

if(telephone){
    axios.defaults.headers.common['login_member_telephone'] = telephone;
}

