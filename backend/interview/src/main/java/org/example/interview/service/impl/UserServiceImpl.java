package org.example.interview.service.impl;

import org.example.interview.entity.User;
import org.example.interview.mapper.UserMapper;
import org.example.interview.service.UserService;
import org.example.interview.utils.Md5Util;
import org.example.interview.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
// UserService的实现类
public class UserServiceImpl implements UserService {

    // 自动注入
    @Autowired
    private UserMapper userMapper;
    // 通过用户名查找用户
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    // 注册
    @Override
    public void register(String username, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username, md5String);
    }

    // 更新用户信息
    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    //更新用户的头像
    @Override
    public void updateAvatar(String avatarUrl) {
        //从线程变量中获取用户id,token
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

}
