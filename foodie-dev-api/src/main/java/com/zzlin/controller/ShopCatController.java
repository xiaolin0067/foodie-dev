package com.zzlin.controller;

import com.zzlin.pojo.bo.ShopCartBO;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zlin
 * @date 20201228
 */
@Api(value = "购物车相关接口", tags = {"购物车相关接口"})
@RequestMapping("shopcart")
@RestController
public class ShopCatController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShopCatController.class);

    /**
     * 添加商品到购物车
     * @return 添加结果
     */
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public Result add(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "shopCartBO", value = "商品")
            @RequestBody ShopCartBO shopCartBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg(null);
        }

        LOGGER.info("添加商品到购物车 userId：{}, shopCartBO：{}", userId, JsonUtils.objectToJson(shopCartBO));

        // TODO 在用户登录的情况下，需要同步购物车到Redis

        return Result.ok();
    }

    /**
     * 删除购物车商品
     * @return 删除结果
     */
    @ApiOperation(value = "删除购物车商品", notes = "删除购物车商品", httpMethod = "POST")
    @PostMapping("/del")
    public Result del(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "itemSpecId", value = "商品规格ID")
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return Result.errorMsg(null);
        }

        LOGGER.info("删除购物车商品 userId：{}, itemSpecId：{}", userId, itemSpecId);

        // TODO 在用户登录的情况下，需要同步购物车到Redis

        return Result.ok();
    }
}
