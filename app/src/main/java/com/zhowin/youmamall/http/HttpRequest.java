package com.zhowin.youmamall.http;


import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.http.RetrofitFactory;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.qiniu.QiNiuYunBean;
import com.zhowin.base_library.utils.RxSchedulers;
import com.zhowin.youmamall.BuildConfig;
import com.zhowin.youmamall.dynamic.model.DynamicList;

import java.util.HashMap;


/**
 * description:
 */
public class HttpRequest {


    private static ApiRequest apiRequest;

    static {
        apiRequest = RetrofitFactory.getInstance().initRetrofit(BuildConfig.API_HOST).create(ApiRequest.class);
    }

    /**
     * 获取用户信息
     */
    public static void getUserInfoMessage(LifecycleOwner activity, final HttpCallBack<UserInfo> callBack) {
        apiRequest.getUserInfoMessage(UserInfo.getUserToken())
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<UserInfo>() {

                    @Override
                    public void onSuccess(UserInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 获取七牛云的信息
     */
    public static void getQiNiuYunBean(LifecycleOwner activity, final HttpCallBack<QiNiuYunBean> callBack) {
        apiRequest.getQiNiuYunBean(UserInfo.getUserToken())
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<QiNiuYunBean>() {

                    @Override
                    public void onSuccess(QiNiuYunBean demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 获取验证码
     */
    public static void getVerificationCode(LifecycleOwner activity, String event, String mobile, final HttpCallBack<Object> callBack) {
        apiRequest.getVerificationCode(event, mobile)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<Object>() {

                    @Override
                    public void onSuccess(Object demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 用户注册
     */
    public static void registerFromPhoneNumber(LifecycleOwner activity, HashMap<String, Object> map, final HttpCallBack<UserInfo> callBack) {
        apiRequest.registerFromPhoneNumber(map)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<UserInfo>() {

                    @Override
                    public void onSuccess(UserInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }


    /**
     * 手机号 + 密码登录
     */
    public static void loginMobileAndPassword(LifecycleOwner activity, String mobile, String password, final HttpCallBack<UserInfo> callBack) {
        apiRequest.loginMobileAndPassword(mobile, password)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<UserInfo>() {

                    @Override
                    public void onSuccess(UserInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 修改用户信息
     */
    public static void changeUserMessageInfo(LifecycleOwner activity, HashMap<String, Object> map, final HttpCallBack<UserInfo> callBack) {
        apiRequest.changeUserMessageInfo(UserInfo.getUserToken(), map)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<UserInfo>() {

                    @Override
                    public void onSuccess(UserInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 更换手机号码
     */
    public static void changUserMobile(LifecycleOwner activity, String password, String mobile, String captcha, final HttpCallBack<Object> callBack) {
        apiRequest.changUserMobile(password, mobile, captcha)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<Object>() {

                    @Override
                    public void onSuccess(Object demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 动态列表
     */
    public static void getDynamicList(LifecycleOwner activity, int pageNum, int pageSize, final HttpCallBack<BaseResponse<DynamicList>> callBack) {
        apiRequest.getDynamicList(UserInfo.getUserToken(), pageNum, pageSize)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<DynamicList>>() {

                    @Override
                    public void onSuccess(BaseResponse<DynamicList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }


}
