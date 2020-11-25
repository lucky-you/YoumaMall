package com.zhowin.base_library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.R;
import com.zhowin.base_library.view.EmptyView;

import java.util.ArrayList;

/**
 * Created by : Z_B on 2017/12/21.
 * Effect : 加载空布局的帮助类
 */

public class EmptyViewUtils {


    public static void bindEmptyView(Context mContext, BaseQuickAdapter adapter) {
        bindEmptyView(mContext, adapter, 0, mContext.getString(R.string.not_have_data), true);
    }


    public static void bindEmptyView(Context mContext, BaseQuickAdapter adapter, int imgIds, String emptyString) {
        bindEmptyView(mContext, adapter, imgIds, emptyString, true);
    }


    public static View bindEmptyView(Context mContext, BaseQuickAdapter adapter, int imgIds, String emptyString, boolean isEmpty) {
        adapter.setNewData(new ArrayList());
        View emptyView = adapter.getEmptyView();
        if (emptyView == null) {
            emptyView = new EmptyView(mContext);
            adapter.setEmptyView(emptyView);
        }
        switchEmptyView(emptyView, imgIds, emptyString, isEmpty);
        return emptyView;
    }

    public static void switchEmptyView(View emptyView, int imgIds, String emptyString, boolean isEmpty) {
        ImageView imageView = emptyView.findViewById(R.id.ivHitImage);
        if (imgIds != 0) {
            imageView.setImageResource(imgIds);
        }
        TextView textView = emptyView.findViewById(R.id.tvHitText);
        if (!TextUtils.isEmpty(emptyString)) {
            textView.setText(emptyString);
        }
        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }


}
