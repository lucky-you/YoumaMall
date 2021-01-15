package com.zhowin.youmamall.mine.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.activity.ProductDetailsActivity;
import com.zhowin.youmamall.mine.model.SoldGoodList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/1
 * desc ：已售商品
 */
public class GoodSoldListAdapter extends BaseQuickAdapter<SoldGoodList, BaseViewHolder> {
    public GoodSoldListAdapter(@Nullable List<SoldGoodList> data) {
        super(R.layout.include_good_sold_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SoldGoodList item) {

        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivLeftImage));

        helper.setText(R.id.tvGoodCardNumber, item.getOrder_no())
                .setText(R.id.tvRightValue, item.getName())
                .setText(R.id.tvOffShelf, "x" + item.getNum())
                .setText(R.id.tvPrice, "¥" + item.getPay_money())
                .getView(R.id.clSoldGoodRootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailsActivity.start(mContext, item.getItem_id(),false);
            }
        });
    }
}
