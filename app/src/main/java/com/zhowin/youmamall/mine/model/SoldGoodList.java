package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/19
 * desc ：已售商品
 */
public class SoldGoodList {


    /**
     * name : Android  studio
     * pay_money : 100.00
     * num : 1
     * order_no : 20201218175124015916
     * item_id : 10
     * createtime : 1608285084
     * image : https://png.lxxxin.com/KuKa/2020/12/18/113739/1608262659454
     */

    private String name;
    private String pay_money;
    private int num;
    private String order_no;
    private int item_id;
    private int createtime;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
