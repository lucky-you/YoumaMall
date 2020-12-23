package com.zhowin.youmamall.home.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.callback.OnShareCodeListener;
import com.zhowin.youmamall.home.model.GoodDetailsInfo;
import com.zhowin.youmamall.home.utils.QRCodeUtils;

/**
 * author : zho
 * date  ：2020/12/23
 * desc ：推广二维码
 */
public class ShareCodeDialog extends BaseDialogView {

    private ImageView ivContent, ivShareQrCodeImage;
    private TextView tvProductName, tvProductPrice;
    private LinearLayout llQrCodeLayout;
    private OnShareCodeListener onShareCodeListener;


    public ShareCodeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_share_code_dialog;
    }

    @Override
    public void initView() {
        ivContent = get(R.id.ivContent);
        ivShareQrCodeImage = get(R.id.ivShareQrCodeImage);
        tvProductName = get(R.id.tvProductName);
        tvProductPrice = get(R.id.tvProductPrice);
        llQrCodeLayout = get(R.id.llQrCodeLayout);
        get(R.id.tvCancel).setOnClickListener(this::onViewClick);
        get(R.id.tvDetermine).setOnClickListener(this::onViewClick);
    }

    @Override
    public void initData() {

    }

    public void setShareCodeContent(GoodDetailsInfo goodDetailsInfo) {
        if (goodDetailsInfo != null) {
            GlideUtils.loadObjectImage(mContext, goodDetailsInfo.getImage(), ivContent);
            tvProductName.setText(goodDetailsInfo.getName());
            tvProductPrice.setText("¥" + goodDetailsInfo.getPrice());
            Bitmap bitmap = QRCodeUtils.createQRCode(goodDetailsInfo.getH5_url());
            ivShareQrCodeImage.setImageBitmap(bitmap);
        }
    }

    public void setOnShareCodeListener(OnShareCodeListener onShareCodeListener) {
        this.onShareCodeListener = onShareCodeListener;
    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                break;
            case R.id.tvDetermine:
                if (onShareCodeListener != null) {
                    onShareCodeListener.onSaveImage(llQrCodeLayout);
                }
                break;
        }
        dismiss();
    }
}
