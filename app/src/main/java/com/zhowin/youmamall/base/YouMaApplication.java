package com.zhowin.youmamall.base;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.zhowin.base_library.base.BaseApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * author : zho
 * date  ：2020/11/25
 * desc ：
 */
public class YouMaApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000) // set connection timeout.
                        .readTimeout(15000) // set read timeout.
                )).maxNetworkThreadCount(2)
                .connectionCountAdapter(new FileDownloadHelper.ConnectionCountAdapter() {
                    @Override
                    public int determineConnectionCount(int downloadId, String url, String path, long totalLength) {
                        return 2;
                    }
                })
                .commit();
    }

}
