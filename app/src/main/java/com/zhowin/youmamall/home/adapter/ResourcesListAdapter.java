package com.zhowin.youmamall.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.ResourcesList;

import java.util.List;

/**
 * author : zho
 * date  ：2021/1/7
 * desc ：资源列表
 */
public class ResourcesListAdapter extends BaseQuickAdapter<ResourcesList, BaseViewHolder> {
    public ResourcesListAdapter(@Nullable List<ResourcesList> data) {
        super(R.layout.include_resources_list_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ResourcesList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivLeftImage));
        helper.setText(R.id.tvRightText, item.getTitle())
                .setText(R.id.tvCreateTime, DateHelpUtils.getStrTimeNotSeconds(item.getCreatetime()));
    }
}
