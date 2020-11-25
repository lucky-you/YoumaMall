package com.zhowin.base_library.pickerview;

/**
 * author      : Z_B
 * date       : 2018/9/26
 * function  : 时间选择的回调
 */
public interface OnSelectTimeClickListener {

    /**
     * 返回字符串类型的时间
     *
     * @param dateTime
     */
    void onDateTime(String dateTime);

}
