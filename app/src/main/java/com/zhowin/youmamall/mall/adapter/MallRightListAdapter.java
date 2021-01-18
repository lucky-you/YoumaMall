package com.zhowin.youmamall.mall.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.callback.OnGoodCardItemClickListener;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mall.model.MallRightList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：
 */
public class MallRightListAdapter extends BaseQuickAdapter<MallRightList, BaseViewHolder> {

    public MallRightListAdapter(@Nullable List<MallRightList> data) {
        super(R.layout.include_mall_right_item_view, data);
    }

    private OnGoodCardItemClickListener onGoodCardItemClickListener;


    public void setOnGoodCardItemClickListener(OnGoodCardItemClickListener onGoodCardItemClickListener) {
        this.onGoodCardItemClickListener = onGoodCardItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallRightList item) {

        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivCardImage));
        helper.setText(R.id.tvNumberOfPayments, item.getSale() + "人付款")
                .setText(R.id.tvCardName, item.getName())
                .setText(R.id.tvPrice, "¥" + item.getPrice())
                .setText(R.id.tvCommissionPrice, "佣金" + item.getCommission_money() + "元");

        helper.getView(R.id.tvBuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodCardItemClickListener != null) {
                    GoodItem goodItem = new GoodItem();
                    goodItem.setId(item.getId());
                    goodItem.setName(item.getName());
                    goodItem.setPrice(item.getPrice());
                    goodItem.setImage(item.getImage());
                    onGoodCardItemClickListener.onClickBuyCard(goodItem);
                }
            }
        });
        helper.getView(R.id.llRootItemLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodCardItemClickListener != null) {
                    onGoodCardItemClickListener.onClickRootLayout(item.getId());
                }
            }
        });

    }
}
