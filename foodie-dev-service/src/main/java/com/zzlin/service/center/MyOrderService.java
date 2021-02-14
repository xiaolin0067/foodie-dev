package com.zzlin.service.center;

import com.zzlin.pojo.Orders;
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

    /**
     * 更新订单发货状态
     * @param orderId 订单ID
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return 订单实体
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 ---> 确认收货
     * @param orderId 订单ID
     * @return 更新状态
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单（逻辑删除）
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return 删除结果
     */
    boolean deleteOrder(String userId, String orderId);
}
