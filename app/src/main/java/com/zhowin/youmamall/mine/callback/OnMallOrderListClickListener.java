package com.zhowin.youmamall.mine.callback;

import com.zhowin.youmamall.mall.model.GoodItem;

/**
 * author : zho
 * date  ：2020/12/18
 * desc ：商城订单列表
 */
public interface OnMallOrderListClickListener {


    void onStartPayment(GoodItem goodItem);

    void onGoodDetails(int goodId);

    void onConfirmReceipt(int goodId);

}
