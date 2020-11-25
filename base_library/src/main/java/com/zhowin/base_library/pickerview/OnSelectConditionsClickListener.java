package com.zhowin.base_library.pickerview;

/**
 * author      : Z_B
 * date       : 2018/10/12
 * function  : 条件选择器结果的回调
 */
public interface OnSelectConditionsClickListener {


    /**
     * 被选中的条件
     */
    void onConditionsSelect(int position, String selectName, int selectId);
}
