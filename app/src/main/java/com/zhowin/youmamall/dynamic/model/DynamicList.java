package com.zhowin.youmamall.dynamic.model;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/8
 * desc ：
 */
public class DynamicList {


    /**
     * id : 3
     * user_id : 3
     * title : 测试
     * content : 测试通过
     * images : ["https://png.lxxxin.com/1.jpg","https://png.lxxxin.com/2.jpg"]
     * status : 1
     * createtime : 1607006299
     * updatetime : 1607006299
     * nickname : 158****2331
     * avatar :
     * level : 2
     */

    private int id;
    private int user_id;
    private String title;
    private String content;
    private int status;
    private int createtime;
    private int updatetime;
    private String nickname;
    private String avatar;
    private int level;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
