package com.zhowin.youmamall.mall.model;

import com.google.gson.annotations.SerializedName;

/**
 * author : zho
 * date  ：2020/12/9
 * desc ：商品的数据
 */
public class MallRightList {


    /**
     * id : 6
     * name : 微微助手
     * shop_category_id : 1
     * image : https://png.lxxxin.com/uploads/20201207/FmjxCwReF6c_I9GXZudKvyQ-ZhLl.jpg
     * price : 50.00
     * original_price : 0.00
     * contact : @
     * content : dddd
     * status : 1
     * type : 1
     * user_id : 0
     * sale : 4
     * examine : 1
     * weigh : 6
     * createtime : 1607354025
     * updatetime : 1607425862
     * commission : {"1":"1","2":"2","3":"4","4":"5","5":"7"}
     * sales_volume : 0
     * pay_num : 0
     * is_vip : 0
     * merchant_id : 0
     * is_del : 0
     * available_money : 0.00
     * commission_money : 1
     */

    private int id;
    private String name;
    private int shop_category_id;
    private String image;
    private String price;
    private String original_price;
    private String contact;
    private String content;
    private int status;
    private int type;
    private int user_id;
    private int sale;
    private int examine;
    private int weigh;
    private int createtime;
    private int updatetime;
    private Object commission;
    private int sales_volume;
    private int pay_num;
    private int is_vip;
    private int merchant_id;
    private int is_del;
    private String available_money;
    private String commission_money;

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

    public int getShop_category_id() {
        return shop_category_id;
    }

    public void setShop_category_id(int shop_category_id) {
        this.shop_category_id = shop_category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getExamine() {
        return examine;
    }

    public void setExamine(int examine) {
        this.examine = examine;
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

    public Object getCommission() {
        return commission;
    }

    public void setCommission(Object commission) {
        this.commission = commission;
    }

    public int getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(int sales_volume) {
        this.sales_volume = sales_volume;
    }

    public int getPay_num() {
        return pay_num;
    }

    public void setPay_num(int pay_num) {
        this.pay_num = pay_num;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getAvailable_money() {
        return available_money;
    }

    public void setAvailable_money(String available_money) {
        this.available_money = available_money;
    }

    public String getCommission_money() {
        return commission_money;
    }

    public void setCommission_money(String commission_money) {
        this.commission_money = commission_money;
    }


}
