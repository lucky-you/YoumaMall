package com.zhowin.base_library.qiniu;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;

public class SingleUploadFiles implements LifecycleObserver {
    ArrayList<String> source;
    QiNiuYunBean qiNiuYunBean;
    SingleUploadListener singleUploadListener;
    ArrayList<String> urls = new ArrayList<>();
    int currentUploadFile;

    public SingleUploadFiles(ArrayList<String> files, QiNiuYunBean qiNiuYunBean) {
        source = files;
        this.qiNiuYunBean = qiNiuYunBean;
    }

    public void startUpload() {
        QinIuUtils.qinIuUpLoad(source.get(currentUploadFile), qiNiuYunBean.getToken(), new QinIuUpLoadListener() {
            @Override
            public void upLoadSuccess(String path) {
                if (isDestroy) return;
                urls.add(path);
                currentUploadFile++;
                if (currentUploadFile == source.size()) {
                    singleUploadListener.uploadSuccess(urls);
                } else {
                    startUpload();
                }

            }

            @Override
            public void upLoadFail(String errorMessage) {
                if (singleUploadListener != null) {
                    singleUploadListener.uploadFail(errorMessage);
                }
            }
        });
    }

    public void setQinIuUpLoadListener(SingleUploadListener qinIuUpLoadListener) {
        this.singleUploadListener = qinIuUpLoadListener;
    }

    public interface SingleUploadListener {
        void uploadSuccess(ArrayList<String> urls);

        void uploadFail(String errorMessage);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        System.out.println("onAny:" + event.name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        System.out.println("onCreate");
    }

    boolean isDestroy;

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        System.out.println("onDestroy");
        isDestroy = true;
    }
}
