package com.zzlin.controller;

import com.zzlin.enums.YesOrNo;
import com.zzlin.pojo.Carousel;
import com.zzlin.pojo.Category;
import com.zzlin.pojo.vo.CatNewItemsVO;
import com.zzlin.pojo.vo.CategoryVO;
import com.zzlin.service.CarouseService;
import com.zzlin.service.CategoryService;
import com.zzlin.utils.JsonUtils;
import com.zzlin.utils.RedisOperator;
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
 * @date 20201225
 */
@Api(value = "首页", tags = {"首页展示相关接口"})
@RequestMapping("index")
@RestController
public class IndexController {

    @Resource
    CarouseService carouseService;
    @Resource
    CategoryService categoryService;
    @Resource
    private RedisOperator redisOperator;

    /**
     * 获取轮播图
     *
     * 缓存更新问题：
     *     1. 后台运营系统，一旦广告(轮播图)发生更改，就可以删除缓存，然后重置
     *     2. 定时重置，注意不可同一时刻将所有缓存清除
     *     3. 每个轮播图都有可能是一个广告，每个广告都有一个过期时间，过期重置
     * @return 轮播图列表
     */
    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public Result carousel() {
        String redisKey = "index:carousel";
        String carouselJson = redisOperator.get(redisKey);
        if (!StringUtils.isBlank(carouselJson)) {
            return Result.ok(JsonUtils.jsonToList(carouselJson, Carousel.class));
        }
        List<Carousel> carousels = carouseService.queryAll(YesOrNo.YES.type);
        redisOperator.set(redisKey, JsonUtils.objectToJson(carousels));
        return Result.ok(carousels);
    }

    @ApiOperation(value = "获取商品分类（一级）", notes = "获取商品分类（一级）", httpMethod = "GET")
    @GetMapping("/cats")
    public Result cats() {
        String redisKey = "index:cats:categories";
        String catsJson = redisOperator.get(redisKey);
        if (!StringUtils.isBlank(catsJson)) {
            return Result.ok(JsonUtils.jsonToList(catsJson, Category.class));
        }
        List<Category> cats = categoryService.queryAllRootCat();
        redisOperator.set(redisKey, JsonUtils.objectToJson(cats));
        return Result.ok(cats);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public Result subCat(
            @ApiParam(name = "rootCatId", value = "商品一级分类ID", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return Result.errorMsg("分类不存在");
        }
        String redisKey = "index:subcat:categories:" + rootCatId;
        String categoriesJson = redisOperator.get(redisKey);
        if (!StringUtils.isBlank(categoriesJson)) {
            return Result.ok(JsonUtils.jsonToList(categoriesJson, CategoryVO.class));
        }
        List<CategoryVO> categories = categoryService.getSubCatList(rootCatId);
        redisOperator.set(redisKey, JsonUtils.objectToJson(categories));
        return Result.ok(categories);
    }

    @ApiOperation(value = "获取每个一级分类下的6个最新商品信息", notes = "获取每个一级分类下的6个最新商品信息", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public Result sixNewItems(
            @ApiParam(name = "rootCatId", value = "商品一级分类ID", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return Result.errorMsg("分类不存在");
        }
        List<CatNewItemsVO> categories = categoryService.getCatNewestSixItemList(rootCatId);
        return Result.ok(categories);
    }
}
