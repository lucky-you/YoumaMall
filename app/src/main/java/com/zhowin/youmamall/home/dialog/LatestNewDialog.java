package com.zhowin.youmamall.home.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.utils.SpanUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.LatestNewInfo;

/**
 * author : zho
 * date  ：2020/12/1
 * desc ： 最新动态的Dialog
 */
public class LatestNewDialog extends BaseDialogView {
    public LatestNewDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_latest_news_dialog_layout;
    }

    @Override
    public void initView() {


        get(R.id.tvDetermine).setOnClickListener(this::onViewClick);

    }

    public void setLatestNewData(LatestNewInfo latestNewData) {
        if (latestNewData != null) {
            SpanUtils.with(get(R.id.tvContent))
                    .appendLine(latestNewData.getTitle()).setBold()
                    .appendLine()
                    .appendLine(latestNewData.getContent())
                    .appendLine()
                    .create();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvDetermine:
                dismiss();
                break;
        }
    }
}
