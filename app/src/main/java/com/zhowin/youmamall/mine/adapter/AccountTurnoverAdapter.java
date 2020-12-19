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

    private int fragmentIndex;


    public AccountTurnoverAdapter(@Nullable List<AccountTurnoverList> data) {
        super(R.layout.include_account_turnover_item_view, data);
    }

    public void setFragmentIndex(int fragmentIndex) {
        this.fragmentIndex = fragmentIndex;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AccountTurnoverList item) {
        switch (fragmentIndex) {
            case 0:
                helper.setText(R.id.tvLeftType, item.getMemo())
                        .setText(R.id.tvLeftTime, DateHelpUtils.getStringDate(item.getCreatetime()))
                        .setText(R.id.tvRightValue, item.getMoney())
                        .setGone(R.id.tvRightStatus, false);
                break;
            case 1:
                helper.setText(R.id.tvLeftType, "提现")
                        .setText(R.id.tvLeftTime, DateHelpUtils.getStringDate(item.getCreatetime()))
                        .setText(R.id.tvRightValue, "-" + item.getMoney())
                        .setGone(R.id.tvRightStatus, true);
                switch (item.getStatus()) {
                    case 0:
                        helper.setText(R.id.tvRightStatus, "处理中")
                                .setTextColor(R.id.tvRightStatus, getItemTextColor(R.color.color_FF560B));
                        break;
                    case 1:
                        helper.setText(R.id.tvRightStatus, "已拒绝")
                                .setTextColor(R.id.tvRightStatus, getItemTextColor(R.color.color_E83219));
                        break;
                    case 2:
                        helper.setText(R.id.tvRightStatus, "已到账")
                                .setTextColor(R.id.tvRightStatus, getItemTextColor(R.color.color_666));
                        break;
                }
                break;
        }

    }


    private int getItemTextColor(int color) {
        return mContext.getResources().getColor(color);
    }
}
