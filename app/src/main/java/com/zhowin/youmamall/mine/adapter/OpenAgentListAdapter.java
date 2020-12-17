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
 * desc ：
 */
public class OpenAgentListAdapter extends BaseQuickAdapter<AgentList, BaseViewHolder> {
    public OpenAgentListAdapter(@Nullable List<AgentList> data) {
        super(R.layout.include_open_agent_list_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AgentList item) {
        helper
                .setText(R.id.tvLeftTitle, item.getTitle())
                .setText(R.id.tvLeftContent, item.getContent() + "%的返佣特权")
                .setText(R.id.tvPrice, "¥" + item.getPrice());
    }
}
