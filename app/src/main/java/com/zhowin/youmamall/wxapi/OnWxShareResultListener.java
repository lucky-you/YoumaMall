package com.zhowin.youmamall.wxapi;

/**
 * desc :分享的回调
 */
public interface OnWxShareResultListener {

    /**
     * @param type 成功，失败，还是取消
     */
    void onShareResult(int type);
}
