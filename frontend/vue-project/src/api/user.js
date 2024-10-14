//导入request.js请求工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData) => {
    //借助于urlsearchParams完成传递
    const params = new URLSearchParams()
    for (let key in registerData) {
        params.append(key, registerData[key]);
    }
    return request.post('/user/register', params);   //调用request.js的post方法，向服务器发送注册请求
}

//提供调用登录接口的函数
export const userLoginService = (loginData) => {
    const params = new URLSearchParams()
    for (let key in loginData) {
        params.append(key, loginData[key]);
    }
    return request.post('/user/login', params);
}


//获取用户详细信息
export const userInfoService = () => {
    return request.get('/user/userInfo');
}

//修改个人信息
export const userInfoUpdateService = (userInfoData) => {
    return request.put('/user/update', userInfoData);
}

//修改头像
export const userAvatarUpdateService = (avatarUrl) => {
    const params = new URLSearchParams();
    params.append('avatarUrl', avatarUrl);
    return request.patch('/user/updateAvatar', params);
}

//修改密码
export const userPasswordUpdateService = (passwordData) => {
    // 直接使用 JSON 格式发送请求参数
    const params = {
        old_pwd: passwordData.old_pwd,
        new_pwd: passwordData.new_pwd,
        re_pwd: passwordData.re_pwd
    };

    // 发送 PATCH 请求到 '/user/updatePwd'，携带 JSON 格式的参数
    return request.patch('/user/updatePwd', params, {
        headers: {
            'Content-Type': 'application/json'
        }
    });
}
