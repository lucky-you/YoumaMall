package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2020/12/23
 * desc ：最新动态
 */
public class LatestNewInfo {


    /**
     * id : 1
     * title : 测试公告
     * createtime : 1607949705
     * content : 实打实的方式
     */

    private int id;
    private String title;
    private int createtime;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
