package com.zhowin.youmamall.dynamic.callback;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/8
 * desc ：
 */
public interface OnDynamicItemClickListener {


    void onSavePhoto(List<String> images);

    void onCopyContent(String content);


    void onImageItemClick(List<String> imageList, int position);
}
