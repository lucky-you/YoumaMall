package com.zhowin.youmamall.home.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.widget.MsgView;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.MessageCategory;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/30
 * desc ：消息类型
 */
public class MessageCategoryAdapter extends BaseQuickAdapter<MessageCategory, BaseViewHolder> {

    private int[] leftDrawableId;


    public MessageCategoryAdapter(@Nullable List<MessageCategory> data) {
        super(R.layout.include_message_category_item_view, data);
    }

    public void setLeftDrawableId(int[] leftDrawableId) {
        this.leftDrawableId = leftDrawableId;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageCategory item) {
        helper.setText(R.id.tvLeftTitle, item.getName())
                .setText(R.id.tvLeftContent, item.getContent())
                .setImageResource(R.id.civLeftImage, leftDrawableId[helper.getAdapterPosition()])
                .setText(R.id.tvRightTie, DateHelpUtils.getPostDetailTime(item.getCreatetime()));
        MsgView msvHitMessage = helper.getView(R.id.msvHitMessage);
        msvHitMessage.setVisibility(item.getRead_num() > 0 ? View.VISIBLE : View.GONE);
        msvHitMessage.setText(String.valueOf(item.getRead_num()));

    }
}
