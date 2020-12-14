package com.zhowin.youmamall.wxapi;

import com.google.gson.annotations.SerializedName;

/**
 * author : zho
 * date  ：2020/8/31
 * desc ：支付返回的信息
 */
public class PaymentReqInfo {

    /**
     * appId : wx28a1ed75f6e246a4
     * timeStamp : 1596877426
     * nonceStr : joanjEIZHyAu9vZV
     * signType : MD5
     * paySign : 0829B44684018B22270B7590FD82776B
     * prepayId : wx08170346678013b24e6dc6b07e27050000
     * partnerId : 1512118311
     * package : Sign=WXPay
     */

    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String signType;
    private String paySign;
    private String prepayId;
    private String partnerId;
    @SerializedName("package")
    private String packageX;

    // 此字段支付宝使用
    private String body;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
