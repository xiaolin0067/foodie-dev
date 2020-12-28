package com.zzlin.pojo.vo;

import com.zzlin.pojo.Items;
import com.zzlin.pojo.ItemsImg;
import com.zzlin.pojo.ItemsParam;
import com.zzlin.pojo.ItemsSpec;

import java.util.List;

/**
 * @author zlin
 * @date 20201228
 */
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

    public ItemInfoVO(Items item, List<ItemsImg> itemImgList, List<ItemsSpec> itemSpecList, ItemsParam itemParams) {
        this.item = item;
        this.itemImgList = itemImgList;
        this.itemSpecList = itemSpecList;
        this.itemParams = itemParams;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }
}
