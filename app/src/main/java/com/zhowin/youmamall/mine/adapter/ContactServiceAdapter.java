package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.model.ContactServiceList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/9
 * desc ：
 */
public class ContactServiceAdapter extends BaseQuickAdapter<ContactServiceList, BaseViewHolder> {
    public ContactServiceAdapter(@Nullable List<ContactServiceList> data) {
        super(R.layout.include_contact_service_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ContactServiceList item) {
        helper.setText(R.id.tvLeftTitle, item.getName())
                .setText(R.id.tvRightValue, item.getValue());

    }
}
