package com.zhowin.youmamall.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.modelbiz.AddCardToWXCardPackage;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhowin.youmamall.R;

import java.util.List;

/**
 * 微信登录的工具类
 */
public class WXLoginOrPayUtils {

    private volatile static WXLoginOrPayUtils instance;

    public static final String WX_APP_ID = "wx4c9bdb5d809f2f0d";

    public static final String WX_APP_SECRET = "427b147e7efe1f7b9f47d097c3e7e319";

    public WXLoginOrPayUtils() {

    }

    public static WXLoginOrPayUtils getInstance() {
        if (instance == null) {
            synchronized (WXLoginOrPayUtils.class) {
                if (instance == null) {
                    instance = new WXLoginOrPayUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 判断是否存在 微信
     */
    public boolean isExist(Context context) {
        IWXAPI WxApi = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
        return WxApi.isWXAppInstalled();
    }

    /**
     * 注册微信
     */
    public IWXAPI registerWxToApp(Context mContext) {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(mContext, WX_APP_ID, false);
        wxApi.registerApp(WX_APP_ID);
        return wxApi;
    }

    /**
     * 获取授权
     */
    public void getWeChatAuthorization(Context context) {
        IWXAPI wxApi = registerWxToApp(context);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_login";
        wxApi.sendReq(req);
    }


    public void setRegisterCallBackListener(Context context, OnWeChatUserInfoListener onWeChatUserInfoListener) {
        if (onWeChatUserInfoListener != null) {
            IWXAPI api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
            api.registerApp(WX_APP_ID);
            WXEntryActivity.setOnWeChatUserInfoListener(onWeChatUserInfoListener);
        }
    }


    public void setWxChatShareResultListener(Context context, OnWxShareResultListener onWxShareResultListener) {
        if (onWxShareResultListener != null) {
            IWXAPI api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
            api.registerApp(WX_APP_ID);
            WXEntryActivity.setOnWxShareResultListener(onWxShareResultListener);
        }
    }

    /**
     * 使用微信支付
     */
    public static void useWeChatPay(Context context, PayReq req, OnWxPayResultListener wxPayResultListener) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
        api.registerApp(WX_APP_ID);
        api.sendReq(req);
        WXPayEntryActivity.registerWxPayResultListener(wxPayResultListener);
    }

    /**
     * 微信卡券添加到卡包
     */
    public static void AddCardToWXCardPackage(Context context, AddCardToWXCardPackage.Req req, List<AddCardToWXCardPackage.WXCardItem> cardArray, OnWxPayResultListener wxPayResultListener) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, WX_APP_ID, false);
        api.registerApp(WX_APP_ID);
        req.cardArrary = cardArray;
        api.sendReq(req);
        WXPayEntryActivity.registerWxPayResultListener(wxPayResultListener);
    }


    /**
     * @param mContext    上下文
     * @param isWxCircle  true为朋友圈，false为朋友圈好友
     * @param title       标题
     * @param imageUrl    图片链接
     * @param pageUrl     网页链接
     * @param description 内容描述
     */
    public void shareUrlToWX(final Context mContext, final boolean isWxCircle, final String title,
                             final String imageUrl, final String pageUrl, final String description) {
        final IWXAPI iwxapi = registerWxToApp(mContext);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WXWebpageObject webPage = new WXWebpageObject();
                    webPage.webpageUrl = pageUrl;//分享出去的网页地址
                    WXMediaMessage msg = new WXMediaMessage(webPage);
                    msg.title = title;//分享的标题
                    msg.description = description;//分享的描述信息
                    Bitmap bmp = WxBitmapUtils.getBitmapByDrawable(mContext, R.drawable.ic_launcher_background);
                    //设置缩略图 一定要小于32k，否则微信会报错
                    Bitmap thumbBmp = WxBitmapUtils.compressScale(bmp);
                    msg.thumbData = WxBitmapUtils.bmpToByteArray(thumbBmp, true);
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("webPage");
                    req.message = msg;
                    req.scene = isWxCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                    iwxapi.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("xy", "e:" + e.getMessage());
                }
            }
        }).start();
    }


    private String buildTransaction(final String type) {
        return (TextUtils.isEmpty(type)) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
