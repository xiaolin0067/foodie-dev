package com.zzlin.mapper;

import com.zzlin.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {

    /**
     * 根据根分类获取子分类列表
     * @param rootCatId 根分类ID
     * @return 子分类列表
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);
}