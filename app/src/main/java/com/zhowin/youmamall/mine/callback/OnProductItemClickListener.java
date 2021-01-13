package com.zhowin.youmamall.mine.callback;

import com.zhowin.youmamall.mall.model.MallRightList;

/**
 * author : zho
 * date  ：2020/12/10
 * desc ：商品列表的回调
 */
public interface OnProductItemClickListener {


    void onItemRootLayoutClick(MallRightList mallRightList);

    void onChangeContentClick(MallRightList mallRightList);

    void onItemOffShelf(int itemId, int goodStatus, int position);

    void onEnterOrClearCardSecret(boolean isEnter, MallRightList mallRightList);


}
