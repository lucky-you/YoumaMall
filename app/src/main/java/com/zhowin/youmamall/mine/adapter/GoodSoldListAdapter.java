package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/1
 * desc ：
 */
public class GoodSoldListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GoodSoldListAdapter(@Nullable List<String> data) {
        super(R.layout.include_good_sold_item_view,  data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
