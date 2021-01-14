package com.zzlin.controller;

import com.zzlin.pojo.bo.SubmitOrderBO;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zlin
 * @date 20201228
 */
@Api(value = "订单相关接口", tags = {"订单相关接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    /**
     * 创建订单
     * @return 地址列表
     */
    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public Result list(@RequestBody SubmitOrderBO submitOrderBO) {
        LOGGER.info("创建订单请求 {}", JsonUtils.objectToJson(submitOrderBO));

        // 1、创建订单
        // 2、提交的商品在购物车中移除
        // 3、向支付中心发送当前订单，用于保存支付中心的订单数据

        return Result.ok();
    }
}
