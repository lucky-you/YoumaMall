package com.zhowin.youmamall.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mall.model.MallLeftList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/10
 * desc ：
 */
public class HomeCategoryListAdapter extends BaseQuickAdapter<MallLeftList, BaseViewHolder> {

    public HomeCategoryListAdapter(@Nullable List<MallLeftList> data) {
        super(R.layout.include_home_category_list_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallLeftList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.civColumn));
        helper.setText(R.id.tvColumnTitle,item.getName());
    }
}
