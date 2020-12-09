
package com.zhowin.youmamall.http;

import com.zhowin.base_library.http.ApiResponse;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.qiniu.QiNiuYunBean;
import com.zhowin.youmamall.circle.model.CircleList;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.home.model.GoodDetailsInfo;
import com.zhowin.youmamall.home.model.HomePageData;
import com.zhowin.youmamall.mall.model.MallLeftList;
import com.zhowin.youmamall.mall.model.MallRightList;
import com.zhowin.youmamall.mine.model.ContactServiceList;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
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

    //商城商品列表
    String MALL_GOOD_LIST_URL = "api/item/list";

    //商品详情
    String GET_GOOD_DETAILS_URL = "api/item/detail";

    //联系客服
    String CONTACT_SERVICE_LIST_URL = "api/set/customer_service";

    //首页商品的数据
    String GET_HOME_PAGE_DATA_LIST_URL = "api/item/home_list";

    //首页banner/vip数据
    String GET_HOME_BANNER_AND_VIP_DATA_URL = "";


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
    @POST(LOGIN_OUT_URL)
    Observable<ApiResponse<Object>> outLoginApp(@Header(TOKEN) String token);

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
    @POST(MALL_GOOD_LIST_URL)
    Observable<ApiResponse<BaseResponse<MallRightList>>> getMallRightList(@Header(TOKEN) String token, @Field("shop_category_id") int shop_category_id, @Field("page") int pageNum, @Field("size") int pageSize);

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
    @POST(SET_PAY_PASSWORD_URL)
    Observable<ApiResponse<Object>> setPayPassword(@Header(TOKEN) String token, @Field("password") String password, @Field("pay_password") String pay_password, @Field("pay_password_again") String pay_password_again);


    /**
     * 重置密码
     */
    @POST(FOR_GET_PASSWORD_URL)
    Observable<ApiResponse<Object>> setResetPassword(@Header(TOKEN) String token, @Field("mobile") String mobile, @Field("newpassword") String newpassword, @Field("captcha") String captcha);

    /**
     * 首页商品数据
     */

    @POST(GET_HOME_PAGE_DATA_LIST_URL)
    Observable<ApiResponse<HomePageData>> getHomePageDataInfo(@Header(TOKEN) String token);
}
