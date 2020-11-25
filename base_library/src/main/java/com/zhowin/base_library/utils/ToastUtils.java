package com.zhowin.base_library.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.zhowin.base_library.R;
import com.zhowin.base_library.base.BaseApplication;


public final class ToastUtils {

    private static Toast mToast;

    /**
     * 显示Toast
     */
    public static void showToast(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


    /**
     * 自定义的toast
     */
    public static void showCustomToast(Context mContext, CharSequence text) {
        TextView radiusTextView = new TextView(mContext);
        radiusTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_progress_dialog_background));
        radiusTextView.setTextColor(mContext.getResources().getColor(R.color.white));
        radiusTextView.setPadding(SizeUtils.dp2px(10), SizeUtils.dp2px(10), SizeUtils.dp2px(10), SizeUtils.dp2px(10));
        radiusTextView.setText(text);
        radiusTextView.setGravity(Gravity.CENTER);
        Toast toast = new Toast(mContext);
        toast.setView(radiusTextView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
