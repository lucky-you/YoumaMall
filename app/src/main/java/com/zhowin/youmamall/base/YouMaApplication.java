package com.zhowin.youmamall.base;

import android.content.Context;
import android.text.TextUtils;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhowin.base_library.base.BaseApplication;
import com.zhowin.youmamall.BuildConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        initBugLy();
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

    private void initBugLy() {
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setUploadProcess(processName == null || processName.equals(this.getPackageName()));
        // 初始化Bugly
        CrashReport.initCrashReport(this, "fcc0cc06ee", BuildConfig.DEBUG, strategy);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


}
