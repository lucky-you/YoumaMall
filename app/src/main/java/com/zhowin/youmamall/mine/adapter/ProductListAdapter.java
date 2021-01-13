package com.zhowin.youmamall.mine.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mall.model.MallRightList;
import com.zhowin.youmamall.mine.callback.OnProductItemClickListener;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：商品列表
 */
public class ProductListAdapter extends BaseQuickAdapter<MallRightList, BaseViewHolder> {

    public ProductListAdapter(@Nullable List<MallRightList> data) {
        super(R.layout.include_product_list_item_view, data);
    }

    private OnProductItemClickListener onProductItemClickListener;

    public void setOnProductItemClickListener(OnProductItemClickListener onProductItemClickListener) {
        this.onProductItemClickListener = onProductItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MallRightList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivLeftImage));
        helper.setText(R.id.tvRightValue, item.getName())
                .setText(R.id.tvOffShelf, 1 == item.getStatus() ? "下架" : "上架")
                .setTextColor(R.id.tvOffShelf, 1 == item.getStatus() ? getItemTextColor(R.color.color_E83219) : getItemTextColor(R.color.color_333));
        helper.getView(R.id.clItemRootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onItemRootLayoutClick(item);
                }
            }
        });
        helper.getView(R.id.tvOffShelf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onItemOffShelf(item.getId(), item.getStatus(), helper.getAdapterPosition());
                }
            }
        });
        helper.getView(R.id.tvXGNRText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onChangeContentClick(item);
                }
            }
        });
        helper.getView(R.id.tvLRKMText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onEnterOrClearCardSecret(true, item);
                }
            }
        });
        helper.getView(R.id.tvQKKMText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onEnterOrClearCardSecret(false, item);
                }
            }
        });
    }

    private int getItemTextColor(int color) {
        return mContext.getResources().getColor(color);
    }
}
