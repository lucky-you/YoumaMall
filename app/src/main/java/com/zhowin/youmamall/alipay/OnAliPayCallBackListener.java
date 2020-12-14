package com.zhowin.youmamall.alipay;

/**
 * author      : Z_B
 * date       : 2019/1/29
 * function  : 支付宝支付结果的回调
 */
public interface OnAliPayCallBackListener {


    /**
     * 支付成功
     */
    void onSuccess();

    /**
     * 支付取消
     */
    void onCancel();

    /**
     * 支付失败
     */
    void onFailed();

}
