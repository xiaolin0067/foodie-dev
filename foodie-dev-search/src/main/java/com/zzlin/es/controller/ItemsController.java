package com.zzlin.es.controller;

import com.zzlin.es.service.ItemESService;
import com.zzlin.utils.PagedGridResult;
import com.zzlin.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20210716
 */
@RequestMapping("items")
@RestController
public class ItemsController {

    @Resource
    private ItemESService itemESService;

    @GetMapping("/hello")
    public Object hello() {
        return "hello Elasticsearch!";
    }

    /**
     * 搜索商品
     * @return 商品列表
     */
    @GetMapping("/es/search")
    public Result search(
            String keywords,
            String sort,
            Integer page,
            Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return Result.errorMsg("用户ID为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        // ES分页从第0页开始
        page--;
        PagedGridResult pagedGridResult = itemESService.searchItems(keywords, sort, page, pageSize);
        return Result.ok(pagedGridResult);
    }
}
