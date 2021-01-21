package com.zhowin.youmamall.mine.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhowin.base_library.base.BaseDialogView;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.model.MyReferrerMessage;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author : zho
 * date  ：2021/1/21
 * desc ：我的推荐人
 */
public class MyReferrerDialog extends BaseDialogView {

    private CircleImageView civHead;
    private TextView tvNickName, tvMobile;
    private RoundedImageView ivWxCodeImage;


    public MyReferrerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_my_referrer_dialog_layout;
    }

    @Override
    public void initView() {
        civHead = get(R.id.civHead);
        tvNickName = get(R.id.tvNickName);
        tvMobile = get(R.id.tvMobile);
        ivWxCodeImage = get(R.id.ivWxCodeImage);
        get(R.id.tvDetermine).setOnClickListener(this::onViewClick);
    }

    public void setMyReferrerMessage(MyReferrerMessage message) {
        if (message != null && !TextUtils.isEmpty(message.getF_mobile())) {
            GlideUtils.loadUserPhotoForLogin(mContext, message.getF_avatar(), civHead);
            tvNickName.setText("昵称：" + message.getF_nickname());
            tvMobile.setText("手机：" + message.getF_mobile());
            GlideUtils.loadObjectImage(mContext, message.getF_wechat_qrcode(), ivWxCodeImage);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvDetermine:
                dismiss();
                break;
        }
    }
}
