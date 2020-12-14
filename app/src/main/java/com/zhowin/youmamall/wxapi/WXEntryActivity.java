package com.zhowin.youmamall.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信的回调
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI iwxapi;
    public static OnWeChatUserInfoListener onWeChatUserInfoListener;
    public static OnWxShareResultListener onWxShareResultListener;
    private MyHandler handler = new MyHandler();


    public static void setOnWeChatUserInfoListener(OnWeChatUserInfoListener onWeChatUserInfoListener) {
        WXEntryActivity.onWeChatUserInfoListener = onWeChatUserInfoListener;
    }

    public static void setOnWxShareResultListener(OnWxShareResultListener onWxShareResultListener) {
        WXEntryActivity.onWxShareResultListener = onWxShareResultListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WXLoginOrPayUtils wxLoginUtils = new WXLoginOrPayUtils();
        iwxapi = wxLoginUtils.registerWxToApp(this);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    private static class MyHandler extends Handler {

        public MyHandler() {

        }

        @Override
        public void handleMessage(Message msg) {
            int tag = msg.what;
            Bundle data = msg.getData();
            String openId, unionid, accessToken, refreshToken, scope;
            switch (tag) {
                case NetworkUtil.GET_TOKEN: {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(data.getString("result"));
                        openId = json.getString("openid");
                        unionid = json.getString("unionid");
                        accessToken = json.getString("access_token");
                        refreshToken = json.getString("refresh_token");
                        scope = json.getString("scope");
                        if (onWeChatUserInfoListener != null) {
                            onWeChatUserInfoListener.loadUserInfo(openId, unionid, accessToken, refreshToken, scope);
                        }
                    } catch (JSONException e) {
                        Log.e("xy", e.getMessage());
                    }
                }
            }
        }
    }


    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        switch (resp.getType()) {
            case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX: //分享
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        Intent intent = new Intent("SHARE_WX_ACTION");
                        intent.putExtra("errCode", resp.errCode);
                        sendBroadcast(intent);
                        if (onWxShareResultListener != null) {
                            onWxShareResultListener.onShareResult(1);
                        }
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        if (onWxShareResultListener != null) {
                            onWxShareResultListener.onShareResult(2);
                        }
                        break;
                    case BaseResp.ErrCode.ERR_SENT_FAILED:
                        if (onWxShareResultListener != null) {
                            onWxShareResultListener.onShareResult(3);
                        }
                        break;
                }
                break;
            case ConstantsAPI.COMMAND_SENDAUTH: //登录授权
                switch (resp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        SendAuth.Resp authResp = (SendAuth.Resp) resp;
                        final String code = authResp.code;
                        NetworkUtil.sendWxAPI(handler, String.format("https://api.weixin.qq.com/sns/oauth2/access_token?" +
                                        "appid=%s&secret=%s&code=%s&grant_type=authorization_code", WXLoginOrPayUtils.WX_APP_ID,
                                WXLoginOrPayUtils.WX_APP_SECRET, code), NetworkUtil.GET_TOKEN);
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        System.out.println("取消授权");
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        System.out.println("拒绝授权");
                        break;
                }
                break;
        }
        finish();
    }


}