package com.myblog.service.impl;

import com.myblog.dao.CategoryMapper;
import com.myblog.model.Category;
import com.myblog.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Zephery on 2017/6/19.
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    @Override
    public Category selectByPrimaryKey(Integer categoryid) {
        return categoryMapper.selectByPrimaryKey(categoryid);
    }

    @Override
    public void update_category(String cName, Integer cId) {
        categoryMapper.update_category(cName, cId);
    }

    @Override
    public Integer delete_category(Integer cId) {
        return categoryMapper.deleteByPrimaryKey(cId);
    }
}
