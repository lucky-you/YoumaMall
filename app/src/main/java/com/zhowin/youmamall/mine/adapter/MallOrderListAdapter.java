package com.zhowin.youmamall.mine.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mine.callback.OnMallOrderListClickListener;
import com.zhowin.youmamall.mine.model.MallOrderList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class MallOrderListAdapter extends BaseQuickAdapter<MallOrderList, BaseViewHolder> {
    public MallOrderListAdapter(@Nullable List<MallOrderList> data) {
        super(R.layout.include_mall_order_item_view, data);
    }

    private OnMallOrderListClickListener onMallOrderListClickListener;

    public void setOnMallOrderListClickListener(OnMallOrderListClickListener onMallOrderListClickListener) {
        this.onMallOrderListClickListener = onMallOrderListClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallOrderList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivLeftImage));
        helper.setText(R.id.tvOrderNumber, item.getOrder_no())
                .setText(R.id.tvCardName, item.getName())
                .setText(R.id.tvNumberOfPayments, "x" + item.getNum())
                .setText(R.id.tvPrice, "¥" + item.getPay_money())
                .setText(R.id.tvCJSJText, DateHelpUtils.getStringDate(item.getCreatetime()))
                .setText(R.id.tvCPMYText, item.getSecret_key())
                .setText(R.id.tvSFJEText, "¥" + item.getPay_money())
                .setGone(R.id.tvNowPay, 0 == item.getStatus())
                .getView(R.id.llOrderRootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMallOrderListClickListener != null) {
                    onMallOrderListClickListener.onGoodDetails(item.getItem_id());
                }
            }
        });

        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.tvOrderStatus, "待付款")
                        .setTextColor(R.id.tvOrderStatus, getItemTextColor(R.color.color_FFA53B))
                        .getView(R.id.tvNowPay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onMallOrderListClickListener != null) {
                            GoodItem goodItem = new GoodItem();
                            goodItem.setId(item.getItem_id());
                            goodItem.setName(item.getName());
                            goodItem.setPrice(item.getPay_money());
                            goodItem.setImage(item.getImage());
                            goodItem.setPayOrder(item.getPay_no());
                            onMallOrderListClickListener.onStartPayment(goodItem);
                        }
                    }
                });
                break;
            case 1:
                helper.setText(R.id.tvOrderStatus, "待发货");
                break;
            case 2:
                helper.setText(R.id.tvOrderStatus, "待收货");
                break;
            case 3:
                helper.setText(R.id.tvOrderStatus, "已完成")
                        .setTextColor(R.id.tvOrderStatus, getItemTextColor(R.color.color_227BFF));
                break;
        }
    }

    private int getItemTextColor(int color) {
        return mContext.getResources().getColor(color);
    }
}
