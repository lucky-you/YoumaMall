package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/14
 * desc ：保证金的信息
 */
public class DepositMessage {


    /**
     * is_pay : 0
     * apply_merchant_money : 1
     * bond_detail : 保证金描述
     */

    private int is_pay;
    private String apply_merchant_money;
    private String bond_detail;

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public String getApply_merchant_money() {
        return apply_merchant_money;
    }

    public void setApply_merchant_money(String apply_merchant_money) {
        this.apply_merchant_money = apply_merchant_money;
    }

    public String getBond_detail() {
        return bond_detail;
    }

    public void setBond_detail(String bond_detail) {
        this.bond_detail = bond_detail;
    }
}
