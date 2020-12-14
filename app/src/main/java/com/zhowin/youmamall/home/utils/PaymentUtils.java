package com.zhowin.youmamall.home.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhowin.youmamall.alipay.AliPayUtils;
import com.zhowin.youmamall.alipay.OnAliPayCallBackListener;
import com.zhowin.youmamall.wxapi.OnWxPayResultListener;
import com.zhowin.youmamall.wxapi.PaymentReqInfo;
import com.zhowin.youmamall.wxapi.WXLoginOrPayUtils;

/**
 * author Z_B
 * date :2020/6/28 15:59
 * description: 支付的工具类
 */
public class PaymentUtils {


    /**
     * 使用微信支付
     */
    public static void userWeChatPaymentOrder(Context mContext, PaymentReqInfo wxPayResult, OnWxPayResultListener wxPayResultListener) {
        if (wxPayResult != null) {
            PayReq request = new PayReq();
            request.appId = wxPayResult.getAppId();
            request.partnerId = wxPayResult.getPartnerId();
            request.prepayId = wxPayResult.getPrepayId();
            request.packageValue = wxPayResult.getPackageX();
            request.nonceStr = wxPayResult.getNonceStr();
            request.timeStamp = wxPayResult.getTimeStamp();
            request.sign = wxPayResult.getPaySign();
            WXLoginOrPayUtils.useWeChatPay(mContext, request, wxPayResultListener);
        }
    }

    /**
     * 使用支付宝支付
     */
    public static void userAliPayPaymentOrder(Activity mContext, String orderInfo, OnAliPayCallBackListener listener) {
        if (!TextUtils.isEmpty(orderInfo)) {
            AliPayUtils aliPayUtils = new AliPayUtils(mContext);
            aliPayUtils.requestPayFromServiceSide(orderInfo);
            aliPayUtils.setPayListener(listener);
        }
    }
}
