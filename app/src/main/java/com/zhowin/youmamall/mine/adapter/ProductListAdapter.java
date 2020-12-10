package com.zhowin.youmamall.mine.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.callback.OnProductItemClickListener;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class ProductListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ProductListAdapter(@Nullable List<String> data) {
        super(R.layout.include_product_list_item_view, data);
    }

    private OnProductItemClickListener onProductItemClickListener;

    public void setOnProductItemClickListener(OnProductItemClickListener onProductItemClickListener) {
        this.onProductItemClickListener = onProductItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {


        helper.getView(R.id.clItemRootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onItemRootLayoutClick();
                }
            }
        });

        helper.getView(R.id.tvOffShelf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onItemOffShelf(1);
                }
            }
        });
        helper.getView(R.id.tvXGNRText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onChangeContentClick();
                }
            }
        });
        helper.getView(R.id.tvLRKMText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onProductItemClickListener != null) {
                    onProductItemClickListener.onEnterCardSecret();
                }
            }
        });
    }
}
