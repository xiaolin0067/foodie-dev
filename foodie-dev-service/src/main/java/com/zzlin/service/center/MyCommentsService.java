package com.zzlin.service.center;

import com.zzlin.pojo.OrderItems;

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
}
