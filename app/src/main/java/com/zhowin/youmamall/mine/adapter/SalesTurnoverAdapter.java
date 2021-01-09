package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.model.SalesTurnoverList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/1
 * desc ：出售流水
 */
public class SalesTurnoverAdapter extends BaseQuickAdapter<SalesTurnoverList, BaseViewHolder> {
    public SalesTurnoverAdapter(@Nullable List<SalesTurnoverList> data) {
        super(R.layout.include_sales_turnover_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SalesTurnoverList item) {
        helper.setText(R.id.tvLeftType, item.getMemo())
                .setText(R.id.tvLeftTime, DateHelpUtils.getStringDate(item.getCreatetime()))
                .setText(R.id.tvRightValue, "+" + item.getMoney());

    }
}
