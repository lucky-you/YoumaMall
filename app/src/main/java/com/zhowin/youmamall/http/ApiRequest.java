
package com.zhowin.youmamall.http;

import com.zhowin.base_library.http.ApiResponse;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.qiniu.QiNiuYunBean;
import com.zhowin.youmamall.BuildConfig;
import com.zhowin.youmamall.circle.model.CircleList;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.home.model.ConfirmOrderInfo;
import com.zhowin.youmamall.home.model.GoodDetailsInfo;
import com.zhowin.youmamall.home.model.HomeDynamicInfo;
import com.zhowin.youmamall.home.model.HomePageData;
import com.zhowin.youmamall.home.model.MessageCategory;
import com.zhowin.youmamall.home.model.MessageList;
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

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * description: 网络请求
 */
public interface ApiRequest {


    String TOKEN = "token";
    //注册
    String REGISTER = "api/user/register";

    //手机号+密码登录
    String MOBILE_AND_PASSWORD_URL = "api/user/login";

    //发送验证码
    String SEND_EMS_CODE = "api/sms/send";

    //获取用户信息
    String GET_USER_INFO_MESSAGE_URL = "api/user/userinfo";

    //退出登录
    String LOGIN_OUT_URL = "api/user/logout";

    //获取七牛云的token
    String GET_QI_NIU_TOKEN_URL = "api/set/qiniu_token";

    //设置支付密码
    String SET_PAY_PASSWORD_URL = "api/user/set_pay_password";

    //忘记秘密/重置密码
    String FOR_GET_PASSWORD_URL = "api/user/resetpwd";

    //修改个人信息
    String CHANGE_USER_INFO_MESSAGE_URL = "api/user/profile";

    //修改手机号码
    String CHANGE_USER_MOBILE_URL = "api/user/changemobile";

    //获取动态列表
    String GET_DYNAMIC_LIST_URL = "api/forum/dynamic_list";

    //获取圈子列表
    String GET_CIRCLE_LIST_URL = "api/forum/circle_list";

    //发布圈子
    String RELEASE_CIRCLE_URL = "api/forum/release";

    //商城左侧分类
    String GET_MALL_LEFT_LIST_URL = "api/category/list";

    //商品分类列表
    String MALL_GOOD_LIST_URL = "api/item/list";

    //vip商品列表
    String GET_VIP_GOOD_LIST_URL = "api/item/vip_list";

    //复购商品列表
    String REPURCHASE_GOOD_LIST_URL = "api/item/repeat_list";

    //商品详情
    String GET_GOOD_DETAILS_URL = "api/item/detail";

    //联系客服
    String CONTACT_SERVICE_LIST_URL = "api/set/customer_service";

    //首页商品的数据
    String GET_HOME_PAGE_DATA_LIST_URL = "api/item/home_list";

    //首页banner/vip数据
    String GET_HOME_BANNER_AND_VIP_DATA_URL = "api/banner/home_list";

    //提交订单
    String CONFIRM_ORDER_URL = "api/trade_order/confirm";

    //下架商品
    String GOOD_OFF_SHELF_URL = "api/merchant/shelf";

    //上架商品
    String PUT_ON_THE_SHELF_URL = "api/merchant/grounding";

    //注销店铺
    String REMOVE_MERCHANT_LIST_URL = "api/merchant/remove";

    // 录入卡密
    String ENTER_CARD_SECRET_URL = "api/merchant/enter";

    //获取保证金信息
    String GET_DEPOSIT_MESSAGE_URL = "api/merchant/is_open_merchant";

    //立即支付
    String START_PAYMENT_DEPOSIT_URL = "api/merchant/apply";

    //账号流水
    String ACCOUNT_TURNOVER_LIST_URL = "api/money_log/list";

    //提现记录
    String WITHDRAWALS_RECORD_LIST_URL = "api/withdraw/list";

    //发布、修改商品
    String RELEASE_GOOD_URL = "api/merchant/release";

    //获取用户商品列表
    String GET_USER_GOOD_LIST_URL = "api/merchant/list";

    //订单列表
    String GET_MALL_ORDER_LIST_URL = "api/trade_order/list";

    //确认收货
    String CONFIRM_RECEIPT_GOOD_URL = "api/trade_order/delivery";

