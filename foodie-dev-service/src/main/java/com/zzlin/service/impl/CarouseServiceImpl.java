package com.zzlin.service.impl;

import com.zzlin.mapper.CarouselMapper;
import com.zzlin.pojo.Carousel;
import com.zzlin.service.CarouseService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zlin
 * @date 20201225
 */
@Service
public class CarouseServiceImpl implements CarouseService {

    @Resource
    CarouselMapper carouselMapper;

    /**
     * 查询所有轮播图
     *
     * @param isShow 是否展示
     * @return 轮播图列表
     */
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        example.orderBy("sort").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        return carouselMapper.selectByExample(example);
    }
}
