package com.zzlin.controller;

import com.zzlin.enums.YesOrNo;
import com.zzlin.pojo.Carousel;
import com.zzlin.pojo.Category;
import com.zzlin.pojo.vo.CatNewItemsVO;
import com.zzlin.pojo.vo.CategoryVO;
import com.zzlin.service.CarouseService;
import com.zzlin.service.CategoryService;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    /**
     * 获取轮播图
     * @return 轮播图列表
     */
    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public Result carousel() {
        List<Carousel> carousels = carouseService.queryAll(YesOrNo.YES.type);
        return Result.ok(carousels);
    }

    @ApiOperation(value = "获取商品分类（一级）", notes = "获取商品分类（一级）", httpMethod = "GET")
    @GetMapping("/cats")
    public Result cats() {
        List<Category> categories = categoryService.queryAllRootCat();
        return Result.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public Result subCat(
            @ApiParam(name = "rootCatId", value = "商品一级分类ID", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return Result.errorMsg("分类不存在");
        }
        List<CategoryVO> categories = categoryService.getSubCatList(rootCatId);
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
