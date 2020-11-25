package com.zhowin.base_library.callback;

import android.app.Dialog;

/**
 * author Z_B
 * date :2018/5/15 17:52
 * description:
 */
public interface OnCenterHitMessageListener {
    /**
     * 取消
     *
     * @param dialog dialog对象
     */
    void onNegativeClick(Dialog dialog);

    /**
     * 确定
     *
     * @param dialog dialog对象
     */
    void onPositiveClick(Dialog dialog);
}
