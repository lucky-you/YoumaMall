package com.zhowin.base_library.model;

import android.text.TextUtils;

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
     * nickname : 周小川
     * mobile : 13677197786
     * avatar : https://png.lxxxin.com/miYou/2020/12/09/112837/1607484517862
     * money : 1146.97
     * wechat_qrcode : https://png.lxxxin.com/miYou/2020/12/09/112837/1607484517764
     * token : ae84ae07-2259-40c0-be62-e7a79eae4ce5
     * user_id : 4
     * createtime : 1608779074
     * expiretime : 1611371074
     * expires_in : 2592000
     * level : 1
     * is_pay_pwd : 1
     * invitation_code : 353534
     * f_nickname : admin
     * withdrawal : 0.00
     * today_income : 2.00
     * income : 2.00
     * last_income_time : 1608775672
     * pid : 1
     * level_name : vip会员
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
    private int level;
    private int is_pay_pwd;
    private String invitation_code;
    private String f_nickname;
    private String withdrawal;
    private String today_income;
    private String income;
    private int last_income_time;
    private int pid;
    private String level_name;

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

    /**
     * 获取token
     */
    public static String getUserToken() {
        return (String) SPUtils.get(ConstantValue.SP_TOKEN, "");
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIs_pay_pwd() {
        return is_pay_pwd;
    }

    public void setIs_pay_pwd(int is_pay_pwd) {
        this.is_pay_pwd = is_pay_pwd;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public String getF_nickname() {
        return f_nickname;
    }

    public void setF_nickname(String f_nickname) {
        this.f_nickname = f_nickname;
    }

    public String getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(String withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getToday_income() {
        return today_income;
    }

    public void setToday_income(String today_income) {
        this.today_income = today_income;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public int getLast_income_time() {
        return last_income_time;
    }

    public void setLast_income_time(int last_income_time) {
        this.last_income_time = last_income_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }
}
