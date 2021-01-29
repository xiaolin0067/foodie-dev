package com.zzlin.mapper;

import com.zzlin.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zlin
 * @date 20210129
 */
public interface OrdersMapperCustom {

    /**
     * 查询订单列表
     * @param map 参数
     * @return 订单列表
     */
    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);
}
