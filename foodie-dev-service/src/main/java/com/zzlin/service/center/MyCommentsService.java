package com.zzlin.service.center;

import com.zzlin.pojo.OrderItems;
import com.zzlin.pojo.bo.center.OrderItemsCommentBO;

import java.util.List;

/**
 * @author zlin
 * @date 20210214
 */
public interface MyCommentsService {

    /**
     * 查询订单商品
     * @param orderId 订单ID
     * @return 商品列表
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存评价
     * @param orderId 订单ID
     * @param userId 用户ID
     * @param commentList 评价列表
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);
}
