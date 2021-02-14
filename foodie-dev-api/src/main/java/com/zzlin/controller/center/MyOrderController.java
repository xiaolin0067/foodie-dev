package com.zzlin.controller.center;

import com.zzlin.controller.BaseController;
import com.zzlin.enums.OrderStatusEnum;
import com.zzlin.utils.PagedGridResult;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author zlin
 * @date 20210121
 */
@Api(value = "用户中心-我的订单相关接口", tags = {"用户中心-我的订单相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrderController extends BaseController {

    @ApiOperation(value = "分页查询我的订单列表", notes = "分页查询我的订单列表", httpMethod = "POST")
    @PostMapping("query")
    public Result query(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态")
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "页码")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页记录数")
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg("用户ID为空");
        }
        if (orderStatus != null && OrderStatusEnum.illegalStatus(orderStatus)) {
            return Result.errorMsg("订单状态错误");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pageResult = myOrderService.queryMyOrders(userId, orderStatus, page, pageSize);
        return Result.ok(pageResult);
    }

    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("deliver")
    public Result deliver(
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return Result.errorMsg("订单ID不能为空");
        }
        myOrderService.updateDeliverOrderStatus(orderId);
        return Result.ok();
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("confirmReceive")
    public Result confirmReceive(
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId) {
        Result result = checkUserOrder(userId, orderId);
        if (!result.isOK()) {
            return result;
        }
        boolean updateResult = myOrderService.updateReceiveOrderStatus(orderId);
        if (!updateResult) {
            return Result.errorMsg("确认收货失败！");
        }
        return Result.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("delete")
    public Result delete(
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId) {
        Result result = checkUserOrder(userId, orderId);
        if (!result.isOK()) {
            return result;
        }

        boolean deleteResult = myOrderService.deleteOrder(userId, orderId);
        if (!deleteResult) {
            return Result.errorMsg("删除订单失败！");
        }
        return Result.ok();
    }
}
