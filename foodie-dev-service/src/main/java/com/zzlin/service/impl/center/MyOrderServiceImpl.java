package com.zzlin.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.zzlin.enums.OrderStatusEnum;
import com.zzlin.enums.YesOrNo;
import com.zzlin.mapper.OrderItemsMapper;
import com.zzlin.mapper.OrderStatusMapper;
import com.zzlin.mapper.OrdersMapper;
import com.zzlin.mapper.OrdersMapperCustom;
import com.zzlin.pojo.*;
import com.zzlin.pojo.bo.SubmitOrderBO;
import com.zzlin.pojo.vo.MerchantOrdersVO;
import com.zzlin.pojo.vo.MyOrdersVO;
import com.zzlin.pojo.vo.OrderVO;
import com.zzlin.service.AddressService;
import com.zzlin.service.ItemService;
import com.zzlin.service.OrderService;
import com.zzlin.service.center.MyOrderService;
import com.zzlin.utils.PagedGridResult;
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
 * @date 20201226
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Resource
    private OrdersMapperCustom ordersMapperCustom;

    /**
     * 分页查询我的订单列表
     *
     * @param userId      用户ID
     * @param orderStatus 订单状态
     * @param page        页码
     * @param pageSize    每页数量
     * @return 订单分页列表
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        if (orderStatus != null) {
            paramMap.put("orderStatus", orderStatus);
        }
        List<MyOrdersVO> myOrdersVoList = ordersMapperCustom.queryMyOrders(paramMap);
        return new PagedGridResult(myOrdersVoList, page);
    }
}
