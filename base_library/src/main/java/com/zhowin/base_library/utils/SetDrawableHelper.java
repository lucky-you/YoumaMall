package com.zhowin.base_library.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhowin.base_library.utils.SizeUtils;

/**
 * author Z_B
 * date :2020/7/9 15:45
 * description:
 */
public class SetDrawableHelper {

    public static void setLeftDrawable(Context mContext, TextView tvTag, boolean isCheck,
                                       int radius, int checkDrawableId, int unCheckDrawableId) {
        Drawable drawableRed;
        drawableRed = mContext.getResources().getDrawable(isCheck ? checkDrawableId : unCheckDrawableId);
        drawableRed.setBounds(0, 0, drawableRed.getMinimumWidth(), drawableRed.getMinimumHeight());
        tvTag.setCompoundDrawablePadding(SizeUtils.dp2px(radius));
        tvTag.setCompoundDrawables(drawableRed, null, null, null);
    }


    public static void setRightDrawable(Context mContext, TextView rtvTag, boolean isCheck,
                                        int radius, int checkDrawableId, int unCheckDrawableId) {
        Drawable drawableRed;
        drawableRed = mContext.getResources().getDrawable(isCheck ? checkDrawableId : unCheckDrawableId);
        drawableRed.setBounds(0, 0, drawableRed.getMinimumWidth(), drawableRed.getMinimumHeight());
        rtvTag.setCompoundDrawablePadding(SizeUtils.dp2px(radius));
        rtvTag.setCompoundDrawables(null, null, drawableRed, null);
    }

    public static void setBottomDrawable(Context mContext, TextView rtvTag, boolean isCheck,
                                         int radius, int checkDrawableId, int unCheckDrawableId) {
        Drawable drawableRed;
        drawableRed = mContext.getResources().getDrawable(isCheck ? checkDrawableId : unCheckDrawableId);
        drawableRed.setBounds(0, 0, drawableRed.getMinimumWidth(), drawableRed.getMinimumHeight());
        rtvTag.setCompoundDrawablePadding(SizeUtils.dp2px(radius));
        rtvTag.setCompoundDrawables(null, null, null, drawableRed);
    }


}
