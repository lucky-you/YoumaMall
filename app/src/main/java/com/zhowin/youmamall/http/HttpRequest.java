package com.zhowin.youmamall.http;


import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.ApiUtils;
import com.google.gson.Gson;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.http.RetrofitFactory;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.qiniu.QiNiuYunBean;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GsonUtils;
import com.zhowin.base_library.utils.LogUtils;
import com.zhowin.base_library.utils.RxSchedulers;
import com.zhowin.youmamall.BuildConfig;
import com.zhowin.youmamall.circle.model.CircleList;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.home.model.ConfirmOrderInfo;
import com.zhowin.youmamall.home.model.GoodDetailsInfo;
import com.zhowin.youmamall.home.model.HomeDynamicInfo;
import com.zhowin.youmamall.home.model.HomePageData;
import com.zhowin.youmamall.home.model.HotKeywordList;
import com.zhowin.youmamall.home.model.MessageCategory;
import com.zhowin.youmamall.home.model.MessageList;
import com.zhowin.youmamall.home.model.ResourcesCategory;
import com.zhowin.youmamall.home.model.ResourcesDetailsInfo;
import com.zhowin.youmamall.home.model.ResourcesList;
import com.zhowin.youmamall.home.model.UnreadMessageInfo;
import com.zhowin.youmamall.mall.model.MallLeftList;
import com.zhowin.youmamall.mall.model.MallRightList;
import com.zhowin.youmamall.mine.model.AccountTurnoverList;
import com.zhowin.youmamall.mine.model.AgentList;
import com.zhowin.youmamall.mine.model.ContactServiceList;
import com.zhowin.youmamall.mine.model.DepositMessage;
import com.zhowin.youmamall.mine.model.MallOrderList;
import com.zhowin.youmamall.mine.model.MineItemConfig;
import com.zhowin.youmamall.mine.model.MyTeamInfo;
import com.zhowin.youmamall.mine.model.SalesTurnoverList;
import com.zhowin.youmamall.mine.model.ShareMaterialList;
import com.zhowin.youmamall.mine.model.SoldGoodList;

