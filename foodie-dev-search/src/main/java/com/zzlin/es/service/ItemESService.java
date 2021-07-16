package com.zzlin.es.service;

import com.zzlin.utils.PagedGridResult;

/**
 * @author zlin
 * @date 20210716
 */
public interface ItemESService {

    /**
     * 搜索商品
     * @param keywords 搜索关键字
     * @param sort 排序规则，c: 根据销量排序，p: 根据价格排序
     * @param page 页码
     * @param pageSize 每页数量
     * @return 商品列表
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);
}
