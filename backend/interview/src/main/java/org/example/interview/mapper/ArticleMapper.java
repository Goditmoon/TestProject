package org.example.interview.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.interview.entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {

    //新增文章
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)" +"" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    //文章列表（条件分页）
    List<Article> list(Integer userId, Integer categoryId, String state);

    //更新文章
    @Insert("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void update(Article article);

    //查询文章详情
    @Select("select * from article where id = #{id}")
    Article detail(Integer id);

    //删除文章
    @Select("delete from article where id = #{id}")
    void delete(Integer id);
}
