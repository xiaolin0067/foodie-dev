package com.zzlin.controller;

import com.zzlin.enums.CacheKey;
import com.zzlin.pojo.Orders;
import com.zzlin.pojo.Users;
import com.zzlin.pojo.vo.UsersVO;
import com.zzlin.service.center.MyOrderService;
import com.zzlin.utils.RedisOperator;
import com.zzlin.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

/**
 * @author zlin
 * @date 20210105
 */
@Controller
public class BaseController {

    @Autowired
    private RedisOperator redisOperator;

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

    /**
     * 缓存用户会话token并转换为VO
     * @param user 用户
     * @return 用户VO
     */
    public UsersVO cacheTokenAndConvertVO(Users user) {
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(CacheKey.USER_TOKEN.append(user.getId()), uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user,usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
