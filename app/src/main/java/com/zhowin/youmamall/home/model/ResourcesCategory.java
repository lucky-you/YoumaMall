package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2021/1/7
 * desc ：资源分类
 */
public class ResourcesCategory {


    /**
     * id : 4
     * name : 资源分类2
     * status : 1
     * weigh : 4
     * createtime : 1609916492
     * updatetime : 1609916492
     */

    private int id;
    private String name;
    private int status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
