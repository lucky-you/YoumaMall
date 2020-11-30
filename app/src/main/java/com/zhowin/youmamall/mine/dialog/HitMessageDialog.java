package com.zhowin.youmamall.mine.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.callback.OnHitMessageClickListener;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class HitMessageDialog extends BaseDialogView {
    public HitMessageDialog(@NonNull Context context) {
        super(context);
    }

    private OnHitMessageClickListener onHitMessageClickListener;

    @Override
    public int getLayoutId() {
        return R.layout.include_hit_message_dialog_layout;
    }

    public void setOnHitMessageClickListener(OnHitMessageClickListener onHitMessageClickListener) {
        this.onHitMessageClickListener = onHitMessageClickListener;
    }

    @Override
    public void initView() {
        get(R.id.tvCancel).setOnClickListener(this::onViewClick);
        get(R.id.tvDetermine).setOnClickListener(this::onViewClick);
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
                if (onHitMessageClickListener != null) {
                    onHitMessageClickListener.onDetermineClick("");
                }
                break;
        }
        dismiss();
    }
}
