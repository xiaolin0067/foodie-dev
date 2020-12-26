package com.zzlin.service.impl;

import com.zzlin.mapper.CategoryMapper;
import com.zzlin.pojo.Category;
import com.zzlin.service.CategoryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlin
 * @date 20201226
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    /**
     * 查询所有一级分类
     *
     * @return 一级分类列表
     */
    @Override
    public List<Category> queryAllRootCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        return categoryMapper.selectByExample(example);
    }
}
