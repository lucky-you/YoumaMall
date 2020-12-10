package com.zhowin.youmamall.home.model;


import com.zhowin.youmamall.mall.model.MallRightList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：首页多布局
 */
public class HomePageList {


    public static final int RXB_TYPE = 1;//热销榜
    public static final int XPSF_TYPE = 2;//新品首发
    public static final int FLGN_TYPE = 3;//福利功能

    private int itemType;
    private String leftTitle;
    private String leftDesc;
    private boolean isShowRight;
    private List<MallRightList> goodDataList;
    private List<VipWelfareList> vipWelfareList;




    public HomePageList(int itemType, String leftTitle, String leftDesc, boolean isShowRight, List<MallRightList> goodDataList) {
        this.itemType = itemType;
        this.leftTitle = leftTitle;
        this.leftDesc = leftDesc;
        this.isShowRight = isShowRight;
        this.goodDataList = goodDataList;
    }

    public HomePageList(int itemType, String leftTitle, String leftDesc, boolean isShowRight, List<MallRightList> goodDataList, List<VipWelfareList> vipWelfareList) {
        this.itemType = itemType;
        this.leftTitle = leftTitle;
        this.leftDesc = leftDesc;
        this.isShowRight = isShowRight;
        this.goodDataList = goodDataList;
        this.vipWelfareList = vipWelfareList;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public String getLeftDesc() {
        return leftDesc;
    }

    public void setLeftDesc(String leftDesc) {
        this.leftDesc = leftDesc;
    }

    public boolean isShowRight() {
        return isShowRight;
    }

    public void setShowRight(boolean showRight) {
        isShowRight = showRight;
    }

    public List<MallRightList> getGoodDataList() {
        return goodDataList;
    }

    public void setGoodDataList(List<MallRightList> goodDataList) {
        this.goodDataList = goodDataList;
    }


    public List<VipWelfareList> getVipWelfareList() {
        return vipWelfareList;
    }

    public void setVipWelfareList(List<VipWelfareList> vipWelfareList) {
        this.vipWelfareList = vipWelfareList;
    }
}
