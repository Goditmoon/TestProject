package org.example.interview.service;

import org.example.interview.entity.Article;
import org.example.interview.entity.PageBean;

public interface ArticleService {
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //更新文章
    void update(Article article);

    //获取文章详情
    Article detail(Integer id);

    //删除文章
    void delete(Integer id);
}
