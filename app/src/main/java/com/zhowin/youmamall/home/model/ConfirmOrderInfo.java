package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2020/12/19
 * desc ：提交订单信息
 */
public class ConfirmOrderInfo {


    /**
     * openid : 20206826084
     * url_qrcode : https://api.xunhupay.com/payments/alipay/qrcode?id=20206826084&nonce_str=1884381064&time=1608344881&appid=201906133968&hash=2dc993a33e8cca68ba6bfdecb8ce90ac
     * url : https://api.xunhupay.com/payments/alipay/index?id=20206826084&nonce_str=8186431084&time=1608344881&appid=201906133968&hash=b00565dea9c2c05f76c95e0a6b031bb4
     * errcode : 0
     * errmsg : success!
     * hash : 2d5f90c16317f696f04b133db1915313
     */

    private long openid;
    private String url_qrcode;
    private String url;
    private int errcode;
    private String errmsg;
    private String hash;

    public long getOpenid() {
        return openid;
    }

    public void setOpenid(long openid) {
        this.openid = openid;
    }

    public String getUrl_qrcode() {
        return url_qrcode;
    }

    public void setUrl_qrcode(String url_qrcode) {
        this.url_qrcode = url_qrcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
