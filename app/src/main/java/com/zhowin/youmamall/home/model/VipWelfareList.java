package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2020/12/10
 * desc ：福利功能
 */
public class VipWelfareList {


    /**
     * id : 1
     * name : 清空朋友圈
     * colour : #00000
     * url : https://www.baidu.com/
     * image : https://png.lxxxin.com/uploads/20201130/FqmKdry1rqKF12xrb2UA7lM8xcBA.png
     * weigh : 1
     * createtime : 1606824633
     */

    private int id;
    private String name;
    private String colour;
    private String url;
    private String image;
    private int weigh;
    private int createtime;

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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
