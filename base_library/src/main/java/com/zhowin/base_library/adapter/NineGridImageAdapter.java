package com.zhowin.base_library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.zhowin.base_library.callback.OnNineItemClickListener;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SizeUtils;

import java.util.List;

/**
 * author      : Z_B
 * function  : 九宫格图片的展示
 */
public class NineGridImageAdapter extends RecyclerView.Adapter<NineGridImageAdapter.ImageViewHolder> {

    private List<String> imageList;
    private Context mContent;
    private int itemType; // 1.圆角  2.直角
    private OnNineItemClickListener onNineItemClickListener;

    public NineGridImageAdapter(List<String> imageList, Context mContent, int type) {
        this.imageList = imageList;
        this.mContent = mContent;
        this.itemType = type;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
        notifyDataSetChanged();
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
        notifyDataSetChanged();
    }

    public void setOnNineItemClickListener(OnNineItemClickListener onNineItemClickListener) {
        this.onNineItemClickListener = onNineItemClickListener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = null;
        LinearLayout.LayoutParams layoutParams = null;
        switch (itemType) {
            case 1:
                RoundedImageView rImageView = new RoundedImageView(mContent);
                rImageView.setCornerRadius(SizeUtils.dp2px(4));
                imageView = rImageView;
                layoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(108), SizeUtils.dp2px(108));
                break;
            case 2:
                imageView = new ImageView(mContent);
                layoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(108), SizeUtils.dp2px(108));
                break;
        }
        layoutParams.setMargins(SizeUtils.dp2px(4), SizeUtils.dp2px(4), SizeUtils.dp2px(4), SizeUtils.dp2px(4));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(layoutParams);
        return new ImageViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        GlideUtils.loadObjectImage(mContent, imageList.get(position), holder.roundedImageView);
        holder.roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onNineItemClickListener != null) {
                    onNineItemClickListener.onNineItemClick(position, imageList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.isEmpty() ? 0 : imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView roundedImageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            roundedImageView = (ImageView) itemView;
        }
    }

}
