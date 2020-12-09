package com.zhowin.youmamall.home.callback;

import com.zhowin.youmamall.mall.model.GoodItem;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：
 */
public interface OnGoodCardItemClickListener {


    void onClickBuyCard(GoodItem goodItem);

    void onClickRootLayout(int itemId);
}
