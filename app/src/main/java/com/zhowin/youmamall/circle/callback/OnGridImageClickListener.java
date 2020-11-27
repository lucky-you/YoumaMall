package com.zhowin.youmamall.circle.callback;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/20
 * desc ：图片点击
 */
public interface OnGridImageClickListener {


    void onItemImageClick(List<String> imageList, int position);
}
