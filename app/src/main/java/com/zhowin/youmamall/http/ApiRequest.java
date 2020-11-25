
package com.zhowin.youmamall.http;

import com.zhowin.base_library.http.ApiResponse;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.qiniu.QiNiuYunBean;

import java.util.HashMap;

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
    String AUTHOR = "Authorization";

    String TOKEN_VALUE = "Bearer ";

    //注册
    String REGISTER = "api/user/register";

    //验证码登录
    String CAPATH_CODE_LOGIN = "/mobile/sms/login";

    //手机号+密码登录
    String MOBILE_AND_PASSWORD_URL = "/mobile/pwd/login";

    //账号 + 密码登录
    String NAME_AND_PASSWORD_LOGIN_URL = "/login";

    //获取用户信息
    String GET_USER_INFO_MESSAGE_URL = "/ant/userInfo/my";

    //获取别人的信息
    String GET_OTHER_USER_INFO_MESSAGE_URL = "/ant/userInfo/getUserInfo";

    //获取七牛云的token
    String GET_QI_NIU_TOKEN_URL = "/qn/getUploadToken";

    //发送验证码
    String SEND_EMS_CODE = "/auth/sendCode";

    //获取默认图像
    String GET_DEFAULT_AVATAR_URL = "/ant/profilePicture/getOptionalProfilePicture";

    //获取默认的标签
    String GET_DEFAULT_LABEL_LIST_URL = "/ant/user/label/getChoiceLabels";

    //提交用户认证信息
    String SUBMIT_USER_INFO_MESSAGE_URL = "/ant/userInfo/initializeUserInfo";

    //更新用户资料
    String UPDATE_USER_INFO_MESSAGE_URL = "/ant/userInfo/updateUserInfo";

    //获取首页标签
    String GET_HOME_BANNER_LIST_URL = "/ant/home/banner/getBanners";

    //获取首页公告
    String GET_HOME_NOTICE_MESSAGE_URL = "/ant/message/scroll/get";

    //获取礼物列表
    String GET_GIFT_LIST_URL = "/ant/live/getGifts";

    //设置用户密码
    String SET_USER_PASSWORD_URL = "/user/initializePwd";

    //修改用户密码
    String CHANGE_USER_PASSWORD_URL = "/user/modifyPwd";

    //忘记秘密
    String FOR_GET_PASSWORD_URL = "/auth/findPassword";



    /**
     * 返回的是自己的用户信息
     */
    @POST(GET_USER_INFO_MESSAGE_URL)
    Observable<ApiResponse<UserInfo>> getUserInfoMessage(@Header(AUTHOR) String token);

    /**
     * 七牛云的信息
     */
    @POST(GET_QI_NIU_TOKEN_URL)
    Observable<ApiResponse<QiNiuYunBean>> getQiNiuYunBean(@Header(AUTHOR) String token);

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
    Observable<ApiResponse<UserInfo>> loginMobileAndPassword(@Field("phone") String phone, @Field("password") String password);

    /**
     * 账号 + 密码 登录
     */
    @FormUrlEncoded
    @POST(NAME_AND_PASSWORD_LOGIN_URL)
    Observable<ApiResponse<UserInfo>> nameAndPasswordLogin(@Field("username") String username, @Field("password") String password);

    /**
     * 手机号 + 短信验证码登录
     */
    @FormUrlEncoded
    @POST(CAPATH_CODE_LOGIN)
    Observable<ApiResponse<UserInfo>> mobileVerificationCodeLogin(@Field("phone") String mobile, @Field("smsCode") String smsCode);


    /**
     * 获取短信验证码
     */
    @FormUrlEncoded
    @POST
    Observable<ApiResponse<Object>> getVerificationCode(@Url String url, @Field("event") int event, @Field("mobileNum") String mobile);


}
