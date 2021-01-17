package com.zzlin.controller;

import com.zzlin.enums.PayMethod;
import com.zzlin.pojo.bo.SubmitOrderBO;
import com.zzlin.pojo.vo.OrderVO;
import com.zzlin.service.OrderService;
import com.zzlin.utils.CookieUtils;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zlin
 * @date 20201228
 */
@Api(value = "订单相关接口", tags = {"订单相关接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    @Resource
    private OrderService orderService;

    /**
     * 创建订单
     * @return 地址列表
     */
    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public Result list(@RequestBody SubmitOrderBO submitOrderBO,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        LOGGER.info("创建订单请求 {}", JsonUtils.objectToJson(submitOrderBO));
        if (PayMethod.illegalPayMethod(submitOrderBO.getPayMethod())) {
            return Result.errorMsg("不合法的支付方式");
        }

        // 1、创建订单
        OrderVO order = orderService.createOrder(submitOrderBO);
        // 2、提交的商品在购物车中移除，整合Redis后只移除提交的商品
//        CookieUtils.setCookie(request, response, SHOP_CART, "", true);

        // 3、向支付中心发送当前订单，用于保存支付中心的订单数据

        return Result.ok(order);
    }
}
