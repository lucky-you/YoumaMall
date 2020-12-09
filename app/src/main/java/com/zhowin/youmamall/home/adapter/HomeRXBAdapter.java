package com.zhowin.youmamall.home.adapter;

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
 * date  ：2020/11/26
 * desc ：热销榜
 */
public class HomeRXBAdapter extends BaseQuickAdapter<MallRightList, BaseViewHolder> {


    public HomeRXBAdapter(@Nullable List<MallRightList> data) {
        super(R.layout.include_item_rxb_layout, data);
    }

    private OnGoodCardItemClickListener onGoodCardItemClickListener;

    public void setOnGoodCardItemClickListener(OnGoodCardItemClickListener onGoodCardItemClickListener) {
        this.onGoodCardItemClickListener = onGoodCardItemClickListener;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallRightList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivLeftImage));
        helper.setText(R.id.tvCardName, item.getName())
                .setText(R.id.tvNumberOfPayments, item.getSale() + "人付款")
                .setText(R.id.tvCommissionPrice, "佣金" + item.getCommission_money() + "元")
                .setText(R.id.tvPrice, "¥" + item.getPrice());

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
        helper.getView(R.id.clRXBRootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodCardItemClickListener != null) {
                    onGoodCardItemClickListener.onClickRootLayout(item.getId());
                }
            }
        });
    }
}
