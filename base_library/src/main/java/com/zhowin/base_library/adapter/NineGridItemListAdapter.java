package com.zhowin.base_library.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.zhowin.base_library.R;
import com.zhowin.base_library.callback.OnNineGridItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author Z_B
 * date :2020/5/16 10:14
 * description: 九宫格选择图片和视频的adapter
 */
public class NineGridItemListAdapter extends RecyclerView.Adapter<NineGridItemListAdapter.ViewHolder> {

    public static final String TAG = "xy";
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private int selectMax = 9;
    private Context mContext;
    //点击添加图片跳转
    //item 的点击事件
    private OnNineGridItemClickListener onNineGridItemClickListener;

    public NineGridItemListAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }


    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setNewDataList(List<LocalMedia> list) {
        this.localMediaList = list;
        notifyDataSetChanged();
    }

    public void setOnNineGridItemClickListener(OnNineGridItemClickListener onNineGridItemClickListener) {
        this.onNineGridItemClickListener = onNineGridItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.include_nine_grid_item_view_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.ivSelectImage.setImageResource(R.mipmap.icon_add_pic);
            viewHolder.ivSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNineGridItemClickListener != null) {
                        onNineGridItemClickListener.onAddPictureClick();
                    }
                }
            });
            viewHolder.ivDeleteImage.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.ivDeleteImage.setVisibility(View.VISIBLE);
            viewHolder.ivDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        localMediaList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, localMediaList.size());
                        if (onNineGridItemClickListener != null) {
                            onNineGridItemClickListener.onItemClickDelete(position, localMediaList);
                        }
                    }
                }
            });
            LocalMedia media = localMediaList.get(position);
            if (media == null
                    || TextUtils.isEmpty(media.getPath())) {
                return;
            }
            int chooseModel = media.getChooseModel();
            String path;
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
//            Log.i(TAG, "原图地址::" + media.getPath());
//            if (media.isCut()) {
//                Log.i(TAG, "裁剪地址::" + media.getCutPath());
//            }
//            if (media.isCompressed()) {
//                Log.i(TAG, "压缩地址::" + media.getCompressPath());
//                Log.i(TAG, "压缩后文件大小::" + new File(media.getCompressPath()).length() / 1024 + "k");
//            }
//            if (!TextUtils.isEmpty(media.getAndroidQToPath())) {
//                Log.i(TAG, "Android Q特有地址::" + media.getAndroidQToPath());
//            }
//            if (media.isOriginal()) {
//                Log.i(TAG, "是否开启原图功能::" + true);
//                Log.i(TAG, "开启原图功能后地址::" + media.getOriginalPath());
//            }
            long duration = media.getDuration();
            viewHolder.tvDuration.setVisibility(PictureMimeType.isHasVideo(media.getMimeType())
                    ? View.VISIBLE : View.GONE);
            if (chooseModel == PictureMimeType.ofAudio()) {
                viewHolder.tvDuration.setVisibility(View.VISIBLE);
                viewHolder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.picture_icon_audio, 0, 0, 0);
            } else {
                viewHolder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                        (R.drawable.picture_icon_video, 0, 0, 0);
            }
            viewHolder.tvDuration.setText(DateUtils.formatDurationTime(duration));
            if (chooseModel == PictureMimeType.ofAudio()) {
                viewHolder.ivSelectImage.setImageResource(R.drawable.picture_audio_placeholder);
            } else {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_def_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(mContext)
                        .load(PictureMimeType.isContent(path) && !media.isCut() && !media.isCompressed() ? Uri.parse(path) : path)
                        .apply(options)
                        .into(viewHolder.ivSelectImage);
            }
            //itemView 的点击事件
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    if (onNineGridItemClickListener != null) {
                        onNineGridItemClickListener.onItemClick(adapterPosition, view);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (localMediaList.size() < selectMax) {
            return localMediaList.size() + 1;
        } else {
            return localMediaList.size();
        }
    }

    private boolean isShowAddItem(int position) {
        int size = localMediaList.isEmpty() ? 0 : localMediaList.size();
        return position == size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivSelectImage, ivDeleteImage;
        TextView tvDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSelectImage = itemView.findViewById(R.id.ivSelectImage);
            ivDeleteImage = itemView.findViewById(R.id.ivDeleteImage);
            tvDuration = itemView.findViewById(R.id.tvDuration);
        }
    }

    public List<LocalMedia> getLocalMediaList() {
        return localMediaList;
    }
}
