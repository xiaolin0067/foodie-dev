package com.zzlin.controller;

import com.zzlin.pojo.*;
import com.zzlin.pojo.vo.ItemInfoVO;
import com.zzlin.service.ItemService;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlin
 * @date 20201228
 */
@Api(value = "商品详情", tags = {"商品详情相关接口"})
@RequestMapping("items")
@RestController
public class ItemController {

    @Resource
    ItemService itemService;

    /**
     * 查询商品详情
     * @return 商品详情
     */
    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public Result info(
            @ApiParam(name = "itemId", value = "商品唯一标识", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return Result.errorMsg("商品不存在");
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemParams = itemService.queryItemParam(itemId);
        return Result.ok(new ItemInfoVO(item, itemImgList, itemSpecList, itemParams));
    }
}