    //已售商品列表
    String GET_SOLD_GOOD_LIST_URL = "api/merchant/order_list";

    //出售流水
    String GET_SALES_TURNOVER_LIST_URL = "api/merchant/sell_record";

    //分享素材
    String GET_SHARE_MATER_LIST_URL = "api/set/share_list";

    //申请提现
    String APPLY_WITHDRAW_LIST_URL = "api/withdraw/apply";

    //未读消息
    String GET_UNREAD_MESSAGE_URL = "api/banner/dynamic";


    //开通Vip规则
    String OPEN_VIP_RULE_URL = "api/proxy/vip_rule";

    //开通代理规则
    String OPEN_AGENT_RULE_URL = "api/proxy/upgrade_rule";

    //开通VIP
    String OPEN_VIP_URL = "api/proxy/open_vip";

    //开通 代理
    String OPEN_AGENT_URL = "api/proxy/open_proxy";

    //我的团队
    String MY_TEAM_LIST_URL = "api/proxy/my_team";

    //意见反馈
    String FEEDBACK_URL = "api/feedback/submit";

    //我的配置
    String MINE_ITEM_CONFIG_URL = "api/set/config_set";


    //站内信信息列表
    String GET_MESSAGE_LIST_URL = "api/message/list";

    //站内信分类
    String MESSAGE_CATEGORY_LIST = "api/message/category";

    //设置全部已读
    String SET_ALL_READ_URL = "api/message/read";


    /**
     * 返回的是自己的用户信息
     */
    @POST(GET_USER_INFO_MESSAGE_URL)
    Observable<ApiResponse<UserInfo>> getUserInfoMessage(@Header(TOKEN) String token);

    /**
     * 七牛云的信息
     */
    @POST(GET_QI_NIU_TOKEN_URL)
    Observable<ApiResponse<QiNiuYunBean>> getQiNiuYunBean(@Header(TOKEN) String token);

    /**
     * 手机号码+验证码注册
     */
    @FormUrlEncoded
    @POST(REGISTER)
    Observable<ApiResponse<UserInfo>> registerFromPhoneNumber(@FieldMap HashMap<String, Object> map);

    /**
     * 手机号 + 密码 登录
     */
    @FormUrlEncoded
    @POST(MOBILE_AND_PASSWORD_URL)
    Observable<ApiResponse<UserInfo>> loginMobileAndPassword(@Field("account") String phone, @Field("password") String password);

    /**
     * 获取短信验证码
     */
    @FormUrlEncoded
    @POST(SEND_EMS_CODE)
    Observable<ApiResponse<Object>> getVerificationCode(@Field("event") String event, @Field("mobile") String mobile);

    /**
     * 退出登录
     */
    @POST
    Observable<ApiResponse<Object>> outLoginAppOrRemoveMerchant(@Header(TOKEN) String token, @Url String url);

    /**
     * 修改手机号码
     */
    @FormUrlEncoded
    @POST(CHANGE_USER_MOBILE_URL)
    Observable<ApiResponse<Object>> changUserMobile(@Header(TOKEN) String token, @Field("password") String password, @Field("mobile") String mobile, @Field("captcha") String captcha);

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST(CHANGE_USER_INFO_MESSAGE_URL)
    Observable<ApiResponse<Object>> changeUserMessageInfo(@Header(TOKEN) String token, @Field("avatar") String avatar, @Field("nickname") String nickname, @Field("wechat_qrcode") String qrCode);


    /**
     * 获取动态
     */
    @FormUrlEncoded
    @POST(GET_DYNAMIC_LIST_URL)
    Observable<ApiResponse<BaseResponse<DynamicList>>> getDynamicList(@Header(TOKEN) String token, @Field("page") int pageNum, @Field("size") int pageSize);

    /**
     * 圈子列表
     */
    @FormUrlEncoded
    @POST(GET_CIRCLE_LIST_URL)
    Observable<ApiResponse<BaseResponse<CircleList>>> getCircleList(@Header(TOKEN) String token, @Field("page") int pageNum, @Field("size") int pageSize);

    /**
     * 发布圈子
     */
    @FormUrlEncoded
    @POST(RELEASE_CIRCLE_URL)
    Observable<ApiResponse<Object>> releaseCircleData(@Header(TOKEN) String token, @Field("title") String name, @Field("content") String content, @Field("images") String images);


