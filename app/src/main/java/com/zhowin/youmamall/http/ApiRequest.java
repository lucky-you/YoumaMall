
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


    //手机号+密码登录
    String MOBILE_AND_PASSWORD_URL = "api/user/login";


    //获取用户信息
    String GET_USER_INFO_MESSAGE_URL = "/ant/userInfo/my";

    //获取别人的信息
    String GET_OTHER_USER_INFO_MESSAGE_URL = "/ant/userInfo/getUserInfo";

    //获取七牛云的token
    String GET_QI_NIU_TOKEN_URL = "/qn/getUploadToken";

    //发送验证码
    String SEND_EMS_CODE = "api/sms/send";

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
    Observable<ApiResponse<UserInfo>> loginMobileAndPassword(@Field("account") String phone, @Field("password") String password);


    /**
     * 获取短信验证码
     */
    @FormUrlEncoded
    @POST(SEND_EMS_CODE)
    Observable<ApiResponse<Object>> getVerificationCode(@Field("event") String event, @Field("mobile") String mobile);


}
