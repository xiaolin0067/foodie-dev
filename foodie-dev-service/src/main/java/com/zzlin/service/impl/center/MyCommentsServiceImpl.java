package com.zzlin.service.impl.center;

import com.zzlin.mapper.OrderItemsMapper;
import com.zzlin.pojo.OrderItems;
import com.zzlin.service.center.MyCommentsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlin
 * @date 20210214
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Resource
    private OrderItemsMapper orderItemsMapper;

    /**
     * 查询订单商品
     *
     * @param orderId 订单ID
     * @return 商品列表
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);
        return orderItemsMapper.select(orderItems);
    }
}
