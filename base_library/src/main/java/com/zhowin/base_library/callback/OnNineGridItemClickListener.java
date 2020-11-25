package com.zhowin.base_library.callback;

import android.view.View;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * author Z_B
 * date :2020/5/16 10:30
 * description: 九宫格图片的点击事件
 */
public interface OnNineGridItemClickListener {


    /**
     * 点击添加图片
     */
    void onAddPictureClick();

    /**
     * 点击图片内容
     */
    void onItemClick(int position, View view);


    /**
     * 点击了删除
     */
    void onItemClickDelete(int position, List<LocalMedia> localMediaList);
}
