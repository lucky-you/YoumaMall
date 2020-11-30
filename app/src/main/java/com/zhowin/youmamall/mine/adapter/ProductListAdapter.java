package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class ProductListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ProductListAdapter(@Nullable List<String> data) {
        super(R.layout.include_product_list_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
