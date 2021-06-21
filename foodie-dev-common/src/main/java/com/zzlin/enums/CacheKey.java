package com.zzlin.enums;

/**
 * @author zlin
 * @date 20210606
 */
public enum CacheKey {

    /**
     * 分隔符
     */
    SEPARATOR(":"),
    /**
     * 首页轮播图
     */
    INDEX_CAROUSEL("index:carousel"),
    /**
     * 首页商品一级分类
     */
    INDEX_CATEGORIES("index:category"),
    /**
     * 首页商品二级分类
     */
    INDEX_SUB_CATEGORIES("index:categoryvo"),
    /**
     * 购物车
     */
    SHOP_CART("shopcart"),
    /**
     * 用户
     */
    USER("user"),
    /**
     * 用户Token
     */
    USER_TOKEN("user:token")
    ;

    public final String value;

    CacheKey(String value){
        this.value = value;
    }

    public String append(Object param) {
        return this.value + SEPARATOR.value + param;
    }
}
