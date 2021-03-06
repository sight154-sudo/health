module.exports ={
    devServer: {
        host: 'localhost',
        port: 80,  //没被占用，可以使用的端口
        open: true,
        proxy: {
            '/api':{
                target: 'http://localhost:9100', //接口域名
                changeOrigin: true,             //是否跨域
                secure: true,                   //是否https接口
                pathRewrite: {                  //路径重置
                    '^/api': ''
                }
            }
        }
    }
}

