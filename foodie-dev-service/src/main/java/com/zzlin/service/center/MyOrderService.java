package com.zzlin.service.center;

import com.zzlin.utils.PagedGridResult;

/**
 * @author zlin
 * @date 20201226
 */
public interface MyOrderService {

    /**
     * 分页查询我的订单列表
     *
     * @param userId 用户ID
     * @param orderStatus 订单状态
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单分页列表
     */
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);
}
