package com.zhowin.youmamall.download;

/**
 * author : zho
 * date  ：2020/12/25
 * desc ：下载的回调
 */
public interface DownFileListener {

    void loadSuccess( );

    void loadProgress(int progress);

    void fail(String error);

}
