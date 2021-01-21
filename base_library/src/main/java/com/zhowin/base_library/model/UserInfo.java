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
     * money : 1413.11
     * wechat_qrcode : https://png.lxxxin.com/KuKa/2020/12/29/104957/1609210197606
     * token : 5d746d7f-ee77-4714-8487-18fbf98391ad
     * user_id : 4
     * createtime : 1610436440
     * expiretime : 1613028440
     * expires_in : 1809113
     * level : 3
     * is_pay_pwd : 1
     * invitation_code : 353534
     * f_nickname : 158****2331
     * f_mobile : 15827062331
     * f_avatar : https://png.lxxxin.com/miYou/2020/12/09/112837/1607484517862
     * f_wechat_qrcode : https://png.lxxxin.com/1223.jpg
     * withdrawal : 0.00
     * today_income : 0.00
     * income : 805.95
     * last_income_time : 1611218183
     * pid : 3
     * level_name : 市级代理商
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
    private String f_mobile;
    private String f_avatar;
    private String f_wechat_qrcode;
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

    public String getF_mobile() {
        return f_mobile;
    }

    public void setF_mobile(String f_mobile) {
        this.f_mobile = f_mobile;
    }

    public String getF_avatar() {
        return f_avatar;
    }

    public void setF_avatar(String f_avatar) {
        this.f_avatar = f_avatar;
    }

    public String getF_wechat_qrcode() {
        return f_wechat_qrcode;
    }

    public void setF_wechat_qrcode(String f_wechat_qrcode) {
        this.f_wechat_qrcode = f_wechat_qrcode;
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
