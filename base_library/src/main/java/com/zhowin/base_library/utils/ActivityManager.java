package com.zhowin.base_library.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Iterator;
import java.util.Stack;

/**
 * Activity管理类
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
    }

    /**
     * 单一实例
     */
    public static synchronized ActivityManager getAppInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public synchronized void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
        Log.i("TAG", "ActivityManager添加了：" + activity.getClass().getName());
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 移除最后一个Activity
     */
    public void removeActivity(Activity activity) {
        try {
            if (activity != null) {
                activityStack.remove(activity);
                Log.i("TAG", "ActivityManager移除了：" + activity.getClass().getName());
            }
        } catch (Exception ex) {

        }

    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        try {
            activityStack.remove(activity);
            activity.finish();
            if (activity != null) {

                Log.i("TAG", "ActivityManager关闭了：" + activity.getClass().getName());
            }
        } catch (Exception ex) {
            Log.e("TAG", "ActivityManager关闭异常：" + activity.getClass().getName());
        }

    }

    /**
     * 结束除了MainActivity,和当前Activity以外的其他Activity
     */
    public void finishOtherTopActivity(Class activity) {
        try {
            if (activity != null) {
                while (true) {
                    Activity currentActivity = activityStack.lastElement();
                    if (currentActivity.getClass().getSimpleName().contains("MainActivity")) {
                        //防止不存在，所有activity全部退出
                        break;
                    }
                    if (currentActivity.getClass().getSimpleName().equals(activity.getClass().getSimpleName())) {
                        break;
                    } else {
                        activityStack.remove(currentActivity);
                        currentActivity.finish();
                    }
                }
                Log.i("TAG", "ActivityManager关闭了：" + activity.getClass().getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 结束指定的Activity上面的所有除了MainActivity
     */
    public void finishAllButCurrentActivity(Class activity) {
        try {
            if (activity != null) {
                Iterator<Activity> iterator = activityStack.iterator();
                while (iterator.hasNext()) {
                    Activity current = iterator.next();
                    if (!current.getClass().getSimpleName().equals(activity.getSimpleName())) {
                        iterator.remove();
                        current.finish();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            if (activityStack == null || activityStack.isEmpty()) return;
            for (int i = 0; i < activityStack.size(); i++) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    finishActivity(activityStack.get(i));
                    removeActivity(activityStack.get(i));
                    return;
                }
            }
        } catch (Exception ex) {

        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        try {
            for (Activity activity : activityStack) {
                if (activity != null) {
                    activity.finish();
                }
            }
            activityStack.clear();
        } catch (Exception ex) {

        }

    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
