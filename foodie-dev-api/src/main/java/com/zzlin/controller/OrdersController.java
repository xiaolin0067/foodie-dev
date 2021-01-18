package com.zzlin.controller;

import com.zzlin.enums.OrderStatusEnum;
import com.zzlin.enums.PayMethod;
import com.zzlin.pojo.bo.SubmitOrderBO;
import com.zzlin.pojo.vo.MerchantOrdersVO;
import com.zzlin.pojo.vo.OrderVO;
import com.zzlin.service.OrderService;
import com.zzlin.utils.CookieUtils;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

    @Resource
    private RestTemplate restTemplate;

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
        MerchantOrdersVO merchantOrdersVO = order.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAT_RETURN_URL);

        // 将所有支付金额统一改为1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<Result> payResponseResult = restTemplate.postForEntity(PAY_MENT_URL, entity, Result.class);
        Result result = payResponseResult.getBody();
        if (result == null || !result.isOK()) {
            return Result.errorMsg("支付中心订单创建失败，请联系管理员！");
        }

        LOGGER.info("创建订单请求响应结果 {}", JsonUtils.objectToJson(order));
        // 响应为{"orderId":"210117F9AR96A6NC","merchantOrdersVO":null}时报400错误，在请求目标中找到无效字符。有效字符在RFC 7230和RFC 3986中定义
        return Result.ok(order.getOrderId());
    }

    /**
     * 通知商户订单支付结果
     * @param merchantOrderId 商户订单ID
     * @return 结果
     */
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }
}