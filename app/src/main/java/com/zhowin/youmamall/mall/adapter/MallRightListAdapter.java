package com.zhowin.youmamall.mall.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：
 */
public class MallRightListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MallRightListAdapter(@Nullable List<String> data) {
        super(R.layout.include_mall_right_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
