package com.zhowin.base_library.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GsonUtils;
import com.zhowin.base_library.utils.SPUtils;

/**
 * author Z_B
 * date :2020/5/14 8:59
 * description: 用户信息 --> 全局通用
 */
public class UserInfo {


    /**
     * id : 4
     * nickname : 136****7786
     * mobile : 13677197786
     * avatar : data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgaGVpZ2h0PSIxMDAiIHdpZHRoPSIxMDAiPjxyZWN0IGZpbGw9InJnYigyMjksMTc0LDE2MCkiIHg9IjAiIHk9IjAiIHdpZHRoPSIxMDAiIGhlaWdodD0iMTAwIj48L3JlY3Q+PHRleHQgeD0iNTAiIHk9IjUwIiBmb250LXNpemU9IjUwIiB0ZXh0LWNvcHk9ImZhc3QiIGZpbGw9IiNmZmZmZmYiIHRleHQtYW5jaG9yPSJtaWRkbGUiIHRleHQtcmlnaHRzPSJhZG1pbiIgYWxpZ25tZW50LWJhc2VsaW5lPSJjZW50cmFsIj4xPC90ZXh0Pjwvc3ZnPg==
     * money : 0.00
     * wechat_qrcode :
     * token : 060e941c-fd86-4285-8630-e16ddce06c0d
     * user_id : 4
     * createtime : 1607334395
     * expiretime : 1609926395
     * expires_in : 2592000
     * is_pay_pwd : 0
     * level : 0
     * invitation_code :
     */

    private int id;
    private String nickname;
    private String mobile;
    private String avatar;
    private String money;
    private String wechat_qrcode;
    private String token;
    private int user_id;
    private int createtime;
    private int expiretime;
    private int expires_in;
    private int is_pay_pwd;
    private int level;
    private String invitation_code;

    public static void setUserInfo(UserInfo data) {
        String userInfo = GsonUtils.toJson(data);
        SPUtils.set(ConstantValue.USER_INFO, userInfo);
        setUserToken(data.getToken());
    }

    public static UserInfo getUserInfo() {
        UserInfo userInfoBean = GsonUtils.fromJson(SPUtils.getString(ConstantValue.USER_INFO), UserInfo.class);
        if (userInfoBean != null) {
            return userInfoBean;
        } else {
            return new UserInfo();
        }
    }

    /**
     * 设置用户token
     */
    public static void setUserToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            SPUtils.set(ConstantValue.SP_TOKEN, token);
        } else {
            SPUtils.set(ConstantValue.SP_TOKEN, "");
        }
    }

    public static void setUserPassword(String password) {
        if (!TextUtils.isEmpty(password)) {
            SPUtils.set(ConstantValue.PASSWORD, password);
        } else {
            SPUtils.set(ConstantValue.PASSWORD, "");
        }
    }

    public static String getUserPassword() {
        return (String) SPUtils.get(ConstantValue.PASSWORD, "");
    }

    /**
     * 获取token
     */
    public static String getUserToken() {
        return (String) SPUtils.get(ConstantValue.SP_TOKEN, "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getWechat_qrcode() {
        return wechat_qrcode;
    }

    public void setWechat_qrcode(String wechat_qrcode) {
        this.wechat_qrcode = wechat_qrcode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public int getIs_pay_pwd() {
        return is_pay_pwd;
    }

    public void setIs_pay_pwd(int is_pay_pwd) {
        this.is_pay_pwd = is_pay_pwd;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }
}
