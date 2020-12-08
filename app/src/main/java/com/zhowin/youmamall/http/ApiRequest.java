
package com.zhowin.youmamall.http;

import com.zhowin.base_library.http.ApiResponse;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.qiniu.QiNiuYunBean;
import com.zhowin.youmamall.circle.model.CircleList;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.mall.model.MallLeftList;

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

    //退出登录
    String USER_OUT_LOGIN_URL = "api/user/logout";


    //获取用户信息
    String GET_USER_INFO_MESSAGE_URL = "/ant/userInfo/my";


    //退出登录
    String LOGIN_OUT_URL = "api/user/logout";

    //获取七牛云的token
    String GET_QI_NIU_TOKEN_URL = "api/qiniu/token";


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


    /**
     * 返回的是自己的用户信息
     */
    @POST(GET_USER_INFO_MESSAGE_URL)
    Observable<ApiResponse<UserInfo>> getUserInfoMessage(@Field(TOKEN) String token);

    /**
     * 七牛云的信息
     */
    @POST(GET_QI_NIU_TOKEN_URL)
    Observable<ApiResponse<QiNiuYunBean>> getQiNiuYunBean(@Field(TOKEN) String token);

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
    Observable<ApiResponse<Object>> changUserMobile(@Field("password") String password, @Field("mobile") String mobile, @Field("captcha") String captcha);

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST(CHANGE_USER_INFO_MESSAGE_URL)
    Observable<ApiResponse<UserInfo>> changeUserMessageInfo(@Header(TOKEN) String token, @FieldMap HashMap<String, Object> map);


    /**
     * 获取动态
     */
    @FormUrlEncoded
    @POST(GET_DYNAMIC_LIST_URL)
    Observable<ApiResponse<BaseResponse<DynamicList>>> getDynamicList(@Header(TOKEN) String token, @Field("pageNum") int pageNum, @Field("pageSize") int pageSize);

    /**
     * 圈子列表
     */
    @FormUrlEncoded
    @POST(GET_CIRCLE_LIST_URL)
    Observable<ApiResponse<BaseResponse<CircleList>>> getCircleList(@Header(TOKEN) String token, @Field("pageNum") int pageNum, @Field("pageSize") int pageSize);

    /**
     * 发布圈子
     */
    @FormUrlEncoded
    @POST(RELEASE_CIRCLE_URL)
    Observable<ApiResponse<BaseResponse<Object>>> releaseCircleData(@Header(TOKEN) String token, @Field("name") String name, @Field("content") String content, @Field("images") String images);


    /**
     * 商城左侧分类
     */
    @POST(GET_MALL_LEFT_LIST_URL)
    Observable<ApiResponse<List<MallLeftList>>> getMallLeftList(@Header(TOKEN) String token);

}