import java.util.HashMap;
import java.util.List;


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
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_USER_INFO_MESSAGE_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.getUserInfoMessage(UserInfo.getUserToken(), paramJson)
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
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_QI_NIU_TOKEN_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.getQiNiuYunBean(UserInfo.getUserToken(), paramJson)
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

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.SEND_EMS_CODE);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("event", event);
        paramMap.put("mobile", mobile);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getVerificationCode(UserInfo.getUserToken(), paramJson)
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

        map.put("method", ApiRequest.REGISTER);
        map.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(map);

        apiRequest.registerFromPhoneNumber(UserInfo.getUserToken(), paramJson)
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


        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.MOBILE_AND_PASSWORD_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("account", mobile);
        paramMap.put("password", password);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.loginMobileAndPassword(UserInfo.getUserToken(), paramJson)
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
     * 退出登录
     */
    public static void outLoginApp(LifecycleOwner activity, boolean isLogout, final HttpCallBack<Object> callBack) {
        String url = isLogout ? ApiRequest.LOGIN_OUT_URL : ApiRequest.REMOVE_MERCHANT_LIST_URL;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", url);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.outLoginAppOrRemoveMerchant(UserInfo.getUserToken(), paramJson)
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
     * 修改用户信息
     */
    public static void changeUserMessageInfo(LifecycleOwner activity, String avatar, String nickname, String qrCode, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.CHANGE_USER_INFO_MESSAGE_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("avatar", avatar);
        paramMap.put("nickname", nickname);
        paramMap.put("wechat_qrcode", qrCode);
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.changeUserMessageInfo(UserInfo.getUserToken(), paramJson)
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
     * 更换手机号码
     */
    public static void changUserMobile(LifecycleOwner activity, String password, String mobile, String captcha, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.CHANGE_USER_MOBILE_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("password", password);
        paramMap.put("mobile", mobile);
        paramMap.put("captcha", captcha);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.changUserMobile(UserInfo.getUserToken(), paramJson)
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
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_DYNAMIC_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("page", pageNum);
        paramMap.put("size", pageSize);
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.getDynamicList(UserInfo.getUserToken(), paramJson)
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

    /**
     * 圈子列表
     */
    public static void getCircleList(LifecycleOwner activity, int pageNum, int pageSize, final HttpCallBack<BaseResponse<CircleList>> callBack) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_CIRCLE_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("page", pageNum);
        paramMap.put("size", pageSize);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getCircleList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<CircleList>>() {

                    @Override
                    public void onSuccess(BaseResponse<CircleList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 发布圈子
     */
    public static void releaseCircleData(LifecycleOwner activity, String name, String content, String images, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.RELEASE_CIRCLE_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("title", name);
        paramMap.put("content", content);
        paramMap.put("images", images);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.releaseCircleData(UserInfo.getUserToken(), paramJson)
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
     * 商城左侧分类
     */
    public static void getMallLeftList(LifecycleOwner activity, final HttpCallBack<List<MallLeftList>> callBack) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_MALL_LEFT_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getMallLeftList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<List<MallLeftList>>() {

                    @Override
                    public void onSuccess(List<MallLeftList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * VIP商品列表  和 商品列表    复购商品列表
     */
    public static void getMallRightList(LifecycleOwner activity, int type, HashMap<String, Object> map, final HttpCallBack<BaseResponse<MallRightList>> callBack) {
        String url = null;
        switch (type) {
            case 1: //商品列表
                url = ApiRequest.MALL_GOOD_LIST_URL;
                break;
            case 2://VIP商品列表
                url = ApiRequest.GET_VIP_GOOD_LIST_URL;
                break;
            case 3://复购商品列表
                url = ApiRequest.REPURCHASE_GOOD_LIST_URL;
                break;
        }
        map.put("method", url);
        map.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(map);
        apiRequest.getMallRightList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<MallRightList>>() {

                    @Override
                    public void onSuccess(BaseResponse<MallRightList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 商品详情
     */
    public static void getGoodDetails(LifecycleOwner activity, int itemId, final HttpCallBack<GoodDetailsInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_GOOD_DETAILS_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("item_id", itemId);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getGoodDetails(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<GoodDetailsInfo>() {

                    @Override
                    public void onSuccess(GoodDetailsInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 联系客服
     */
    public static void getContactServiceList(LifecycleOwner activity, final HttpCallBack<List<ContactServiceList>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.CONTACT_SERVICE_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getContactServiceList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<List<ContactServiceList>>() {

                    @Override
                    public void onSuccess(List<ContactServiceList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 设置支付密码
     */
    public static void setPayPassword(LifecycleOwner activity, String password, String pay_password, String pay_password_again, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.SET_PAY_PASSWORD_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("password", password);
        paramMap.put("pay_password", pay_password);
        paramMap.put("pay_password_again", pay_password_again);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.setPayPassword(UserInfo.getUserToken(), paramJson)
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
     * 忘记密码/重置密码
     */
    public static void setResetPassword(LifecycleOwner activity, String mobile, String newpassword, String captcha, final HttpCallBack<Object> callBack) {


        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.FOR_GET_PASSWORD_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("mobile", mobile);
        paramMap.put("newpassword", newpassword);
        paramMap.put("captcha", captcha);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.setResetPassword(UserInfo.getUserToken(), paramJson)
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
     * 获取首页信息
     */
    public static void getHomePageDataInfo(LifecycleOwner activity, final HttpCallBack<HomePageData> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_HOME_PAGE_DATA_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.getHomePageDataInfo(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<HomePageData>() {

                    @Override
                    public void onSuccess(HomePageData demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 首页banner 和 福利功能
     */
    public static void getHomeDynamicDataInfo(LifecycleOwner activity, final HttpCallBack<HomeDynamicInfo> callBack) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_HOME_BANNER_AND_VIP_DATA_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.getHomeDynamicDataInfo(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<HomeDynamicInfo>() {

                    @Override
                    public void onSuccess(HomeDynamicInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 提交订单
     */
    public static void confirmOrder(LifecycleOwner activity, int id, int type, String password, String payOrder, final HttpCallBack<ConfirmOrderInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.CONFIRM_ORDER_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("id", id);
        paramMap.put("type", type);
        paramMap.put("pay_password", password);
        paramMap.put("pay_no", payOrder);
        String paramJson = GsonUtils.toJson(paramMap);


        apiRequest.confirmOrder(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<ConfirmOrderInfo>() {

                    @Override
                    public void onSuccess(ConfirmOrderInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 商品上架、下架
     */
    public static void goodOffOrPutOnShelf(LifecycleOwner activity, boolean isPutOn, int id, final HttpCallBack<Object> callBack) {
        String url = isPutOn ? ApiRequest.PUT_ON_THE_SHELF_URL : ApiRequest.GOOD_OFF_SHELF_URL;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", url);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("id", id);
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.goodOffShelf(UserInfo.getUserToken(), paramJson)
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
     * 录入卡密
     */
    public static void onEnterCardSecret(LifecycleOwner activity, int id, String content, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.ENTER_CARD_SECRET_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("id", id);
        paramMap.put("content", content);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.onEnterCardSecret(UserInfo.getUserToken(), paramJson)
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
     * 清空卡密
     */
    public static void onClearCardSecret(LifecycleOwner activity, int id, final HttpCallBack<Object> callBack) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.CLEAR_CARD_SECRET_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("id", id);
        String paramJson = GsonUtils.toJson(paramMap);
        apiRequest.onEnterCardSecret(UserInfo.getUserToken(), paramJson)
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
     * 保证金信息
     */
    public static void getDepositMessage(LifecycleOwner activity, final HttpCallBack<DepositMessage> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_DEPOSIT_MESSAGE_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getDepositMessage(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<DepositMessage>() {

                    @Override
                    public void onSuccess(DepositMessage demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 立即入住
     */
    public static void startDepositPayment(LifecycleOwner activity, int type, String password, final HttpCallBack<ConfirmOrderInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.START_PAYMENT_DEPOSIT_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("type", type);
        paramMap.put("pay_password", password);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.startDepositPayment(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<ConfirmOrderInfo>() {

                    @Override
                    public void onSuccess(ConfirmOrderInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 账号流水
     */
    public static void getAccountTurnoverList(LifecycleOwner activity, boolean isWithdraw, int page, int size, final HttpCallBack<BaseResponse<AccountTurnoverList>> callBack) {
        String url = isWithdraw ? ApiRequest.WITHDRAWALS_RECORD_LIST_URL : ApiRequest.ACCOUNT_TURNOVER_LIST_URL;

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", url);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getAccountTurnoverList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<AccountTurnoverList>>() {

                    @Override
                    public void onSuccess(BaseResponse<AccountTurnoverList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 发布 修改 商品
     */
    public static void releaseOrChangeGood(LifecycleOwner activity, HashMap<String, Object> map, final HttpCallBack<Object> callBack) {

        map.put("method", ApiRequest.RELEASE_GOOD_URL);
        map.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(map);

        apiRequest.releaseOrChangeGood(UserInfo.getUserToken(), paramJson)
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
     * 获取商品列表
     */
    public static void getGoodList(LifecycleOwner activity, int page, int size, final HttpCallBack<BaseResponse<MallRightList>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_USER_GOOD_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getGoodList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<MallRightList>>() {

                    @Override
                    public void onSuccess(BaseResponse<MallRightList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 已售商品列表
     */
    public static void getSoldGoodList(LifecycleOwner activity, int page, int size, final HttpCallBack<BaseResponse<SoldGoodList>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_SOLD_GOOD_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getSoldGoodList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<SoldGoodList>>() {

                    @Override
                    public void onSuccess(BaseResponse<SoldGoodList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 出售流水
     */
    public static void getSalesTurnoverList(LifecycleOwner activity, int page, int size, final HttpCallBack<BaseResponse<SalesTurnoverList>> callBack) {


        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_SALES_TURNOVER_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);


        apiRequest.getSalesTurnoverList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<SalesTurnoverList>>() {

                    @Override
                    public void onSuccess(BaseResponse<SalesTurnoverList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 订单列表
     */
    public static void getMallOrderList(LifecycleOwner activity, int status, int page, int size, final HttpCallBack<BaseResponse<MallOrderList>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_MALL_ORDER_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("status", status);
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getMallOrderList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<MallOrderList>>() {

                    @Override
                    public void onSuccess(BaseResponse<MallOrderList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 确认收货
     */
    public static void goodConfirmReceipt(LifecycleOwner activity, int goodId, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.CONFIRM_RECEIPT_GOOD_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("id", goodId);
        String paramJson = GsonUtils.toJson(paramMap);


        apiRequest.goodConfirmReceipt(UserInfo.getUserToken(), paramJson)
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
     * 分享素材
     */
    public static void getShareMaterial(LifecycleOwner activity, final HttpCallBack<List<ShareMaterialList>> callBack) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_SHARE_MATER_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getShareMaterial(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<List<ShareMaterialList>>() {

                    @Override
                    public void onSuccess(List<ShareMaterialList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }


    /**
     * 申请提现
     */
    public static void applyWithdraw(LifecycleOwner activity, HashMap<String, Object> map, final HttpCallBack<Object> callBack) {

        map.put("method", ApiRequest.APPLY_WITHDRAW_LIST_URL);
        map.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(map);

        apiRequest.applyWithdraw(UserInfo.getUserToken(), paramJson)
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
     * 获取未读消息
     */
    public static void getUnreadMessageInfo(LifecycleOwner activity, final HttpCallBack<UnreadMessageInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_UNREAD_MESSAGE_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getUnreadMessageInfo(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<UnreadMessageInfo>() {

                    @Override
                    public void onSuccess(UnreadMessageInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 开通vip / 代理的规则
     */
    public static void getVipOrAgentRule(LifecycleOwner activity, boolean isOpenVIP, final HttpCallBack<List<AgentList>> callBack) {
        String url = isOpenVIP ? ApiRequest.OPEN_VIP_RULE_URL : ApiRequest.OPEN_AGENT_RULE_URL;
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", url);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getVipOrAgentRule(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<List<AgentList>>() {

                    @Override
                    public void onSuccess(List<AgentList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 开通vip
     */
    public static void openVIP(LifecycleOwner activity, int type, final HttpCallBack<ConfirmOrderInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.OPEN_VIP_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("type", type);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.openVIP(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<ConfirmOrderInfo>() {

                    @Override
                    public void onSuccess(ConfirmOrderInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 开通代理
     */
    public static void openAgent(LifecycleOwner activity, int type, int itemId, final HttpCallBack<ConfirmOrderInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.OPEN_AGENT_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("type", type);
        paramMap.put("id", itemId);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.openAgent(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<ConfirmOrderInfo>() {

                    @Override
                    public void onSuccess(ConfirmOrderInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 我的团队
     */
    public static void getMyTeamList(LifecycleOwner activity, String mobile, int page, int size, final HttpCallBack<MyTeamInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.MY_TEAM_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("mobile", mobile);
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getMyTeamList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<MyTeamInfo>() {

                    @Override
                    public void onSuccess(MyTeamInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 意见反馈
     */
    public static void submitFeedback(LifecycleOwner activity, String content, String contact, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.FEEDBACK_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("content", content);
        paramMap.put("contact", contact);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.submitFeedback(UserInfo.getUserToken(), paramJson)
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
     * 我的界面配置
     */
    public static void getMineItemConfig(LifecycleOwner activity, final HttpCallBack<MineItemConfig> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.MINE_ITEM_CONFIG_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getMineItemConfig(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<MineItemConfig>() {

                    @Override
                    public void onSuccess(MineItemConfig demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }


    /**
     * 获取信息列表
     */
    public static void getMessageList(LifecycleOwner activity, int type, int page, int size, final HttpCallBack<BaseResponse<MessageList>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.GET_MESSAGE_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("type", type);
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getMessageList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<MessageList>>() {

                    @Override
                    public void onSuccess(BaseResponse<MessageList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 获取消息分类
     */
    public static void getMessageCategory(LifecycleOwner activity, final HttpCallBack<List<MessageCategory>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.MESSAGE_CATEGORY_LIST);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getMessageCategory(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<List<MessageCategory>>() {

                    @Override
                    public void onSuccess(List<MessageCategory> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 设置全部已读
     */
    public static void setAllReadMessage(LifecycleOwner activity, final HttpCallBack<Object> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.SET_ALL_READ_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.setAllReadMessage(UserInfo.getUserToken(), paramJson)
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
     * 获取资源分类
     */
    public static void getResourcesCategory(LifecycleOwner activity, final HttpCallBack<List<ResourcesCategory>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.RESOURCES_CATEGORY_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getResourcesCategory(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<List<ResourcesCategory>>() {

                    @Override
                    public void onSuccess(List<ResourcesCategory> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 热搜关键字
     */
    public static void getHotKeywordList(LifecycleOwner activity, final HttpCallBack<List<HotKeywordList>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.HOT_SEARCH_KEYWORD_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getHotKeywordList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<List<HotKeywordList>>() {

                    @Override
                    public void onSuccess(List<HotKeywordList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 资源详情
     */
    public static void getResourcesDetails(LifecycleOwner activity, int itemId, final HttpCallBack<ResourcesDetailsInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.RESOURCES_DETAILS_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("id", itemId);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getResourcesDetails(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<ResourcesDetailsInfo>() {

                    @Override
                    public void onSuccess(ResourcesDetailsInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 获取资源列表
     */
    public static void getResourcesList(LifecycleOwner activity, int category_id, int page, int size, final HttpCallBack<BaseResponse<ResourcesList>> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.RESOURCES_LIST_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("category_id", category_id);
        paramMap.put("page", page);
        paramMap.put("size", size);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.getResourcesList(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<BaseResponse<ResourcesList>>() {

                    @Override
                    public void onSuccess(BaseResponse<ResourcesList> demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

    /**
     * 资源付费
     */

    public static void startResourcesPayment(LifecycleOwner activity, int id, int type, String password, final HttpCallBack<ConfirmOrderInfo> callBack) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("method", ApiRequest.RESOURCE_PAYMENT_URL);
        paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
        paramMap.put("id", id);
        paramMap.put("type", type);
        paramMap.put("pay_password", password);
        String paramJson = GsonUtils.toJson(paramMap);

        apiRequest.confirmOrder(UserInfo.getUserToken(), paramJson)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(activity)))
                .subscribe(new ApiObserver<ConfirmOrderInfo>() {

                    @Override
                    public void onSuccess(ConfirmOrderInfo demo) {
                        callBack.onSuccess(demo);
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        callBack.onFail(errorCode, errorMsg);
                    }
                });
    }

}
