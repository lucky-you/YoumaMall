package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2021/1/7
 * desc ：资源列表
 */
public class ResourcesList {


    /**
     * id : 3
     * image : https://png.lxxxin.com/uploads/20210106/Fpw57TZUNxDBzk3n4OVjkcN65Y1W.png
     * title : 测试
     * price : 0.01
     * createtime : 1609916764
     */

    private int id;
    private String image;
    private String title;
    private String price;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }
}
