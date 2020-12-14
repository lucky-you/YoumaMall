package com.zhowin.youmamall.wxapi;

/**
 * desc ：微信返回的信息
 */
public interface OnWeChatUserInfoListener {

    void loadUserInfo(String openId, String unionid, String accessToken, String refreshToken, String scope);
}
