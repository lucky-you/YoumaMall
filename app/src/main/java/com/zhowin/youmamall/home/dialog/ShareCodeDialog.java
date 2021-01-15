package com.zhowin.youmamall.home.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.ScreenUtils;
import com.zhowin.base_library.utils.SizeUtils;
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
            tvProductName.setText(goodDetailsInfo.getName());
            tvProductPrice.setText("¥" + goodDetailsInfo.getPrice());
            Bitmap bitmap = QRCodeUtils.createQRCode(goodDetailsInfo.getH5_url());
            ivShareQrCodeImage.setImageBitmap(bitmap);

            Glide.with(mContext).load(goodDetailsInfo.getImage()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    int screenWidth = ScreenUtils.getScreenWidth();
                    int imageWidth = resource.getIntrinsicWidth();
                    int imageHeight = resource.getIntrinsicHeight();
                    int height = ScreenUtils.getScreenWidth() + imageHeight / imageWidth;
//                    Log.e("xy", "screenWidth:" + screenWidth + "--imageWidth:" + imageWidth + "--imageHeight:" + imageHeight);
                    ViewGroup.LayoutParams para = ivContent.getLayoutParams();
                    int newWidth = SizeUtils.dp2px(280);
                    int newHeight = height;
                    para.width = newWidth;
                    para.height = newHeight;
//                    Log.e("xy", "height:" + height + "--newWidth:" + newWidth + "--newHeight:" + newHeight);
                    ivContent.setLayoutParams(para);
                    ivContent.setScaleType(ImageView.ScaleType.FIT_XY);
                    ivContent.setImageDrawable(resource);
                }
            });

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
