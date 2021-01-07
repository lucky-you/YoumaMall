package com.zhowin.youmamall.home.model;

/**
 * author : zho
 * date  ：2021/1/7
 * desc ：资源详情
 */
public class ResourcesDetailsInfo {


    /**
     * id : 3
     * title : 测试
     * image : /uploads/20210106/Fpw57TZUNxDBzk3n4OVjkcN65Y1W.png
     * price : 0.01
     * paid_links :
     * content : 想到的是防守打法<img src="https://png.lxxxin.com/uploads/20201225/Fn3rMifv9AmWgExAKtqfakFsqIir.jpg" alt="" />
     * category_id : 4
     * weigh : 3
     * pay_num : 0
     * hits : 0
     * createtime : 1609916764
     * updatetime : 1609926427
     * status : 1
     * is_pay : 1
     */

    private int id;
    private String title;
    private String image;
    private String price;
    private String paid_links;
    private String content;
    private int category_id;
    private int weigh;
    private int pay_num;
    private int hits;
    private int createtime;
    private int updatetime;
    private int status;
    private int is_pay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPaid_links() {
        return paid_links;
    }

    public void setPaid_links(String paid_links) {
        this.paid_links = paid_links;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }

    public int getPay_num() {
        return pay_num;
    }

    public void setPay_num(int pay_num) {
        this.pay_num = pay_num;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }
}
