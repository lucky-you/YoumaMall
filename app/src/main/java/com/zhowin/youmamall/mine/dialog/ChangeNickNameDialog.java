package com.zhowin.youmamall.mine.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.callback.OnHitMessageClickListener;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class ChangeNickNameDialog extends BaseDialogView {

    private EditText editNickName;

    private OnHitMessageClickListener onHitMessageClickListener;

    public ChangeNickNameDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_change_nickname_dialog;
    }

    @Override
    public void initView() {
        editNickName = get(R.id.editNickName);
        get(R.id.tvCancel).setOnClickListener(this::onViewClick);
        get(R.id.tvDetermine).setOnClickListener(this::onViewClick);
    }

    public void setEditNickName(String nickName) {
        if (!TextUtils.isEmpty(nickName)) {
            editNickName.setText(nickName);
            editNickName.setSelection(nickName.length());
        }
    }

    public void setOnHitMessageClickListener(OnHitMessageClickListener onHitMessageClickListener) {
        this.onHitMessageClickListener = onHitMessageClickListener;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                break;
            case R.id.tvDetermine:
                String nickName = editNickName.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)) {
                    ToastUtils.showToast("请输入昵称");
                    return;
                }
                if (onHitMessageClickListener != null) {
                    onHitMessageClickListener.onDetermineClick(nickName);
                }
                break;
        }
        dismiss();
    }
}
