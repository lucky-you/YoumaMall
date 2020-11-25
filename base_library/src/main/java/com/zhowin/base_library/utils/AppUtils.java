package com.zhowin.base_library.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;


import com.zhowin.base_library.base.BaseApplication;

import java.lang.reflect.Method;
import java.util.List;

public class AppUtils {


    public static String getAndroidId() {
        return Settings.Secure.getString(BaseApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 获取设备序列号
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // 9.0 +
                serial = getAndroidId();
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                // 8.0 +
                serial = Build.SERIAL;
            } else {
                // 8.0 -
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("xy", "获取设备序列号失败");
        }
        return serial;
    }


    /**
     * 获取版本号
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 获取版本号名称
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取当前应用程序的包名
     */
    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

    /**
     * 获取程序 图标
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        try {
            //包管理操作管理类
            PackageManager pm = context.getPackageManager();
            //获取到应用信息
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取程序的名字
     */
    public static String getAppName(Context context, String packageName) {
        //包管理操作管理类
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return packageName;
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {       //0代表相等，1代表version1大于version2，-1代表version1小于version2
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }


}
