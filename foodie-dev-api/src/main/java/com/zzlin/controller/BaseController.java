package com.zzlin.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @author zlin
 * @date 20210105
 */
@Controller
public class BaseController {

    static final Integer COMMENT_PAGE_SIZE = 10;
    static final Integer PAGE_SIZE = 20;

    static final String SHOP_CART = "shopcart";

    /**
     * 微信支付成功 -> 支付中心 -> 天天吃货平台
     *                      |-> 回调通知的url
     */
    static final String PAT_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

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
}
