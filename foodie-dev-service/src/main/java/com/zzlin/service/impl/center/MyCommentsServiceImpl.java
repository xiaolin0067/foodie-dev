package com.zzlin.service.impl.center;

import com.zzlin.enums.YesOrNo;
import com.zzlin.mapper.*;
import com.zzlin.pojo.OrderItems;
import com.zzlin.pojo.OrderStatus;
import com.zzlin.pojo.Orders;
import com.zzlin.pojo.bo.center.OrderItemsCommentBO;
import com.zzlin.service.center.MyCommentsService;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlin
 * @date 20210214
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Resource
    private OrderItemsMapper orderItemsMapper;

    @Resource
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Resource
    private Sid sid;

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

    /**
     * 保存评价
     *
     * @param orderId     订单ID
     * @param userId      用户ID
     * @param commentList 评价列表
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1.保存评价 items_comments
        for (OrderItemsCommentBO commentBo : commentList) {
            commentBo.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2.修改订单状态为已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }
}
