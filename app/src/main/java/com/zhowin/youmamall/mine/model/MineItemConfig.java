package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/24
 * desc ：我的界面配置
 */
public class MineItemConfig {


    /**
     * open_upgrade : 1
     * open_withdraw : 1
     * h5_domain : http://yz.longtai360.com/h5
     * ios_url : https://fanyi.baidu.com
     * android_url : https://fanyi.baidu.com
     */

    private String open_upgrade;
    private String open_withdraw;
    private String h5_domain;
    private String ios_url;
    private String android_url;

    public String getOpen_upgrade() {
        return open_upgrade;
    }

    public void setOpen_upgrade(String open_upgrade) {
        this.open_upgrade = open_upgrade;
    }

    public String getOpen_withdraw() {
        return open_withdraw;
    }

    public void setOpen_withdraw(String open_withdraw) {
        this.open_withdraw = open_withdraw;
    }

    public String getH5_domain() {
        return h5_domain;
    }

    public void setH5_domain(String h5_domain) {
        this.h5_domain = h5_domain;
    }

    public String getIos_url() {
        return ios_url;
    }

    public void setIos_url(String ios_url) {
        this.ios_url = ios_url;
    }

    public String getAndroid_url() {
        return android_url;
    }

    public void setAndroid_url(String android_url) {
        this.android_url = android_url;
    }
}
