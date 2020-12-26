package com.zzlin.pojo.vo;

import java.util.List;

/**
 * 一级分类最新6个商品VO
 * @author zlin
 * @date 20201226
 */
public class CatNewItemsVO {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVo> simpleItemVoList;

    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemVo> getSimpleItemVoList() {
        return simpleItemVoList;
    }

    public void setSimpleItemVoList(List<SimpleItemVo> simpleItemVoList) {
        this.simpleItemVoList = simpleItemVoList;
    }
}
