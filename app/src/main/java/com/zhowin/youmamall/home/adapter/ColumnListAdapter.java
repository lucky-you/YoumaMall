package com.zhowin.youmamall.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：栏目分类adapter
 */
public class ColumnListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ColumnListAdapter(@Nullable List<String> data) {
        super(R.layout.include_column_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tvColumnTitle, item);

    }
}
