package com.zzlin.service;

import com.zzlin.pojo.OrderStatus;
import com.zzlin.pojo.bo.ShopCartBO;
import com.zzlin.pojo.bo.SubmitOrderBO;
import com.zzlin.pojo.vo.OrderVO;

import java.util.List;

/**
 * @author zlin
 * @date 20201226
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param shopCartList 用户购物车列表
     * @param submitOrderBO 创建订单BO
     * @return 订单VO
     */
    OrderVO createOrder(List<ShopCartBO> shopCartList, SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId 订单ID
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId 订单ID
     * @return 订单状态
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closePayOvertimeOrder();
}
