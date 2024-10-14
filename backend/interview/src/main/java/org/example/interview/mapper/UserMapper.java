package org.example.interview.mapper;

import org.apache.ibatis.annotations.*;
import org.example.interview.entity.User;

// 使用Mapper注解标记该接口为MyBatis的映射接口
@Mapper
// 定义了对用户信息进行数据库操作的接口
public interface UserMapper {
    // 根据用户名查询用户信息
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    // 添加用户
    @Insert("insert into user(username,password,create_time,update_time)" +
    " values(#{username},#{password},now(),now())")
    void add(String username, String password);

    //更新用户信息
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    //更新用户头像
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update(
            "update user set password=#{md5String},update_time=now() where id=#{id}"
    )
    void updatePwd(String md5String, Integer id);

    // 根据Id删除用户
    @Delete("delete from user where id=#{id}")
    void delete(Integer id);
}
