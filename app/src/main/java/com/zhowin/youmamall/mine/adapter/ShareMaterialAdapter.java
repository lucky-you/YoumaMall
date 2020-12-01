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
 * desc ： 分享素材
 */
public class ShareMaterialAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ShareMaterialAdapter(@Nullable List<String> data) {
        super( R.layout.include_share_materia_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
