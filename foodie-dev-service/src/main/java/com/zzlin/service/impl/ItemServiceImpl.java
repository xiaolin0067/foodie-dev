package com.zzlin.service.impl;

import com.zzlin.enums.CommentLevel;
import com.zzlin.mapper.*;
import com.zzlin.pojo.*;
import com.zzlin.pojo.vo.CommentLevelCountsVo;
import com.zzlin.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    @Resource
    ItemsCommentsMapper itemsCommentsMapper;
    @Resource
    ItemsCommentsMapperCustom itemsCommentsMapperCustom;

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

    /**
     * 通过商品ID查询商品评价数量
     *
     * @param itemId 商品ID
     * @return 评价数量
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVo queryCommentCounts(String itemId) {
        CommentLevelCountsVo resultLevelCount = new CommentLevelCountsVo();
        List<CommentsLevelCount> levelCountList = itemsCommentsMapperCustom.queryCommentsLevelCount(itemId);
        if (!CollectionUtils.isEmpty(levelCountList)) {
            // 统计commentsCount为totalCount
            resultLevelCount.setTotalCounts(levelCountList.stream().mapToInt(CommentsLevelCount::getCommentsCount).sum());
            // 填入各等级数量
            resultLevelCount.setGoodCounts(levelCountList.stream()
                    .filter(e -> CommentLevel.GOOD.type.equals(e.getCommentsLevel()))
                    .findAny().orElse(new CommentsLevelCount()).getCommentsCount());
            resultLevelCount.setNormalCounts(levelCountList.stream()
                    .filter(e -> CommentLevel.NORMAL.type.equals(e.getCommentsLevel()))
                    .findAny().orElse(new CommentsLevelCount()).getCommentsCount());
            resultLevelCount.setBadCounts(levelCountList.stream()
                    .filter(e -> CommentLevel.BAD.type.equals(e.getCommentsLevel()))
                    .findAny().orElse(new CommentsLevelCount()).getCommentsCount());
        }
        return resultLevelCount;
    }

    /**
     * 通过商品ID和评价级别查询评价数量
     * @param itemId 商品ID
     * @param level 评价级别
     * @return 评价数量
     */
    private int queryCommentCounts(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        if (level != null) {
            itemsComments.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(itemsComments);
    }
}
