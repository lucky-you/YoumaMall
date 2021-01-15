package com.zhowin.youmamall.mine.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.utils.QRCodeUtils;
import com.zhowin.youmamall.mine.model.ShareMaterialList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/1
 * desc ： 分享素材
 */
public class ShareMaterialAdapter extends BaseQuickAdapter<ShareMaterialList, BaseViewHolder> {


    public String shareUrl;


    public ShareMaterialAdapter(@Nullable List<ShareMaterialList> data) {
        super(R.layout.include_share_materia_item_view, data);
    }


    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShareMaterialList item) {
//        Log.e("xy", "imageUrl:" + item.getImage() + "--shareUrl:" + shareUrl);
        Bitmap qrBitmap = QRCodeUtils.createQRCode(shareUrl);
        ImageView ivQrImage = helper.getView(R.id.ivQrImage);
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivTopQrCode));
        ivQrImage.setImageBitmap(qrBitmap);
    }
}
