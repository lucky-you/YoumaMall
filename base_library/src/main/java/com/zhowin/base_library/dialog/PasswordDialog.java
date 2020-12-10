package com.zhowin.base_library.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhowin.base_library.R;
import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.callback.OnPasswordEditListener;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.PasswordEditText;

/**
 * author : zho
 * date  ：2020/12/10
 * desc ：密码输入框的dialog
 */
public class PasswordDialog extends BaseDialogView {

    private PasswordEditText editPassword;
    private TextView tvDialogTitle;
    private String passwordText;
    private OnPasswordEditListener onPasswordEditListener;

    public PasswordDialog(@NonNull Context context) {
        super(context);
    }

    public void setOnPasswordEditListener(OnPasswordEditListener onPasswordEditListener) {
        this.onPasswordEditListener = onPasswordEditListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_password_dialog_view;
    }

    @Override
    public void initView() {
        tvDialogTitle = get(R.id.tvDialogTitle);
        editPassword = get(R.id.editPassword);
        get(R.id.tvCancel).setOnClickListener(this::onViewClick);
        get(R.id.tvDetermine).setOnClickListener(this::onViewClick);
        editPassword.setOnPasswordFullListener(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String password) {
                passwordText = password;
            }
        });
    }

    /**
     * 设置标题
     */
    public void setDialogTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvDialogTitle.setText(title);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        int id = view.getId();
        if (id == R.id.tvCancel) {
            if (onPasswordEditListener != null) {
                onPasswordEditListener.onCancelPayment();
            }
            dismiss();
        } else if (id == R.id.tvDetermine) {
            if (!TextUtils.isEmpty(passwordText)) {
                if (passwordText.length() >= 6) {
                    if (onPasswordEditListener != null) {
                        onPasswordEditListener.onDeterminePayment(passwordText);
                    }
                    dismiss();
                }
            } else {
                ToastUtils.showToast("请输入支付密码");
            }
        }
    }
}
