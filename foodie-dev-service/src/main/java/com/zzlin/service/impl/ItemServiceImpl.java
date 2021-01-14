package com.zzlin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzlin.enums.CommentLevel;
import com.zzlin.mapper.*;
import com.zzlin.pojo.*;
import com.zzlin.pojo.vo.CommentLevelCountsVo;
import com.zzlin.pojo.vo.ItemCommentVO;
import com.zzlin.pojo.vo.SearchItemsVO;
import com.zzlin.pojo.vo.ShopCartVO;
import com.zzlin.service.ItemService;
import com.zzlin.utils.DesensitizationUtil;
import com.zzlin.utils.PagedGridResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    ItemsMapperCustom itemsMapperCustom;

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
        // 只查询一次数据库统计评价等级数量
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
     * 课程中的统计方式，会多次查询数据库--弃用
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

    /**
     * 查询商品评价
     *
     * @param itemId   商品ID
     * @param level    评价等级
     * @param page     页码
     * @param pageSize 每页数量
     * @return 商品评价列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("itemId", itemId);
        paramsMap.put("level", level);
        PageHelper.startPage(page, pageSize);

        List<ItemCommentVO> itemComments = itemsMapperCustom.queryItemComments(paramsMap);
        for (ItemCommentVO comment : itemComments) {
            comment.setNickname(DesensitizationUtil.commonDisplay(comment.getNickname()));
        }

        return setterPagedGrid(itemComments, page);
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    /**
     * 搜索商品
     *
     * @param keywords 搜索关键字
     * @param sort     排序规则，c: 根据销量排序，p: 根据价格排序
     * @param page     页码
     * @param pageSize 每页数量
     * @return 商品列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("keywords", keywords);
        paramsMap.put("sort", sort);
        PageHelper.startPage(page, pageSize);

        List<SearchItemsVO> itemComments = itemsMapperCustom.searchItems(paramsMap);

        return setterPagedGrid(itemComments, page);
    }

    /**
     * 搜索商品
     *
     * @param catId    分类ID
     * @param sort     排序规则，c: 根据销量排序，p: 根据价格排序
     * @param page     页码
     * @param pageSize 每页数量
     * @return 商品列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("catId", catId);
        paramsMap.put("sort", sort);
        PageHelper.startPage(page, pageSize);

        List<SearchItemsVO> itemComments = itemsMapperCustom.searchItemsByCatId(paramsMap);

        return setterPagedGrid(itemComments, page);
    }

    /**
     * 根据商品规格ID查询商品信息（用于刷新购物车商品信息）
     *
     * @param specIds 规格ID
     * @return 商品列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopCartVO> queryItemsBySpecIds(String specIds) {
        List<String> specIdList = Arrays.stream(specIds.split(",")).collect(Collectors.toList());
//        Collections.addAll(List, str[])
        return itemsMapperCustom.queryItemsBySpecIds(specIdList);
    }

    /**
     * 通过规格IDs查询商品规格列表
     *
     * @param itemSpecIds 规格IDs
     * @return 商品规格列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecListByIds(String itemSpecIds) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(itemSpecIds.split(",")));
        return itemsSpecMapper.selectByExample(example);
    }
}
