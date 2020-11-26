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
 * desc ：热销榜
 */
public class HomeRXBAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeRXBAdapter(@Nullable List<String> data) {
        super(R.layout.include_item_rxb_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
