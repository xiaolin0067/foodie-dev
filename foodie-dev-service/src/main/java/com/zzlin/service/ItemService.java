package com.zzlin.service;

import com.zzlin.pojo.Items;
import com.zzlin.pojo.ItemsImg;
import com.zzlin.pojo.ItemsParam;
import com.zzlin.pojo.ItemsSpec;
import com.zzlin.pojo.vo.CommentLevelCountsVo;
import com.zzlin.pojo.vo.ShopCartVO;
import com.zzlin.utils.PagedGridResult;

import java.util.List;

/**
 * @author zlin
 * @date 20201228
 */
public interface ItemService {

    /**
     * 通过商品ID查询商品详情
     * @param itemId 商品ID
     * @return 商品详情
     */
    Items queryItemById(String itemId);

    /**
     * 通过商品ID查询商品图片列表
     * @param itemId 商品ID
     * @return 商品图片列表
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 通过商品ID查询商品规格列表
     * @param itemId 商品ID
     * @return 商品规格列表
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 通过商品ID查询商品参数
     * @param itemId 商品ID
     * @return 商品参数
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 通过商品ID查询商品评价数量
     * @param itemId 商品ID
     * @return 评价数量
     */
    CommentLevelCountsVo queryCommentCounts(String itemId);

    /**
     * 分页查询商品评价
     * @param itemId 商品ID
     * @param level 评价等级
     * @param page 页码
     * @param pageSize 每页数量
     * @return 商品评价列表
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品
     * @param keywords 搜索关键字
     * @param sort 排序规则，c: 根据销量排序，p: 根据价格排序
     * @param page 页码
     * @param pageSize 每页数量
     * @return 商品列表
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 搜索商品
     * @param catId 分类ID
     * @param sort 排序规则，c: 根据销量排序，p: 根据价格排序
     * @param page 页码
     * @param pageSize 每页数量
     * @return 商品列表
     */
    PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据商品规格ID查询商品信息（用于刷新购物车商品信息）
     * @param specIds 规格ID
     * @return 商品列表
     */
    List<ShopCartVO> queryItemsBySpecIds(String specIds);

    /**
     * 通过规格IDs查询商品规格列表
     * @param itemSpecIds 规格IDs
     * @return 商品规格列表
     */
    List<ItemsSpec> queryItemSpecListByIds(String itemSpecIds);
}
