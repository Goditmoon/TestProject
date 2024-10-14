package org.example.interview.mapper;

import org.apache.ibatis.annotations.*;
import org.example.interview.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //新增
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
    "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    //查询所有
    @Select("select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    //根据Id查询
    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    //更新文章分类
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);

    @Delete("delete from category where id=#{id}")
    void deleteById(Integer id);
}
