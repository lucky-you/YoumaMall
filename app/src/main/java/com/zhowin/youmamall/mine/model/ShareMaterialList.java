package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/19
 * desc ：分享素材
 */
public class ShareMaterialList {


    /**
     * id : 1
     * image : https://png.lxxxin.com/uploads/20201214/FnO4lsFI3_2qOiYVxc5fbzIwgEIY.jpg
     * weigh : 1
     * createtime : 1607949705
     */

    private int id;
    private String image;
    private int weigh;
    private int createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
