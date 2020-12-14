package com.zhowin.youmamall.wxapi;

/**
 * desc ： 微信支付的回调
 */

public interface OnWxPayResultListener {

    void onSuccess();

    void onCancel();

    void onFailed();

}
