package com.zzlin.service;

import com.zzlin.pojo.Category;
import com.zzlin.pojo.vo.CatNewItemsVO;
import com.zzlin.pojo.vo.CategoryVO;

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

    /**
     * 根据一级分类ID查询子分类列表
     * @param rootCatId 一级分类ID
     * @return 子分类列表
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品详细数据
     * @param rootCatId 一级分类ID
     * @return 商品详细信息列表
     */
    List<CatNewItemsVO> getCatNewestSixItemList(Integer rootCatId);
}
