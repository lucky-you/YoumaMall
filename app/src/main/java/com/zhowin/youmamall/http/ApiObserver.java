package com.zhowin.youmamall.http;


import com.google.gson.JsonParseException;
import com.zhowin.base_library.base.BaseApplication;
import com.zhowin.base_library.callback.OnNoNetWorkClickListener;
import com.zhowin.base_library.http.ApiResponse;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.view.NoNetWorkDialogView;
import com.zhowin.youmamall.login.activity.LoginActivity;
import com.zhowin.youmamall.main.activity.MainActivity;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava2.HttpException;


public abstract class ApiObserver<T> implements Observer<ApiResponse<T>> {

    public static final int NET_ERROR = -1;
    public static final int JSON_ERROR = -2;
    public static final int DATA_ERROR = -3;
    public static final int TOKEN_ERROR = -4;
    public static final int PERMISSION_ERROR = -5;

    @Override
    public void onSubscribe(Disposable d) {
        subscribe(d);
    }

    @Override
    public void onNext(ApiResponse<T> response) {
        //在这边对 基础数据 进行统一处理  初步解析：
        if (response.getCode() == 1) {
            onSuccess(response.getData());
        } else if (response.getCode() == 401) {
            onFail(response.getCode(), response.getMsg());
            showNoNetWorkDialog();
        } else {
            onFail(response.getCode(), response.getMsg());
        }
    }

    private void showNoNetWorkDialog() {
        NoNetWorkDialogView noNetWorkDialogView = new NoNetWorkDialogView(BaseApplication.getInstance());
        noNetWorkDialogView.setHitTitleMessage("Token已失效,请重新登录");
        noNetWorkDialogView.show();
        noNetWorkDialogView.setOnNoNetWorkClickListener(new OnNoNetWorkClickListener() {
            @Override
            public void onDurationClick() {
                UserInfo.setUserInfo(new UserInfo());
                LoginActivity.start(LoginActivity.getInstance);
                ActivityManager.getAppInstance().finishActivity(MainActivity.class);
            }
        });
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable throwable) {
        String error;
        if (throwable instanceof SocketTimeoutException  //网络超时,网络连接异常
                || throwable instanceof ConnectException   //均视为网络异常
                || throwable instanceof UnknownHostException) {
            onFail(NET_ERROR, "网络错误");
        } else if (throwable instanceof JsonParseException//均视为解析错误
                || throwable instanceof java.text.ParseException) {
            error = throwable.getMessage();
            onFail(JSON_ERROR, "数据解析异常:" + error);
        } else if (throwable instanceof HttpException) {
//            Log.e("xy", "错误: " + throwable.getMessage());
            onFail(JSON_ERROR, "Http错误: " + throwable.getMessage());
        }

    }

    public abstract void onSuccess(T demo);

    public void subscribe(Disposable d) {

    }

    public abstract void onFail(int errorCode, String errorMsg);
}
