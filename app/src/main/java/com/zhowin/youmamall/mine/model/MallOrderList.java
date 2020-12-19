package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/18
 * desc ：商品订单
 */
public class MallOrderList {

    /**
     * item_id : 6
     * pay_no : 20201219104250058727
     * name : 微微助手
     * image : https://png.lxxxin.com/uploads/20201207/FmjxCwReF6c_I9GXZudKvyQ-ZhLl.jpg
     * createtime : 1608345770
     * id : 71
     * order_no : 20201219104250058727
     * pay_money : 50.00
     * secret_key :
     * status : 0
     * num : 1
     */

    private int item_id;
    private String pay_no;
    private String name;
    private String image;
    private int createtime;
    private int id;
    private String order_no;
    private String pay_money;
    private String secret_key;
    private int status;
    private int num;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getPay_money() {
        return pay_money;
    }

    public void setPay_money(String pay_money) {
        this.pay_money = pay_money;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
