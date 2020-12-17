package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.model.AccountTurnoverList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class AccountTurnoverAdapter extends BaseQuickAdapter<AccountTurnoverList, BaseViewHolder> {
    public AccountTurnoverAdapter(@Nullable List<AccountTurnoverList> data) {
        super(R.layout.include_account_turnover_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AccountTurnoverList item) {

        helper.setText(R.id.tvLeftType, item.getMemo())
                .setText(R.id.tvLeftTime, DateHelpUtils.getStringDate(item.getCreatetime()))
                .setText(R.id.tvRightValue, item.getMoney())
                .setText(R.id.tvRightStatus, "缴纳成功");

    }
}
