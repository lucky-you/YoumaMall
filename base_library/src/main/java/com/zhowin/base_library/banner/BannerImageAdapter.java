package com.zhowin.base_library.banner;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.adapter.BannerAdapter;
import com.zhowin.base_library.R;
import com.zhowin.base_library.callback.OnBannerItemClickListener;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SizeUtils;

import java.util.List;

/**
 * author Z_B
 * date :2020/5/18 12:08
 * description: 加载图片的banner
 */
public class BannerImageAdapter extends BannerAdapter<BannerList, BannerImageAdapter.ViewHolder> {

    protected int imageType;
    private OnBannerItemClickListener onBannerItemClickListener;

    public BannerImageAdapter(List<BannerList> dates, int type) {
        super(dates);
        this.imageType = type;
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
        this.onBannerItemClickListener = onBannerItemClickListener;
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        if (imageType == 1) {
            ImageView imageView = new ImageView(parent.getContext());
            //注意，必须设置为match_parent，这个是viewpager2强制要求的
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setBackgroundColor(parent.getResources().getColor(R.color.white));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new ViewHolder(imageView);
        } else {
            RoundedImageView imageView = new RoundedImageView(parent.getContext());
            imageView.setCornerRadius(SizeUtils.dp2px(4));
            //注意，必须设置为match_parent，这个是viewpager2强制要求的
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new ViewHolder(imageView);
        }
    }

    @Override
    public void onBindView(ViewHolder holder, BannerList data, int position, int size) {
        if (!TextUtils.isEmpty(data.getImage()))
            GlideUtils.loadObjectImage(holder.imageView.getContext(), data.getImage(), holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBannerItemClickListener != null) {
                    onBannerItemClickListener.onBannerClick(data);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView;
        }
    }

}
