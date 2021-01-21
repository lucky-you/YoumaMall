package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2021/1/21
 * desc ：我的推荐人信息
 */
public class MyReferrerMessage {

    private String f_nickname;
    private String f_mobile;
    private String f_avatar;
    private String f_wechat_qrcode;

    public MyReferrerMessage() {
    }

    public MyReferrerMessage(String f_nickname, String f_mobile, String f_avatar, String f_wechat_qrcode) {
        this.f_nickname = f_nickname;
        this.f_mobile = f_mobile;
        this.f_avatar = f_avatar;
        this.f_wechat_qrcode = f_wechat_qrcode;
    }

    public String getF_nickname() {
        return f_nickname;
    }

    public void setF_nickname(String f_nickname) {
        this.f_nickname = f_nickname;
    }

    public String getF_mobile() {
        return f_mobile;
    }

    public void setF_mobile(String f_mobile) {
        this.f_mobile = f_mobile;
    }

    public String getF_avatar() {
        return f_avatar;
    }

    public void setF_avatar(String f_avatar) {
        this.f_avatar = f_avatar;
    }

    public String getF_wechat_qrcode() {
        return f_wechat_qrcode;
    }

    public void setF_wechat_qrcode(String f_wechat_qrcode) {
        this.f_wechat_qrcode = f_wechat_qrcode;
    }
}
