package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2020/12/24
 * desc ：信息列表
 */
public class MessageList {


    /**
     * id : 72
     * user_id : 4
     * title : 新用户注册信息
     * content : 恭喜，手机号【15677187758】已经注册为您的下级客户，可联系指导其使用。
     * status : 1
     * is_read : 0
     * createtime : 1608779139
     */

    private int id;
    private int user_id;
    private String title;
    private String content;
    private int status;
    private int is_read;
    private int createtime;

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

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }
}
