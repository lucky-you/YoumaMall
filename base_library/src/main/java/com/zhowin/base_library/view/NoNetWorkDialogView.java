package com.zhowin.base_library.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhowin.base_library.R;
import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.callback.OnNoNetWorkClickListener;

/**
 * author : zho
 * date  ：2020/9/30
 * desc ：token 失效 的dialog
 */
public class NoNetWorkDialogView extends BaseDialogView {


    private OnNoNetWorkClickListener onNoNetWorkClickListener;
    private TextView tvTitle;


    public NoNetWorkDialogView(@NonNull Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setOnNoNetWorkClickListener(OnNoNetWorkClickListener onNoNetWorkClickListener) {
        this.onNoNetWorkClickListener = onNoNetWorkClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_no_new_work_layout;
    }

    @Override
    public void initView() {
        tvTitle = get(R.id.tvTitle);
        get(R.id.tvDuration).setOnClickListener(this::onViewClick);
    }

    @Override
    public void initData() {

    }

    public void setHitTitleMessage(String hitTitle) {
        if (!TextUtils.isEmpty(hitTitle))
            tvTitle.setText(hitTitle);

    }

    @Override
    public void onViewClick(View view) {
        if (view.getId() == R.id.tvDuration) {
            if (onNoNetWorkClickListener != null) {
                onNoNetWorkClickListener.onDurationClick();
            }
        }

    }
}
