package com.zzlin.controller;

import com.zzlin.pojo.*;
import com.zzlin.pojo.vo.CommentLevelCountsVo;
import com.zzlin.pojo.vo.ItemInfoVO;
import com.zzlin.service.ItemService;
import com.zzlin.utils.PagedGridResult;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlin
 * @date 20201228
 */
@Api(value = "商品详情", tags = {"商品详情相关接口"})
@RequestMapping("items")
@RestController
public class ItemController extends BaseController {

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

    /**
     * 查询商品评价等级数量统计
     * @return 评价等级数量统计
     */
    @ApiOperation(value = "商品评价等级数量统计", notes = "商品评价等级数量统计", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public Result commentLevel(
            @ApiParam(name = "itemId", value = "商品唯一标识", required = true)
            @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return Result.errorMsg("商品不存在");
        }
        CommentLevelCountsVo levelCountsVo = itemService.queryCommentCounts(itemId);
        return Result.ok(levelCountsVo);
    }

    /**
     * 查询商品评价
     * @return 商品评价
     */
    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    @GetMapping("/comments")
    public Result comments(
            @ApiParam(name = "itemId", value = "商品唯一标识", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级")
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "页码")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页记录数")
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return Result.errorMsg("商品不存在");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return Result.ok(pagedGridResult);
    }

    /**
     * 搜索商品
     * @return 商品列表
     */
    @ApiOperation(value = "搜索商品", notes = "搜索商品", httpMethod = "GET")
    @GetMapping("/search")
    public Result search(
            @ApiParam(name = "keywords", value = "搜索关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序规则，c: 根据销量排序，p: 根据价格排序")
            @RequestParam String sort,
            @ApiParam(name = "page", value = "页码")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页记录数")
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return Result.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return Result.ok(pagedGridResult);
    }
}
