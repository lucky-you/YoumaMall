package com.zhowin.youmamall.mall.model;

/**
 * author : zho
 * date  ：2020/12/8
 * desc ：分类
 */
public class MallLeftList {


    /**
     * id : 1
     * name : 安卓软件
     * type :
     * image : https://png.lxxxin.com
     * pid : 0
     * weigh : 4
     * description :
     * status : normal
     * createtime : 1606564680
     * updatetime : 1606564680
     */

    private int id;
    private String name;
    private String type;
    private String image;
    private int pid;
    private int weigh;
    private String description;
    private String status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}