    /**
     * 商城左侧分类
     */
    @POST(GET_MALL_LEFT_LIST_URL)
    Observable<ApiResponse<List<MallLeftList>>> getMallLeftList(@Header(TOKEN) String token);

    /**
     * 商城右侧列表
     */
    @FormUrlEncoded
    @POST
    Observable<ApiResponse<BaseResponse<MallRightList>>> getMallRightList(@Header(TOKEN) String token, @Url String url, @FieldMap HashMap<String, Object> map);

    /**
     * 商品详情
     */
    @FormUrlEncoded
    @POST(GET_GOOD_DETAILS_URL)
    Observable<ApiResponse<GoodDetailsInfo>> getGoodDetails(@Header(TOKEN) String token, @Field("item_id") int item_id);

    /**
     * 联系客服
     */
    @POST(CONTACT_SERVICE_LIST_URL)
    Observable<ApiResponse<List<ContactServiceList>>> getContactServiceList(@Header(TOKEN) String token);

    /**
     * 设置支付密码
     */
    @FormUrlEncoded
    @POST(SET_PAY_PASSWORD_URL)
    Observable<ApiResponse<Object>> setPayPassword(@Header(TOKEN) String token, @Field("password") String password, @Field("pay_password") String pay_password, @Field("pay_password_again") String pay_password_again);


    /**
     * 重置密码
     */
    @FormUrlEncoded
    @POST(FOR_GET_PASSWORD_URL)
    Observable<ApiResponse<Object>> setResetPassword(@Header(TOKEN) String token, @Field("mobile") String mobile, @Field("newpassword") String newpassword, @Field("captcha") String captcha);

    /**
     * 首页商品数据
     */

    @POST(GET_HOME_PAGE_DATA_LIST_URL)
    Observable<ApiResponse<HomePageData>> getHomePageDataInfo(@Header(TOKEN) String token);


    /**
     * 首页banner、动态，福利数据
     */
    @POST(GET_HOME_BANNER_AND_VIP_DATA_URL)
    Observable<ApiResponse<HomeDynamicInfo>> getHomeDynamicDataInfo(@Header(TOKEN) String token);

    /**
     * 提交订单
     */
    @FormUrlEncoded
    @POST(CONFIRM_ORDER_URL)
    Observable<ApiResponse<ConfirmOrderInfo>> confirmOrder(@Header(TOKEN) String token, @Field("id") int id, @Field("type") int type, @Field("pay_password") String pay_password, @Field("pay_no") String pay_no);


    /**
     * 商品的下架
     */
    @FormUrlEncoded
    @POST
    Observable<ApiResponse<Object>> goodOffShelf(@Header(TOKEN) String token, @Url String url, @Field("id") int id);

    /**
     * 录入卡密
     */
    @FormUrlEncoded
    @POST(ENTER_CARD_SECRET_URL)
    Observable<ApiResponse<Object>> onEnterCardSecret(@Header(TOKEN) String token, @Field("id") int id, @Field("content") String content);


    /**
     * 获取保证金
     */
    @POST(GET_DEPOSIT_MESSAGE_URL)
    Observable<ApiResponse<DepositMessage>> getDepositMessage(@Header(TOKEN) String token);


    /**
     * 立即入住
     */
    @FormUrlEncoded
    @POST(START_PAYMENT_DEPOSIT_URL)
    Observable<ApiResponse<ConfirmOrderInfo>> startDepositPayment(@Header(TOKEN) String token, @Field("type") int type, @Field("pay_password") String pay_password);


    /**
     * 账号流水
     */
    @FormUrlEncoded
    @POST
    Observable<ApiResponse<BaseResponse<AccountTurnoverList>>> getAccountTurnoverList(@Header(TOKEN) String token, @Url String url, @Field("page") int pageNum, @Field("size") int pageSize);


    /**
     * 发布、修改商品
     */
    @FormUrlEncoded
    @POST(RELEASE_GOOD_URL)
    Observable<ApiResponse<Object>> releaseOrChangeGood(@Header(TOKEN) String token, @FieldMap HashMap<String, Object> map);

    /**
     * 用户商品列表
     */
    @FormUrlEncoded
    @POST(GET_USER_GOOD_LIST_URL)
    Observable<ApiResponse<BaseResponse<MallRightList>>> getGoodList(@Header(TOKEN) String token, @Field("page") int pageNum, @Field("size") int pageSize);

