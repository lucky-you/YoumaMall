package com.zhowin.youmamall.circle.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.circle.callback.OnCircleItemClickListener;
import com.zhowin.youmamall.circle.callback.OnGridImageClickListener;
import com.zhowin.youmamall.circle.model.CircleList;

import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：
 */
public class CircleFragmentAdapter extends BaseQuickAdapter<CircleList, BaseViewHolder> {
    public CircleFragmentAdapter(@Nullable List<CircleList> data) {
        super(R.layout.include_circle_fragment_item_view, data);
    }


    private OnCircleItemClickListener onCircleItemClickListener;

    public void setOnCircleItemClickListener(OnCircleItemClickListener onCircleItemClickListener) {
        this.onCircleItemClickListener = onCircleItemClickListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CircleList item) {

        GlideUtils.loadUserPhotoForLogin(mContext, item.getAvatar(), helper.getView(R.id.civUserHead));
        helper.setText(R.id.tvUserNickName, item.getNickname())
                .setText(R.id.tvDescContent, item.getContent())
                .setText(R.id.tvCreateTime, DateHelpUtils.getPostDetailTime(item.getCreatetime()));

        RecyclerView imageRecyclerView = helper.getView(R.id.imageRecyclerView);
        imageRecyclerView.setVisibility(item.getImages() != null && !item.getImages().isEmpty() ? View.VISIBLE : View.GONE);

        ImageAdapter imageAdapter = new ImageAdapter();
        imageAdapter.updateData(item.getImages());
        imageRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        imageRecyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnGridImageClickListener(new OnGridImageClickListener() {
            @Override
            public void onItemImageClick(List<String> imageList, int position) {
                if (onCircleItemClickListener != null) {
                    onCircleItemClickListener.onImageItemClick(imageList, position);
                }
            }
        });
    }
}
