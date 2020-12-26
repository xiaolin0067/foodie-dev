package com.zzlin.service;

import com.zzlin.pojo.Category;

import java.util.List;

/**
 * @author zlin
 * @date 20201226
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return 一级分类列表
     */
    List<Category> queryAllRootCat();
}
