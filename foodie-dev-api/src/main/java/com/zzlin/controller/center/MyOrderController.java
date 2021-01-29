package com.zzlin.controller.center;

import com.zzlin.controller.BaseController;
import com.zzlin.enums.OrderStatusEnum;
import com.zzlin.service.center.MyOrderService;
import com.zzlin.utils.PagedGridResult;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20210121
 */
@Api(value = "用户中心-我的订单相关接口", tags = {"用户中心-我的订单相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrderController extends BaseController {

    @Resource
    private MyOrderService myOrderService;

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
}
