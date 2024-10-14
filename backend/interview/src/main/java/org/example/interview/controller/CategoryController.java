package org.example.interview.controller;


import org.apache.ibatis.annotations.Delete;
import org.example.interview.entity.Article;
import org.example.interview.entity.Category;
import org.example.interview.entity.PageBean;
import org.example.interview.entity.Result;
import org.example.interview.service.CategoryService;
import org.example.interview.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 控制器类，处理与类别相关的HTTP请求
@RestController
@RequestMapping("/category")
public class CategoryController {

    // 自动注入类别服务，用于业务逻辑处理
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    // 添加类别
    // 使用POST请求，根据请求体中的数据创建一个新的类别实例
    // 使用@Validated注解确保传入的类别对象符合验证规则
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        // 调用服务层的add方法添加新的类别
        categoryService.add(category);
        // 返回添加成功的结果
        return Result.success();
    }

    // 获取所有类别列表
    // 使用GET请求，从服务层获取所有类别的列表并返回
    @GetMapping
    public Result<List<Category>> list(){
        // 调用服务层的list方法获取所有类别列表
        List<Category> cs = categoryService.list();
        // 返回类别列表成功的结果
        return Result.success(cs);
    }

    //根据id查询
    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    //更新文章分类
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    // 删除类别
    @DeleteMapping
    public Result delete(Integer id){
        if(categoryService.findById(id)==null){
            return Result.error("该分类不存在");
        }
        categoryService.deleteById(id);
        return Result.success();
    }

}

