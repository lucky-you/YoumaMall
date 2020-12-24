package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/17
 * desc ： VIP /  代理列表
 */
public class AgentList {


    /**
     * id : 6
     * name : 市级代理商
     * open_price : 488.00
     * open_detail : 永久市级代理
     * level : 3
     */

    private int id;
    private String name;
    private String open_price;
    private String open_detail;
    private int level;
    private boolean  isSelect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen_price() {
        return open_price;
    }

    public void setOpen_price(String open_price) {
        this.open_price = open_price;
    }

    public String getOpen_detail() {
        return open_detail;
    }

    public void setOpen_detail(String open_detail) {
        this.open_detail = open_detail;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
