package com.zzlin.service;

import com.zzlin.pojo.Items;
import com.zzlin.pojo.ItemsImg;
import com.zzlin.pojo.ItemsParam;
import com.zzlin.pojo.ItemsSpec;
import com.zzlin.pojo.vo.CommentLevelCountsVo;

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
}
