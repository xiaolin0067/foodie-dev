package com.zzlin.mapper;

import com.zzlin.pojo.vo.CatNewItemsVO;
import com.zzlin.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zlin
 * @date 20201226
 */
public interface CategoryMapperCustom {

    /**
     * 根据根分类获取子分类列表
     * @param rootCatId 根分类ID
     * @return 子分类列表
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品详细数据
     * @param map 查询参数
     * @return 商品详细信息列表
     */
    List<CatNewItemsVO> getCatNewestSixItemList(@Param("paramsMap") Map<String, Object> map);
}