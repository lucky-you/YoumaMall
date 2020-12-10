package com.zhowin.youmamall.home.model;

import com.zhowin.base_library.banner.BannerList;
import com.zhowin.youmamall.mall.model.MallLeftList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/10
 * desc ：首页动态的数据
 */
public class HomeDynamicInfo {


    private List<BannerList> home_list;
    private List<MallLeftList> category_list;
    private List<VipWelfareList> vip_rule_list;
    private List<BannerList> slide_list;

    public List<BannerList> getHome_list() {
        return home_list;
    }

    public void setHome_list(List<BannerList> home_list) {
        this.home_list = home_list;
    }

    public List<MallLeftList> getCategory_list() {
        return category_list;
    }

    public void setCategory_list(List<MallLeftList> category_list) {
        this.category_list = category_list;
    }

    public List<VipWelfareList> getVip_rule_list() {
        return vip_rule_list;
    }

    public void setVip_rule_list(List<VipWelfareList> vip_rule_list) {
        this.vip_rule_list = vip_rule_list;
    }

    public List<BannerList> getSlide_list() {
        return slide_list;
    }

    public void setSlide_list(List<BannerList> slide_list) {
        this.slide_list = slide_list;
    }
}
