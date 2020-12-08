package com.zhowin.youmamall.mall.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mall.model.MallLeftList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：
 */
public class MallLeftListAdapter extends BaseQuickAdapter<MallLeftList, BaseViewHolder> {

    private int currentPosition;

    public MallLeftListAdapter(@Nullable List<MallLeftList> data, int position) {
        super(R.layout.include_mall_left_item_view, data);
        this.currentPosition = position;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallLeftList item) {
        helper.setText(R.id.tvLeftTitle, item.getName())
                .setVisible(R.id.leftView, currentPosition == helper.getAdapterPosition())
                .setBackgroundColor(R.id.tvLeftTitle, currentPosition == helper.getAdapterPosition() ? getItemColor(R.color.color_FFFEEF) : getItemColor(R.color.white));

    }

    private int getItemColor(int color) {
        return mContext.getResources().getColor(color);
    }
}
