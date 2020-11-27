package com.zhowin.youmamall.home.model;


/**
 * author : zho
 * date  ：2020/11/26
 * desc ：首页多布局
 */
public class HomePageList   {


    public static final int RXB_TYPE = 1;//热销榜
    public static final int XPSF_TYPE = 2;//新品首发
    public static final int FLGN_TYPE = 3;//福利功能

    private int itemType;
    private String leftTitle;
    private String leftDesc;
    private boolean  isShowRight;

    public HomePageList(int itemType, String leftTitle, String leftDesc, boolean isShowRight) {
        this.itemType = itemType;
        this.leftTitle = leftTitle;
        this.leftDesc = leftDesc;
        this.isShowRight = isShowRight;
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
}