    /**
     * 订单列表
     */
    @FormUrlEncoded
    @POST(GET_MALL_ORDER_LIST_URL)
    Observable<ApiResponse<BaseResponse<MallOrderList>>> getMallOrderList(@Header(TOKEN) String token, @Field("status") int status, @Field("page") int pageNum, @Field("size") int pageSize);


    /**
     * 确认收货
     */
    @FormUrlEncoded
    @POST(CONFIRM_RECEIPT_GOOD_URL)
    Observable<ApiResponse<Object>> goodConfirmReceipt(@Header(TOKEN) String token, @Field("id") int goodId);


    /**
     * 已售商品列表
     */
    @FormUrlEncoded
    @POST(GET_SOLD_GOOD_LIST_URL)
    Observable<ApiResponse<BaseResponse<SoldGoodList>>> getSoldGoodList(@Header(TOKEN) String token, @Field("page") int pageNum, @Field("size") int pageSize);

    /**
     * 出售流水
     */
    @FormUrlEncoded
    @POST(GET_SALES_TURNOVER_LIST_URL)
    Observable<ApiResponse<BaseResponse<SalesTurnoverList>>> getSalesTurnoverList(@Header(TOKEN) String token, @Field("page") int pageNum, @Field("size") int pageSize);


    /**
     * 获取分享素材
     */
    @POST(GET_SHARE_MATER_LIST_URL)
    Observable<ApiResponse<List<ShareMaterialList>>> getShareMaterial(@Header(TOKEN) String token);


    /**
     * 申请提现
     */
    @FormUrlEncoded
    @POST(APPLY_WITHDRAW_LIST_URL)
    Observable<ApiResponse<Object>> applyWithdraw(@Header(TOKEN) String token, @FieldMap HashMap<String, Object> map);

    /**
     * 未读消息与动态
     */
    @POST(GET_UNREAD_MESSAGE_URL)
    Observable<ApiResponse<UnreadMessageInfo>> getUnreadMessageInfo(@Header(TOKEN) String token);

    /**
     * 开通vip / 代理 的规则
     */
    @POST
    Observable<ApiResponse<List<AgentList>>> getVipOrAgentRule(@Header(TOKEN) String token, @Url String url);

    /**
     * 开通vip
     */
    @FormUrlEncoded
    @POST(OPEN_VIP_URL)
    Observable<ApiResponse<ConfirmOrderInfo>> openVIP(@Header(TOKEN) String token, @Field("type") int type);

    /**
     * 开通代理
     */
    @FormUrlEncoded
    @POST(OPEN_AGENT_URL)
    Observable<ApiResponse<ConfirmOrderInfo>> openAgent(@Header(TOKEN) String token, @Field("type") int type, @Field("id") int id);


    /**
     * 我的团队
     */
    @FormUrlEncoded
    @POST(MY_TEAM_LIST_URL)
    Observable<ApiResponse<MyTeamInfo>> getMyTeamList(@Header(TOKEN) String token, @Field("page") int pageNum, @Field("size") int pageSize);


    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST(FEEDBACK_URL)
    Observable<ApiResponse<Object>> submitFeedback(@Header(TOKEN) String token, @Field("content") String content, @Field("contact") String contact);

    /**
     * 我的界面配置
     */
    @POST(MINE_ITEM_CONFIG_URL)
    Observable<ApiResponse<MineItemConfig>> getMineItemConfig(@Header(TOKEN) String token);


    /**
     * 获取信息列表
     */
    @FormUrlEncoded
    @POST(GET_MESSAGE_LIST_URL)
    Observable<ApiResponse<BaseResponse<MessageList>>> getMessageList(@Header(TOKEN) String token, @Field("type") int type, @Field("page") int pageNum, @Field("size") int pageSize);


    /**
     * 消息分类
     */
    @POST(MESSAGE_CATEGORY_LIST)
    Observable<ApiResponse<List<MessageCategory>>> getMessageCategory(@Header(TOKEN) String token);

    /**
     * 下载图片
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String imgUrl);

    /**
     * 设置全部已读
     */
    @POST(SET_ALL_READ_URL)
    Observable<ApiResponse<Object>> setAllReadMessage(@Header(TOKEN) String token);

}
