package com.zhowin.youmamall.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.HotKeywordList;

import java.util.List;

/**
 * author : zho
 * date  ：2021/1/9
 * desc ：热搜
 */
public class HotSearchAdapter extends BaseQuickAdapter<HotKeywordList, BaseViewHolder> {
    public HotSearchAdapter(@Nullable List<HotKeywordList> data) {
        super(R.layout.include_hot_search_item_view,  data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HotKeywordList item) {
        helper.setText(R.id.tvTitle,item.getName());
    }
}
