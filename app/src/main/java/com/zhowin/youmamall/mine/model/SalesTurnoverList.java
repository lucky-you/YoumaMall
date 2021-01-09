package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/19
 * desc ：出售流水
 */
public class SalesTurnoverList {


    /**
     * id : 931
     * user_id : 4
     * money : 3.00
     * before : 1133.11
     * after : 1136.11
     * memo : 商品售出
     * createtime : 1610157739
     * sort : 6
     * sort_id : 34
     */

    private int id;
    private int user_id;
    private String money;
    private String before;
    private String after;
    private String memo;
    private int createtime;
    private int sort;
    private int sort_id;

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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }
}
