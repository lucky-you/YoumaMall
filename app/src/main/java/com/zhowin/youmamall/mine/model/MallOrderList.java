package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/18
 * desc ：商品订单
 */
public class MallOrderList {


    /**
     * pay_no : 20201218170108030318
     * name : 亲密约
     * image : https://png.lxxxin.com/uploads/20201130/FqmKdry1rqKF12xrb2UA7lM8xcBA.png
     * createtime : 1608282068
     * id : 33
     * order_no : 20201218170108030318
     * pay_money : 0.01
     * secret_key :
     * status : 1
     * num : 1
     */

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
