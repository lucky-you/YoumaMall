package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/24
 * desc ：我的团队
 */
public class MyTeamList {


    /**
     * user_id : 16
     * level : 4
     * upgrade_str :
     * avatar : /motorRider/uploads/image/20201225/1630401608892170
     * mobile : 17386259367
     * nickname : 猫猫
     * jointime : 1608884676
     * volume : 0
     */

    private int user_id;
    private int level;
    private String upgrade_str;
    private String avatar;
    private String mobile;
    private String nickname;
    private int jointime;
    private int volume;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUpgrade_str() {
        return upgrade_str;
    }

    public void setUpgrade_str(String upgrade_str) {
        this.upgrade_str = upgrade_str;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getJointime() {
        return jointime;
    }

    public void setJointime(int jointime) {
        this.jointime = jointime;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
