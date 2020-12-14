package com.zhowin.youmamall.wxapi;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 存放用户信息的类
 */
public class WxUserInfo implements Parcelable {

    private String openId;
    private String unionid;
    private String userNickName;
    private String userPhotoUrl;
    private String userSex;

    public WxUserInfo() {
    }

    public WxUserInfo(String openId, String unionid, String userNickName, String userPhotoUrl, String userSex) {
        this.openId = openId;
        this.unionid = unionid;
        this.userNickName = userNickName;
        this.userPhotoUrl = userPhotoUrl;
        this.userSex = userSex;
    }


    protected WxUserInfo(Parcel in) {
        openId = in.readString();
        unionid = in.readString();
        userNickName = in.readString();
        userPhotoUrl = in.readString();
        userSex = in.readString();
    }


    public static final Creator<WxUserInfo> CREATOR = new Creator<WxUserInfo>() {
        @Override
        public WxUserInfo createFromParcel(Parcel in) {
            return new WxUserInfo(in);
        }

        @Override
        public WxUserInfo[] newArray(int size) {
            return new WxUserInfo[size];
        }
    };

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(openId);
        dest.writeString(unionid);
        dest.writeString(userNickName);
        dest.writeString(userPhotoUrl);
        dest.writeString(userSex);
    }

    @Override
    public String toString() {
        return "WxUserInfo{" +
                "openId='" + openId + '\'' +
                ", unionid='" + unionid + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", userSex='" + userSex + '\'' +
                '}';
    }
}
