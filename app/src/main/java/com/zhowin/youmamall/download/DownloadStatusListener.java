package com.zhowin.youmamall.download;

/**
 * 下载状态的监听
 */
public interface DownloadStatusListener {
    /**
     * 开始
     */
    void onStart();

    /**
     * 当前进度
     *
     * @param currentLength 进度
     */
    void onProgress(int currentLength);

    /**
     * 下载结束
     *
     * @param localPath 路径
     */
    void onFinish(String localPath);

    /**
     * 下载失败
     *
     * @param errorInfo 信息
     */
    void onFailure(String errorInfo);
}
