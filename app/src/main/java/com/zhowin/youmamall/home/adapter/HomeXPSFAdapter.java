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
 * desc ：新品首发
 */
public class HomeXPSFAdapter extends BaseQuickAdapter<MallRightList, BaseViewHolder> {

    public HomeXPSFAdapter(@Nullable List<MallRightList> data) {
        super(R.layout.include_item_xpsf_layout, data);
    }

    private OnGoodCardItemClickListener onGoodCardItemClickListener;

    public void setOnGoodCardItemClickListener(OnGoodCardItemClickListener onGoodCardItemClickListener) {
        this.onGoodCardItemClickListener = onGoodCardItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallRightList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivCardImage));
        helper.setText(R.id.tvNumberOfPayments, item.getPay_num() + "人付款")
                .setText(R.id.tvCardName, item.getName())
                .setText(R.id.tvCommissionPrice, "佣金" + item.getCommission_money() + "元")
                .setText(R.id.tvPrice, "¥" + item.getPrice());
        if (0 == item.getUser_id()) {
            switch (item.getType()) {
                case 1: //推荐
                    helper.setText(R.id.tvGoodStatus, "推荐")
                            .setBackgroundColor(R.id.tvGoodStatus, getItemColor(R.color.color_50AD65));
                    break;
                case 2: //热卖
                    helper.setText(R.id.tvGoodStatus, "热卖")
                            .setBackgroundColor(R.id.tvGoodStatus, getItemColor(R.color.color_F7AA0A));
                    break;
                case 3: //停售
                    helper.setText(R.id.tvGoodStatus, "停售")
                            .setBackgroundColor(R.id.tvGoodStatus, getItemColor(R.color.color_757575));
                    break;
            }
        } else {
            if (0 == item.getSale()) {
                helper.setText(R.id.tvGoodStatus, "售罄")
                        .setBackgroundColor(R.id.tvGoodStatus, getItemColor(R.color.color_757575));
            } else {
                switch (item.getType()) {
                    case 1: //推荐
                        helper.setText(R.id.tvGoodStatus, "推荐")
                                .setBackgroundColor(R.id.tvGoodStatus, getItemColor(R.color.color_50AD65));
                        break;
                    case 2: //热卖
                        helper.setText(R.id.tvGoodStatus, "热卖")
                                .setBackgroundColor(R.id.tvGoodStatus, getItemColor(R.color.color_F7AA0A));
                        break;
                    case 3: //停售
                        helper.setText(R.id.tvGoodStatus, "停售")
                                .setBackgroundColor(R.id.tvGoodStatus, getItemColor(R.color.color_757575));
                        break;
                }
            }
        }
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
        helper.getView(R.id.llHomeItemRootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodCardItemClickListener != null) {
                    onGoodCardItemClickListener.onClickRootLayout(item.getId());
                }
            }
        });
    }

    private int getItemColor(int color) {
        return mContext.getResources().getColor(color);
    }
}
