package com.zzlin.service.impl;

import com.zzlin.enums.OrderStatusEnum;
import com.zzlin.enums.YesOrNo;
import com.zzlin.mapper.OrderItemsMapper;
import com.zzlin.mapper.OrderStatusMapper;
import com.zzlin.mapper.OrdersMapper;
import com.zzlin.pojo.*;
import com.zzlin.pojo.bo.ShopCartBO;
import com.zzlin.pojo.bo.SubmitOrderBO;
import com.zzlin.pojo.vo.MerchantOrdersVO;
import com.zzlin.pojo.vo.OrderVO;
import com.zzlin.service.AddressService;
import com.zzlin.service.ItemService;
import com.zzlin.service.OrderService;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zlin
 * @date 20201226
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Resource
    private AddressService addressService;

    @Resource
    private ItemService itemService;

    @Resource
    private OrderItemsMapper orderItemsMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Resource
    private Sid sid;

    /**
     * 用于创建订单相关信息
     *
     * @param submitOrderBO 创建订单BO
     * @return 订单VO
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public OrderVO createOrder(List<ShopCartBO> shopCartList, SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        int postAmount = 0;
        String orderId = sid.nextShort();
        UserAddress address = addressService.queryUserAddress(userId, addressId);

        // 1. 新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail());
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        // 2. 根据itemSpecIds保存订单商品信息表
        int totalAmount = 0;
        int realPayAmount = 0;
        // 商品购买的数量重新从redis的购物车中获取
        Map<String, ShopCartBO> userShopCartMap = getUserShopCartMap(shopCartList);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecListByIds(itemSpecIds);
        for (ItemsSpec itemsSpec : itemsSpecList) {
            // TODO 此处暂未考虑下单的规格ID在购物车中不存在的情况，后续可自定义异常优化
            Integer buyCounts = userShopCartMap.get(itemsSpec.getId()).getBuyCounts();
            // 2.1 根据商品规格计算总价与折扣价
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            // 2.2 获取订单商品信息和图片地址，TODO 可优化
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryItemById(itemId);
            String mainImgUrl = itemService.queryItemMainImgByItemId(itemId);
            String orderItemId = sid.nextShort();
            // 2.3 订单商品赋值
            OrderItems orderItem = new OrderItems();
            orderItem.setBuyCounts(buyCounts);
            orderItem.setId(orderItemId);
            orderItem.setItemId(itemId);
            orderItem.setItemImg(mainImgUrl);
            orderItem.setItemName(items.getItemName());
            orderItem.setItemSpecName(itemsSpec.getName());
            orderItem.setItemSpecId(itemsSpec.getId());
            orderItem.setOrderId(orderId);
            orderItem.setPrice(itemsSpec.getPriceDiscount());
            // 2.4 订单商品入库
            orderItemsMapper.insert(orderItem);
            // 2.5 减少库存
            itemService.decreaseItemSpecStock(itemsSpec.getId(), buyCounts);
        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);

        // 3. 订单状态保存
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(orderStatus);

        // 4. 构建商户订单，传给支付中心
        MerchantOrdersVO merchantOrders = new MerchantOrdersVO();
        merchantOrders.setAmount(totalAmount + postAmount);
        merchantOrders.setMerchantOrderId(orderId);
        merchantOrders.setMerchantUserId(userId);
        merchantOrders.setPayMethod(payMethod);

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrders);
        return orderVO;
    }

    private Map<String, ShopCartBO> getUserShopCartMap(List<ShopCartBO> shopCartList) {
        Map<String, ShopCartBO> result = new HashMap<>();
        if (CollectionUtils.isEmpty(shopCartList)) {
            return result;
        }
        try {
            // 一个SpecId应该只对应一个ShopCartBO
            result = shopCartList.stream()
                    .collect(Collectors.toMap(ShopCartBO::getSpecId, shopCart -> shopCart));
        }catch (Exception e) {
            LOGGER.error("用户购物车列表转Map异常", e);
        }
        return result;
    }

    /**
     * 修改订单状态
     *
     * @param orderId     订单ID
     * @param orderStatus 订单状态
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus os = new OrderStatus();
        os.setOrderId(orderId);
        os.setOrderStatus(orderStatus);
        os.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(os);
    }

    /**
     * 查询订单状态
     *
     * @param orderId 订单ID
     * @return 订单状态
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 关闭超时未支付订单
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void closePayOvertimeOrder() {
        OrderStatus queryOs = new OrderStatus();
        queryOs.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> waitOrderStatus = orderStatusMapper.select(queryOs);
        for (OrderStatus orderStatus : waitOrderStatus) {
            long createTime = orderStatus.getCreatedTime().getTime();
            // 一天未支付，TODO 是否有性能影响
            if ((System.currentTimeMillis() - createTime) > 86400000) {
                doCloseOrder(orderStatus.getOrderId());
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void doCloseOrder(String orderId) {
        OrderStatus os = new OrderStatus();
        os.setOrderStatus(OrderStatusEnum.CLOSE.type);
        os.setCloseTime(new Date());
        os.setOrderId(orderId);
        orderStatusMapper.updateByPrimaryKeySelective(os);
    }
}
