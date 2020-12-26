package com.zzlin.service.impl;

import com.zzlin.mapper.CategoryMapper;
import com.zzlin.mapper.CategoryMapperCustom;
import com.zzlin.pojo.Category;
import com.zzlin.pojo.vo.CatNewItemsVO;
import com.zzlin.pojo.vo.CategoryVO;
import com.zzlin.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlin
 * @date 20201226
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;
    @Resource
    CategoryMapperCustom categoryMapperCustom;

    /**
     * 查询所有一级分类
     *
     * @return 一级分类列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        return categoryMapper.selectByExample(example);
    }

    /**
     * 根据一级分类ID查询子分类列表
     *
     * @param rootCatId 一级分类ID
     * @return 子分类列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

    /**
     * 查询首页每个一级分类下的6条最新商品详细数据
     * @param rootCatId 一级分类ID
     * @return 商品详细信息列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CatNewItemsVO> getCatNewestSixItemList(Integer rootCatId) {
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("rootCatId", rootCatId);
        return categoryMapperCustom.getCatNewestSixItemList(paramMap);
    }
}
