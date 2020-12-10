package com.zhowin.youmamall.mine.callback;

/**
 * author : zho
 * date  ：2020/12/10
 * desc ：
 */
public interface OnProductItemClickListener {


    void onItemRootLayoutClick();

    void onChangeContentClick();

    void onItemOffShelf(int itemId);

    void onEnterCardSecret();

}
