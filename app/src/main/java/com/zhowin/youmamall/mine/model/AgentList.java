package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/17
 * desc ：代理列表
 */
public class AgentList {


    private String title;
    private String content;
    private String price;

    public AgentList(String title, String content, String price) {
        this.title = title;
        this.content = content;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
