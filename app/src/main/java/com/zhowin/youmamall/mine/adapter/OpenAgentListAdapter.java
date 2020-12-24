package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.model.AgentList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/17
 * desc ：开通vip  / 代理
 */
public class OpenAgentListAdapter extends BaseQuickAdapter<AgentList, BaseViewHolder> {

    private int currentPosition = 0;
    private int adapterType;

    public OpenAgentListAdapter(@Nullable List<AgentList> data, int type) {
        super(R.layout.include_open_agent_list_item_view, data);
        this.adapterType = type;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AgentList item) {
        helper
                .setText(R.id.tvLeftTitle, item.getName())
                .setText(R.id.tvLeftContent, item.getOpen_detail())
                .setText(R.id.tvPrice, "¥" + item.getOpen_price())
                .setGone(R.id.ivSelect, 1 == adapterType)
                .setImageResource(R.id.ivSelect, currentPosition == helper.getAdapterPosition() ? R.drawable.icon_circle_select_y : R.drawable.icon_circle_select_n);
    }
}
