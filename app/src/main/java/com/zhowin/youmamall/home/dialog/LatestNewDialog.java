package com.zhowin.youmamall.home.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.utils.SpanUtils;
import com.zhowin.youmamall.R;

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
        SpanUtils.with(get(R.id.tvContent))
                .appendLine("所有软件：请先下载安装好软件，再购买激活码").setBold()
                .appendLine()
                .appendLine("提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信" +
                        "息提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信息提示信息")
                .appendLine()
                .create();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {

    }
}
