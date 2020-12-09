package com.zhowin.youmamall.home.model;

import com.zhowin.youmamall.mall.model.MallRightList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/9
 * desc ：
 */
public class HomePageData {


    private List<MallRightList> new_item_list;
    private List<MallRightList> home_sale_item;


    public List<MallRightList> getNew_item_list() {
        return new_item_list;
    }

    public void setNew_item_list(List<MallRightList> new_item_list) {
        this.new_item_list = new_item_list;
    }

    public List<MallRightList> getHome_sale_item() {
        return home_sale_item;
    }

    public void setHome_sale_item(List<MallRightList> home_sale_item) {
        this.home_sale_item = home_sale_item;
    }
}
