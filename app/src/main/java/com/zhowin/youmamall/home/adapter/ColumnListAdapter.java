package com.zhowin.youmamall.home.adapter;

import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.ColumnList;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：栏目分类adapter
 */
public class ColumnListAdapter extends BaseQuickAdapter<ColumnList, BaseViewHolder> {
    private int adapterType;

    public ColumnListAdapter(@Nullable List<ColumnList> data, int type) {
        super(R.layout.include_column_item_view, data);
        this.adapterType = type;
    }

    public void setAdapterType(int adapterType) {
        this.adapterType = adapterType;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ColumnList item) {
        CircleImageView civColumn = helper.getView(R.id.civColumn);
        LinearLayout llColumnRootLayout = helper.getView(R.id.llColumnRootLayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams fLayoutParams;
        switch (adapterType) {
            case 1:
                fLayoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(45), SizeUtils.dp2px(45));
                layoutParams.setMargins(SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(10));
                break;
            case 2:
                civColumn.setImageResource(item.getDrawable());
                fLayoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(40), SizeUtils.dp2px(40));
                layoutParams.setMargins(SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8), SizeUtils.dp2px(8));
                layoutParams.gravity = Gravity.CENTER;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + adapterType);
        }
        llColumnRootLayout.setLayoutParams(layoutParams);
        fLayoutParams.gravity = Gravity.CENTER;
        civColumn.setLayoutParams(fLayoutParams);
        helper.setText(R.id.tvColumnTitle, item.getTitle());
    }
}
