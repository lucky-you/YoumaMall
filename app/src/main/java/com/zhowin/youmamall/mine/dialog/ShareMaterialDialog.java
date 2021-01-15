package com.zhowin.youmamall.mine.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.utils.QRCodeUtils;
import com.zhowin.youmamall.mine.callback.OnShareMaterialListener;

/**
 * author : zho
 * date  ：2020/12/1
 * desc ：分享素材的dialog
 */
public class ShareMaterialDialog extends BaseDialogView {


    private ConstraintLayout clShareRootLayout;
    private ImageView ivContent, ivQrImage;
    private OnShareMaterialListener onShareMaterialListener;

    public ShareMaterialDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_share_materia_dialog;
    }

    @Override
    public void initView() {
        clShareRootLayout = get(R.id.clShareRootLayout);
        ivContent = get(R.id.ivContent);
        ivQrImage = get(R.id.ivQrImage);
        get(R.id.tvCancel).setOnClickListener(this::onViewClick);
        get(R.id.tvDetermine).setOnClickListener(this::onViewClick);
    }

    public void setImageUrl(String imageUrl, String qrCodeUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            GlideUtils.loadObjectImage(mContext, imageUrl, ivContent);
            Bitmap qrBitmap = QRCodeUtils.createQRCode(qrCodeUrl);
            ivQrImage.setImageBitmap(qrBitmap);
        }
    }

    @Override
    public void initData() {

    }

    public void setOnShareMaterialListener(OnShareMaterialListener onShareMaterialListener) {
        this.onShareMaterialListener = onShareMaterialListener;
    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                break;
            case R.id.tvDetermine:
                if (onShareMaterialListener != null) {
                    onShareMaterialListener.onStartShare(clShareRootLayout);
                }
                break;
        }
        dismiss();
    }
}
