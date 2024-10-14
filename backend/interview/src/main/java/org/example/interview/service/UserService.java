package org.example.interview.service;

import lombok.Data;
import org.example.interview.entity.User;
import org.springframework.context.annotation.ComponentScan;

public interface UserService {

    // 根据用户名查找用户
    User findByUserName(String username);

    // 注册用户
    void register(String username, String password);

    // 更新用户信息
    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

}

