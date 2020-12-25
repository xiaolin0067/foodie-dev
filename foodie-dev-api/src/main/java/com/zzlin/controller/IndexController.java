package com.zzlin.controller;

import com.zzlin.enums.YesOrNo;
import com.zzlin.pojo.Carousel;
import com.zzlin.service.CarouseService;
import com.zzlin.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
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
}
