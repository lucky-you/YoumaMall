package com.zhowin.youmamall.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.MessageList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：信息列表
 */
public class MessageListAdapter extends BaseQuickAdapter<MessageList, BaseViewHolder> {
    public MessageListAdapter(@Nullable List<MessageList> data) {
        super(R.layout.include_message_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageList item) {
        helper.setText(R.id.tvLeftTitle, item.getTitle())
                .setText(R.id.tvContent, item.getContent())
                .setText(R.id.tvCreateTime, DateHelpUtils.getStrTimeNotSeconds(item.getCreatetime()))
                .setText(R.id.tvRightTie, DateHelpUtils.getCurrentDayNotYear(item.getCreatetime()));

    }
}
