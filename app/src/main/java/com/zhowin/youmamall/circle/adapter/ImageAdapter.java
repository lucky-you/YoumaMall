package com.zhowin.youmamall.circle.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.youmamall.circle.callback.OnGridImageClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义布局，图片
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private List<String> mDatas = new ArrayList<>();
    private OnGridImageClickListener onGridImageClickListener;

    //更新数据
    public void updateData(List<String> data) {
        this.mDatas = data == null ? new ArrayList<String>() : data;
        notifyDataSetChanged();
    }

    public void setOnGridImageClickListener(OnGridImageClickListener onGridImageClickListener) {
        this.onGridImageClickListener = onGridImageClickListener;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RoundedImageView imageView = new RoundedImageView(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                SizeUtils.dp2px(112), SizeUtils.dp2px(130));
        params.setMargins(SizeUtils.dp2px(4), SizeUtils.dp2px(4), SizeUtils.dp2px(4), SizeUtils.dp2px(4));
        imageView.setCornerRadius(SizeUtils.dp2px(4));
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        GlideUtils.loadObjectImage(holder.imageView.getContext(), mDatas.get(position), holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGridImageClickListener != null) {
                    onGridImageClickListener.onItemImageClick(mDatas, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.isEmpty() ? 0 : mDatas.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view;
        }
    }
}
