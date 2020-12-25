package com.zzlin.service;

import com.zzlin.pojo.Carousel;

import java.util.List;

/**
 * @author zlin
 * @date 20201225
 */
public interface CarouseService {

    /**
     * 查询所有轮播图
     * @param isShow 是否展示
     * @return 轮播图列表
     */
    List<Carousel> queryAll(Integer isShow);
}
