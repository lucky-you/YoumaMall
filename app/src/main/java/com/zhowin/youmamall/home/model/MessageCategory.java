package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2020/12/30
 * desc ：消息分类
 */
public class MessageCategory {


    /**
     * type : 2
     * name : 新用户注册信息
     * read_num : 0
     * content :
     */

    private int type;
    private String name;
    private int read_num;
    private String content;
    private int createtime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }
}
