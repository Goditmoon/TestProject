//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';

import { ElMessage } from 'element-plus';
//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8080';
//由于浏览器的同源策略限制，向不同源发送ajax请求时，会出现跨域问题。因此，需要在服务端配置允许跨域请求。解决跨域问题
const baseURL = '/api';
const instance = axios.create({baseURL})

//添加请求拦截器
import {useTokenStore} from '@/stores/token.js'
instance.interceptors.request.use(
    (config)=>{
        //请求前的回调
        //添加token
        const tokenStore = useTokenStore();
        //判断有没有token
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    (err)=>{
        //请求失败的回调
        Promise.reject(err);
    }
)

/* import { useRouter } from 'vue-router';
const router = useRouter();*/

//导入router,用于跳转到登录页面
import router from '@/router';
//添加响应拦截器
instance.interceptors.response.use(
    result=>{
        //如果业务状态码为0，代表本次操作成功
        if(result.data.code === 0){
            return result.data;
        }
        //代码走到这里，代表业务状态码不是0，代表本次操作失败
        ElMessage.error(result.data.msg?result.data.msg:'服务异常')
        //异常操作的状态转换为失败
        return Promise.reject(result.data);
    },
    err=>{
        //判断响应状态码，如果为401，则证明未登录，提示请登录，并跳转到登录页面
        if(err.response.status === 401){
            ElMessage.error('请登录');
            router.push('/login');
        }else{
            ElMessage.error('服务异常');
        }
        
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;