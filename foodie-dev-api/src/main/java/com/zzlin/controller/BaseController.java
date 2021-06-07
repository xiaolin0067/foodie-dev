package com.zzlin.controller;

import com.zzlin.pojo.Orders;
import com.zzlin.service.center.MyOrderService;
import com.zzlin.utils.Result;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author zlin
 * @date 20210105
 */
@Controller
public class BaseController {

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    /**
     * 微信支付成功 -> 支付中心 -> 天天吃货平台
     *                      |-> 回调通知的url
     */
    static final String PAT_RETURN_URL = "http://192.168.3.16:8088/foodie-dev-api/orders/notifyMerchantOrderPaid";

    /**
     * 支付中心的调用地址
     */
    static final String PAY_MENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    /**
     * 用户上传头像的位置
     */
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces" +
            File.separator + "images" +
            File.separator + "foodie" +
            File.separator + "faces";

    @Resource
    public MyOrderService myOrderService;

    /**
     * 检查订单是否存在
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return 结果
     */
    public Result checkUserOrder(String userId, String orderId) {
        Orders orders = myOrderService.queryMyOrder(userId, orderId);
        if (orders == null) {
            return Result.errorMsg("订单不存在!");
        }
        return Result.ok(orders);
    }
}
