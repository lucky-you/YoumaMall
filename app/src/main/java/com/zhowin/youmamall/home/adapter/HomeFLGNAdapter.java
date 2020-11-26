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
 * desc ：福利功能
 */
public class HomeFLGNAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public HomeFLGNAdapter(@Nullable List<String> data) {
        super(R.layout.include_item_flgn_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
