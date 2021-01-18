package com.zhowin.youmamall.dynamic.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.circle.adapter.ImageAdapter;
import com.zhowin.youmamall.circle.callback.OnGridImageClickListener;
import com.zhowin.youmamall.dynamic.callback.OnDynamicItemClickListener;
import com.zhowin.youmamall.dynamic.model.DynamicList;

import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：动态列表
 */
public class DynamicFragmentAdapter extends BaseQuickAdapter<DynamicList, BaseViewHolder> {
    public DynamicFragmentAdapter(@Nullable List<DynamicList> data) {
        super(R.layout.include_dynamic_fragment_item_view, data);
    }

    private OnDynamicItemClickListener onDynamicItemClickListener;

    public void setOnDynamicItemClickListener(OnDynamicItemClickListener onDynamicItemClickListener) {
        this.onDynamicItemClickListener = onDynamicItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DynamicList item) {

        GlideUtils.loadUserPhotoForLogin(mContext, item.getAvatar(), helper.getView(R.id.civUserHead));
        helper.setText(R.id.tvDynamicTitle, item.getTitle())
                .setText(R.id.tvDynamicContent, item.getContent())
                .setText(R.id.tvCreateTime, DateHelpUtils.getPostDetailTime(item.getCreatetime()))
                .setGone(R.id.tvSavePhoto, item.getImages() != null && !item.getImages().isEmpty());
        RecyclerView imageRecyclerView = helper.getView(R.id.imageRecyclerView);
        imageRecyclerView.setVisibility(item.getImages() != null && !item.getImages().isEmpty() ? View.VISIBLE : View.GONE);
        ImageAdapter imageAdapter = new ImageAdapter();
        imageAdapter.updateData(item.getImages());
        imageRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        imageRecyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnGridImageClickListener(new OnGridImageClickListener() {
            @Override
            public void onItemImageClick(List<String> imageList, int position) {
                if (onDynamicItemClickListener != null) {
                    onDynamicItemClickListener.onImageItemClick(imageList, position);
                }
            }
        });
        helper.getView(R.id.tvSavePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDynamicItemClickListener != null) {
                    onDynamicItemClickListener.onSavePhoto(item.getImages());
                }
            }
        });
        helper.getView(R.id.tvCopyContent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDynamicItemClickListener != null) {
                    onDynamicItemClickListener.onCopyContent(item.getContent());
                }
            }
        });
    }
}
