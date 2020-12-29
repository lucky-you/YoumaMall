package com.zhowin.youmamall.home.adapter;


import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.callback.OnHomeSeeMoreListener;
import com.zhowin.youmamall.home.model.VipWelfareList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：福利功能
 */
public class HomeFLGNAdapter extends BaseQuickAdapter<VipWelfareList, BaseViewHolder> {
    public HomeFLGNAdapter(@Nullable List<VipWelfareList> data) {
        super(R.layout.include_item_flgn_layout, data);
    }

    private OnHomeSeeMoreListener onHomeSeeMoreListener;

    public void setOnHomeSeeMoreListener(OnHomeSeeMoreListener onHomeSeeMoreListener) {
        this.onHomeSeeMoreListener = onHomeSeeMoreListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VipWelfareList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivIconImage));
        helper.setText(R.id.tvIconName, item.getName());
        RoundedImageView rivBackground = helper.getView(R.id.rivBackground);
        String colorText = item.getColour();
        int color;
        if (colorText.startsWith("#")) {
            color = Color.parseColor(item.getColour());
        } else {
            color = Color.parseColor("#" + item.getColour());
        }
        rivBackground.setBackgroundColor(color);
        helper.getView(R.id.clFLRootLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHomeSeeMoreListener != null) {
                    onHomeSeeMoreListener.onFLGNItemClick(item);
                }
            }
        });
    }
}
