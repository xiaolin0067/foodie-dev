package com.zzlin.service.impl;

import com.zzlin.enums.YesOrNo;
import com.zzlin.pojo.ItemsSpec;
import com.zzlin.pojo.OrderStatus;
import com.zzlin.pojo.Orders;
import com.zzlin.pojo.UserAddress;
import com.zzlin.pojo.bo.SubmitOrderBO;
import com.zzlin.pojo.vo.OrderVO;
import com.zzlin.service.AddressService;
import com.zzlin.service.ItemService;
import com.zzlin.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author zlin
 * @date 20201226
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private AddressService addressService;

    @Resource
    private ItemService itemService;

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
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        Integer postAmount = 0;
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

        // 2. 循环根据itemSpecIds保存订单商品信息表
        Integer totalAmount = 0;
        Integer realPayAmount = 0;

        // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
        int buyCounts = 1;
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecListByIds(itemSpecIds);
        for (ItemsSpec itemsSpec : itemsSpecList) {

        }

        return null;
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

    }

    /**
     * 查询订单状态
     *
     * @param orderId 订单ID
     * @return 订单状态
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return null;
    }

    /**
     * 关闭超时未支付订单
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void closePayOvertimeOrder() {

    }
}
