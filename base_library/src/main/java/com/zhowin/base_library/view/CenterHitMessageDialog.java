package com.zhowin.base_library.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhowin.base_library.R;
import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.utils.SizeUtils;


/**
 * description: 居中提示的dialog -->全局通用
 */
public class CenterHitMessageDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private TextView tvContentTitle, tvPositiveTxt, tvNegativeTxt;
    private String contentTitle;
    private String positiveText;
    private String negativeText;
    private int contentTextColor;
    private int positiveTextColor;
    private int negativeTextColor;
    private OnCenterHitMessageListener onCenterHitMessageListener;

    public void setOnCenterHitMessageListener(OnCenterHitMessageListener onCenterHitMessageListener) {
        this.onCenterHitMessageListener = onCenterHitMessageListener;
    }

    public CenterHitMessageDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public CenterHitMessageDialog(@NonNull Context context, String contentTitle) {
        super(context, R.style.DialogFragmentStyle);
        this.mContext = context;
        this.contentTitle = contentTitle;
    }

    public CenterHitMessageDialog(@NonNull Context context, String contentTitle, OnCenterHitMessageListener onCenterHitMessageListener) {
        super(context, R.style.DialogFragmentStyle);
        this.mContext = context;
        this.contentTitle = contentTitle;
        this.onCenterHitMessageListener = onCenterHitMessageListener;
    }


    public CenterHitMessageDialog setContent(String content) {
        this.contentTitle = content;
        return this;
    }

    public CenterHitMessageDialog setPositiveButton(String name) {
        this.positiveText = name;
        return this;
    }

    public CenterHitMessageDialog setNegativeButton(String name) {
        this.negativeText = name;
        return this;
    }

    public CenterHitMessageDialog setContentTextColor(int color) {
        this.contentTextColor = color;
        return this;
    }

    public CenterHitMessageDialog setPositiveTextColor(int color) {
        this.positiveTextColor = color;
        return this;
    }

    public CenterHitMessageDialog setNegativeTextColor(int color) {
        this.negativeTextColor = color;
        return this;
    }

    public CenterHitMessageDialog setCancelableThat(boolean cancel) {
        this.setCancelable(cancel);
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_center_hit_message_layout);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = SizeUtils.dp2px(300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        initViews();
    }

    private void initViews() {
        tvContentTitle = findViewById(R.id.tvContentTitle);
        tvPositiveTxt = findViewById(R.id.tvCancel);
        tvNegativeTxt = findViewById(R.id.tvDetermine);
        tvPositiveTxt.setOnClickListener(this);
        tvNegativeTxt.setOnClickListener(this);
        if (!TextUtils.isEmpty(contentTitle)) {
            tvContentTitle.setText(contentTitle);
        }
        if (!TextUtils.isEmpty(positiveText)) {
            tvPositiveTxt.setText(positiveText);
        }
        if (!TextUtils.isEmpty(negativeText)) {
            tvNegativeTxt.setText(negativeText);
        }
        if (contentTextColor != 0) {
            tvContentTitle.setTextColor(contentTextColor);
        }
        if (positiveTextColor != 0) {
            tvPositiveTxt.setTextColor(positiveTextColor);
        }
        if (negativeTextColor != 0) {
            tvNegativeTxt.setTextColor(negativeTextColor);
        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvCancel) {
            if (onCenterHitMessageListener != null) {
                onCenterHitMessageListener.onNegativeClick(this);
            }
            dismiss();
        } else if (id == R.id.tvDetermine) {
            if (onCenterHitMessageListener != null) {
                onCenterHitMessageListener.onPositiveClick(this);
            }
            dismiss();
        }

    }
}
