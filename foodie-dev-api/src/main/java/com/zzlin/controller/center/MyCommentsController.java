package com.zzlin.controller.center;

import com.zzlin.controller.BaseController;
import com.zzlin.enums.YesOrNo;
import com.zzlin.pojo.OrderItems;
import com.zzlin.pojo.Orders;
import com.zzlin.pojo.bo.center.OrderItemsCommentBO;
import com.zzlin.service.center.MyCommentsService;
import com.zzlin.utils.PagedGridResult;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "保存商品评价列表", notes = "保存商品评价列表", httpMethod = "POST")
    @PostMapping("saveList")
    public Result saveList(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {
        if (CollectionUtils.isEmpty(commentList)) {
            return Result.errorMsg("评价列表为空!");
        }
        Result result = checkUserOrder(userId, orderId);
        if (!result.isOK()) {
            return result;
        }
        Orders orders = (Orders) result.getData();
        if (orders.getIsComment().equals(YesOrNo.YES.type)) {
            return Result.errorMsg("订单已评价!");
        }
        myCommentsService.saveComments(orderId, userId, commentList);
        return Result.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query")
    public Result query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = myCommentsService.queryMyComments(userId, page, pageSize);
        return Result.ok(grid);
    }

}
