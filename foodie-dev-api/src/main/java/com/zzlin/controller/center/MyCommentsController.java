package com.zzlin.controller.center;

import com.zzlin.controller.BaseController;
import com.zzlin.enums.YesOrNo;
import com.zzlin.pojo.OrderItems;
import com.zzlin.pojo.Orders;
import com.zzlin.service.center.MyCommentsService;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlin
 * @date 20210121
 */
@Api(value = "用户中心-我的评价相关接口", tags = {"用户中心-我的评价相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Resource
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询未评价订单商品列表", notes = "查询未评价订单商品列表", httpMethod = "POST")
    @PostMapping("pending")
    public Result query(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId) {
        Result result = checkUserOrder(userId, orderId);
        if (!result.isOK()) {
            return result;
        }
        Orders orders = (Orders) result.getData();
        if (orders.getIsComment().equals(YesOrNo.YES.type)) {
            return Result.errorMsg("订单已评价!");
        }
        List<OrderItems> orderItemsList = myCommentsService.queryPendingComment(orderId);
        return Result.ok(orderItemsList);
    }

}
