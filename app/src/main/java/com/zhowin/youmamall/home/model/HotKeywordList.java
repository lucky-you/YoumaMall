package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2021/1/7
 * desc ：热搜关键字
 */
public class HotKeywordList {


    /**
     * id : 26
     * name : 安卓
     * weigh : 26
     * createtime : 1608793120
     * updatetime : 1608793120
     */

    private int id;
    private String name;
    private int weigh;
    private int createtime;
    private int updatetime;

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

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
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
}
