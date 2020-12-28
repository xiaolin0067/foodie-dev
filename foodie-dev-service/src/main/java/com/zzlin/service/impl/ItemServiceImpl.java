package com.zzlin.service.impl;

import com.zzlin.mapper.ItemsImgMapper;
import com.zzlin.mapper.ItemsMapper;
import com.zzlin.mapper.ItemsParamMapper;
import com.zzlin.mapper.ItemsSpecMapper;
import com.zzlin.pojo.Items;
import com.zzlin.pojo.ItemsImg;
import com.zzlin.pojo.ItemsParam;
import com.zzlin.pojo.ItemsSpec;
import com.zzlin.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlin
 * @date 20201228
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    ItemsMapper itemsMapper;
    @Resource
    ItemsImgMapper itemsImgMapper;
    @Resource
    ItemsSpecMapper itemsSpecMapper;
    @Resource
    ItemsParamMapper itemsParamMapper;

    /**
     * 通过商品ID查询商品详情
     *
     * @param itemId 商品ID
     * @return 商品详情
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 通过商品ID查询商品图片列表
     *
     * @param itemId 商品ID
     * @return 商品图片列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    /**
     * 通过商品ID查询商品规格列表
     *
     * @param itemId 商品ID
     * @return 商品规格列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    /**
     * 通过商品ID查询商品参数
     *
     * @param itemId 商品ID
     * @return 商品参数
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }
}
