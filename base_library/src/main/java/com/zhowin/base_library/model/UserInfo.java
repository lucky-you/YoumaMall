package com.zhowin.base_library.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SPUtils;

import java.util.List;

/**
 * author Z_B
 * date :2020/5/14 8:59
 * description: 用户信息 --> 全局通用
 */
public class UserInfo {


    /**
     * age : 19
     * avatar : 周小川
     * backgroundPictureKeys : ["http://qfah2px93.hn-bkt.clouddn.com//image.backgroundPicture.default.default.jpg"]
     * birthday : 2000-09-18
     * cars : [{"carId":"1","carName":"兰博基尼","number":0,"pictureKey":"image.backgroundPicture.default.default.jpg"}]
     * completed : true
     * constellation : 处女座
     * decorations : [{"id":"2","name":"兔耳朵","number":0,"pictureKey":"image.backgroundPicture.default.default.jpg"}]
     * fansNum : 0
     * followNum : 0
     * gender : 女
     * gifts : [{"giftId":"0","giftName":"高跟鞋","number":"0"}]
     * newFans : 0
     * newVisitor : 0
     * profilePictureKey : http://qfah2px93.hn-bkt.clouddn.com/image.profilePicture.default.1.jpg
     * status : 好好学习，天天向上
     * token : eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlZTc1OGU0YS0zZGJiLTQ3ZjUtYWE5NS1lNGQxMzJmMTFlZjUiLCJzdWIiOiIxMjcwOTU2NCIsImV4cCI6MTU5OTU2NDIwNSwidXNlcmlkIjoxMSwiY3JlYXRlZCI6MTU5OTUyODIwNTU2MywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IkNPTU1PTiJ9LHsiYXV0aG9yaXR5IjoiVU5ERUZJTkVEIn1dfQ.d5asAtF59hgrfQwLbn2WWkN6BxHu9gTQ34S_PK5rEaRWArbrArhEe8AnU1CvaZD1Ozzb5OiQYyzesdgoYwAl0w
     * undefinedPwd : 1
     * userId : 11
     * username : 12709564
     * visitNum : 0
     */

    private int age;
    private String avatar;
    private long birthday;
    private boolean completed;
    private String constellation;
    private int fansNum;
    private int followNum;
    private String gender;
    private int newFans;
    private int newVisitor;
    private String profilePictureKey;
    private String status;
    private String token;
    private String rongToken;
    private int undefinedPwd;
    private int userId;
    private String username;
    private String mobileNum;
    private int visitNum;

    public static void setUserInfo(UserInfo data) {
        Gson gson = new Gson();
        String userInfo = gson.toJson(data);
        SPUtils.set(ConstantValue.USER_INFO, userInfo);
        setUserToken(data.getToken());
        setIMToken(data.getRongToken());
    }

    public static UserInfo getUserInfo() {
        Gson gson = new Gson();
        UserInfo userInfoBean = gson.fromJson(SPUtils.getString(ConstantValue.USER_INFO), UserInfo.class);
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
     * 设置融云token
     */
    public static void setIMToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            SPUtils.set(ConstantValue.IM_TOKEN, token);
        } else {
            SPUtils.set(ConstantValue.IM_TOKEN, "");
        }
    }

    /**
     * 获取token
     */
    public static String getUserToken() {
        return (String) SPUtils.get(ConstantValue.SP_TOKEN, "");
    }

    /**
     * 获取融云token
     *
     * @return
     */
    public static String getIMToken() {
        return (String) SPUtils.get(ConstantValue.IM_TOKEN, "");
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getNewFans() {
        return newFans;
    }

    public void setNewFans(int newFans) {
        this.newFans = newFans;
    }

    public int getNewVisitor() {
        return newVisitor;
    }

    public void setNewVisitor(int newVisitor) {
        this.newVisitor = newVisitor;
    }

    public String getProfilePictureKey() {
        return profilePictureKey;
    }

    public void setProfilePictureKey(String profilePictureKey) {
        this.profilePictureKey = profilePictureKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }

    public int getUndefinedPwd() {
        return undefinedPwd;
    }

    public void setUndefinedPwd(int undefinedPwd) {
        this.undefinedPwd = undefinedPwd;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
