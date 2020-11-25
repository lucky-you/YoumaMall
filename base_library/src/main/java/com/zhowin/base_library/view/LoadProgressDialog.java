package com.zhowin.base_library.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhowin.base_library.R;


/**
 * function  : 加载进度的Dialog
 */
public class LoadProgressDialog extends Dialog {


    private Context context = null;
    private LoadProgressDialog progressDialog = null;
    private MiniLoadingView miniLoadingView = null;

    public LoadProgressDialog(Context context) {
        super(context);
        this.context = context;
    }


    public LoadProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public LoadProgressDialog createLoadingDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.include_dialog_progress_layout, null);
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        miniLoadingView = view.findViewById(R.id.miniLoadingView);
        TextView tipTextView = view.findViewById(R.id.tvLoadText);
        miniLoadingView.start();
        tipTextView.setText(msg);
        progressDialog = new LoadProgressDialog(context, R.style.myProgressDialog);// 创建自定义样式dialog
        progressDialog.setCanceledOnTouchOutside(false);//点击外围不可消失
        progressDialog.setCancelable(false);// 不可以用“返回键”取消
        progressDialog.setContentView(layout, new LinearLayout.LayoutParams(dip2px(context, 120), dip2px(context, 110)));
        return progressDialog;
    }

    /**
     * 停止动画
     */
    public void stopAnimator() {
        if (miniLoadingView != null) {
            miniLoadingView.stop();
            miniLoadingView = null;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
