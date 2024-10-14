package org.example.interview.service.impl;

import org.example.interview.entity.Category;
import org.example.interview.mapper.CategoryMapper;
import org.example.interview.service.CategoryService;
import org.example.interview.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// CategoryServicelmpl类实现了CategoryService接口，提供分类相关的业务逻辑处理
@Service
public class CategoryServiceImpl implements CategoryService {

    // 使用@Autowired注解自动装配CategoryMapper，用于执行与数据库的交互操作
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加一个新的分类
     * 设置创建时间和更新时间为当前时间，以及创建用户ID
     * 从ThreadLocal中获取当前用户ID，并将其设置为分类的创建用户
     * 调用CategoryMapper的add方法将分类添加到数据库
     *
//   * @param category 要添加的分类对象
     */
    @Override
    public void add(Category category) {
        //补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    /**
     * 获取当前用户的分类列表
     * 从ThreadLocal中获取当前用户ID，并使用该ID调用CategoryMapper的list方法获取分类列表
     *
     * @return 包含当前用户所有分类的列表
     */
    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }

    /**
     * 根据ID查找分类
     * 调用CategoryMapper的findById方法根据ID从数据库中查找分类
     *
//     * @param id 要查找的分类的ID
     * @return 找到的分类对象，如果找不到则返回null
     */
    @Override
    public Category findById(Integer id) {
        Category c = categoryMapper.findById(id);
        return c;
    }

    //更新分类
    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

}
