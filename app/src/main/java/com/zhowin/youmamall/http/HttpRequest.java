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
     * 退出登录
     */
    public static void outLoginApp(LifecycleOwner activity, boolean isLogout, final HttpCallBack<Object> callBack) {
        String url = isLogout ? ApiRequest.LOGIN_OUT_URL : ApiRequest.REMOVE_MERCHANT_LIST_URL;
        apiRequest.outLoginAppOrRemoveMerchant(UserInfo.getUserToken(), url)
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
        apiRequest.changeUserMessageInfo(UserInfo.getUserToken(), avatar, nickname, qrCode)
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
        apiRequest.changUserMobile(UserInfo.getUserToken(), password, mobile, captcha)
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
        apiRequest.releaseCircleData(UserInfo.getUserToken(), name, content, images)
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
        apiRequest.getMallLeftList(UserInfo.getUserToken())
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
        apiRequest.getMallRightList(UserInfo.getUserToken(), url, map)
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
        apiRequest.getGoodDetails(UserInfo.getUserToken(), itemId)
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
        apiRequest.getContactServiceList(UserInfo.getUserToken())
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
        apiRequest.setPayPassword(UserInfo.getUserToken(), password, pay_password, pay_password_again)
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
        apiRequest.setResetPassword(UserInfo.getUserToken(), mobile, newpassword, captcha)
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
        apiRequest.getHomePageDataInfo(UserInfo.getUserToken())
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
        apiRequest.getHomeDynamicDataInfo(UserInfo.getUserToken())
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
        apiRequest.confirmOrder(UserInfo.getUserToken(), id, type, password, payOrder)
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
        apiRequest.goodOffShelf(UserInfo.getUserToken(), url, id)
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
        apiRequest.onEnterCardSecret(UserInfo.getUserToken(), id, content)
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
        apiRequest.getDepositMessage(UserInfo.getUserToken())
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
        apiRequest.startDepositPayment(UserInfo.getUserToken(), type, password)
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
        apiRequest.getAccountTurnoverList(UserInfo.getUserToken(), url, page, size)
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
        apiRequest.releaseOrChangeGood(UserInfo.getUserToken(), map)
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
        apiRequest.getGoodList(UserInfo.getUserToken(), page, size)
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
        apiRequest.getSoldGoodList(UserInfo.getUserToken(), page, size)
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
        apiRequest.getSalesTurnoverList(UserInfo.getUserToken(), page, size)
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
        apiRequest.getMallOrderList(UserInfo.getUserToken(), status, page, size)
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
        apiRequest.goodConfirmReceipt(UserInfo.getUserToken(), goodId)
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
        apiRequest.getShareMaterial(UserInfo.getUserToken())
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
        apiRequest.applyWithdraw(UserInfo.getUserToken(), map)
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
        apiRequest.getUnreadMessageInfo(UserInfo.getUserToken())
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
        apiRequest.getVipOrAgentRule(UserInfo.getUserToken(), url)
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
        apiRequest.openVIP(UserInfo.getUserToken(), type)
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
        apiRequest.openAgent(UserInfo.getUserToken(), type, itemId)
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
    public static void getMyTeamList(LifecycleOwner activity, int page, int size, final HttpCallBack<MyTeamInfo> callBack) {
        apiRequest.getMyTeamList(UserInfo.getUserToken(), page, size)
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
        apiRequest.submitFeedback(UserInfo.getUserToken(), content, contact)
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
        apiRequest.getMineItemConfig(UserInfo.getUserToken())
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
        apiRequest.getMessageList(UserInfo.getUserToken(), type, page, size)
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
        apiRequest.getMessageCategory(UserInfo.getUserToken())
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
        apiRequest.setAllReadMessage(UserInfo.getUserToken())
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
        apiRequest.getResourcesCategory(UserInfo.getUserToken())
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
        apiRequest.getHotKeywordList(UserInfo.getUserToken())
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
        apiRequest.getResourcesDetails(UserInfo.getUserToken(), itemId)
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
        apiRequest.getResourcesList(UserInfo.getUserToken(), category_id, page, size)
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

}
