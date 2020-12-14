package com.zhowin.youmamall.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * 微信支付的回调类
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;
    public static OnWxPayResultListener wxPayResultListener;



    public static void registerWxPayResultListener(OnWxPayResultListener PayResultListener) {
        wxPayResultListener = PayResultListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXLoginOrPayUtils WXLoginOrPayUtils = new WXLoginOrPayUtils();
        iwxapi = WXLoginOrPayUtils.registerWxToApp(this);
        iwxapi.handleIntent(getIntent(), this);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_ADD_CARD_TO_EX_CARD_PACKAGE:
            case ConstantsAPI.COMMAND_CHOOSE_CARD_FROM_EX_CARD_PACKAGE:
                String wxOpenId = baseReq.openId;
                String wxTransaction = baseReq.transaction;
                System.out.println("wxOpenId=" + wxOpenId + "\n" + "wxTransaction=" + wxTransaction);
                break;
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX: //支付
//            case ConstantsAPI.COMMAND_ADD_CARD_TO_EX_CARD_PACKAGE: //添加到卡包
                if (baseResp.errCode == 0) { //成功
                    if (wxPayResultListener != null) {
                        wxPayResultListener.onSuccess();
                    }
                } else if (baseResp.errCode == -2) { //取消
                    if (wxPayResultListener != null) {
                        wxPayResultListener.onCancel();
                    }
                } else if (baseResp.errCode == -1) { //失败
                    if (wxPayResultListener != null) {
                        wxPayResultListener.onFailed();
                    }
                }
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("err_code", baseResp.errCode + "");
        sendBroadcast(intent);
        finish();//这里重要，如果没有 finish()；将留在微信支付后的界面.

    }
}
