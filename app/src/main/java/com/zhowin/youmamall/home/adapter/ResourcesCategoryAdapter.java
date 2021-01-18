package com.zhowin.youmamall.home.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.ResourcesCategory;

import java.util.List;

/**
 * author : zho
 * date  ：2021/1/7
 * desc ：资源分类
 */
public class ResourcesCategoryAdapter extends BaseQuickAdapter<ResourcesCategory, BaseViewHolder> {

    private int currentPosition = -1;

    public ResourcesCategoryAdapter(@Nullable List<ResourcesCategory> data) {
        super(R.layout.include_resources_category_item_view, data);
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ResourcesCategory item) {
        TextView tvCategoryTitle = helper.getView(R.id.tvCategoryTitle);
        tvCategoryTitle.setText(item.getName());
        if (currentPosition == helper.getAdapterPosition()) {
            tvCategoryTitle.setBackground(mContext.getResources().getDrawable(R.drawable.shape_theme_color_4dp_bg));
        } else {
            tvCategoryTitle.setBackground(mContext.getResources().getDrawable(R.drawable.shape_grey_color_4dp_bg));
        }
    }
}
