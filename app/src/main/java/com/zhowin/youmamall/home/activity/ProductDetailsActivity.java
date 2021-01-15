package com.zhowin.youmamall.home.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.BitmapUtils;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SpanUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityProductDetailsBinding;
import com.zhowin.youmamall.home.callback.OnShareCodeListener;
import com.zhowin.youmamall.home.dialog.ShareCodeDialog;
import com.zhowin.youmamall.home.model.GoodDetailsInfo;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.GoodItem;

import java.io.File;

/**
 * 商品详情
 */
public class ProductDetailsActivity extends BaseBindActivity<ActivityProductDetailsBinding> {

    private int goodId, categoryId;
    private String categoryName;
    private GoodItem goodItem = new GoodItem();
    private GoodDetailsInfo goodDetail;
    private boolean isHideShareButton;

    public static void start(Context context, int goodId, boolean isHideShare) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(ConstantValue.ID, goodId);
        intent.putExtra(ConstantValue.TYPE, isHideShare);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initView() {
        goodId = getIntent().getIntExtra(ConstantValue.ID, -1);
        isHideShareButton = getIntent().getBooleanExtra(ConstantValue.TYPE, false);
        setOnClick(R.id.tvBuyNow, R.id.tvShareCode, R.id.llShopMallLayout, R.id.llShareCodeLayout);
        mBinding.tvShareCode.setVisibility(isHideShareButton ? View.GONE : View.VISIBLE);
    }

    @Override
    public void initData() {
        getGoodDetails();
    }

    private void getGoodDetails() {
        showLoadDialog();
        HttpRequest.getGoodDetails(this, goodId, new HttpCallBack<GoodDetailsInfo>() {
            @Override
            public void onSuccess(GoodDetailsInfo goodDetailsInfo) {
                dismissLoadDialog();
                if (goodDetailsInfo != null) {
                    goodDetail = goodDetailsInfo;
                    categoryName = goodDetailsInfo.getCategory_name();
                    categoryId = goodDetailsInfo.getShop_category_id();
                    goodItem.setId(goodDetailsInfo.getId());
                    goodItem.setName(goodDetailsInfo.getName());
                    goodItem.setImage(goodDetailsInfo.getImage());
                    goodItem.setPrice(goodDetailsInfo.getPrice());
                    GlideUtils.loadObjectImage(mContext, goodDetailsInfo.getImage(), mBinding.ivGoodImage);
                    mBinding.tvProductName.setText(goodDetailsInfo.getName());
                    mBinding.tvNumberOfPayments.setText(goodDetailsInfo.getSale() + "人已付款");
                    mBinding.tvProductPrice.setText("¥" + goodDetailsInfo.getPrice());
                    mBinding.tvCommissionPrice.setText("佣金" + goodDetailsInfo.getCommission_money() + "元");
//                    mBinding.tvProductLink.setText(goodDetailsInfo.getContent());
                    mBinding.tvProductLink.setText(Html.fromHtml(goodDetailsInfo.getContent()));
                    String goodDescription = goodDetailsInfo.getPay_description();
                    if (!TextUtils.isEmpty(goodDescription)) {
                        SpanUtils.with(mBinding.tvPurchaseNotes)
                                .appendLine("购买须知").setForegroundColor(getBaseColor(R.color.color_E83219)).setBold()
                                .appendLine(goodDescription)
                                .create();
                    }
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llShopMallLayout:
                ColumnListActivity.start(mContext, 1, categoryName, categoryId, false);
                break;
            case R.id.tvShareCode:
            case R.id.llShareCodeLayout:
                if (!isLogin())
                    showShareDialog();
                break;
            case R.id.tvBuyNow:
                if (!isLogin())
                    ConfirmOrderActivity.start(mContext, goodItem);
                break;
        }
    }

    private void showShareDialog() {
        ShareCodeDialog shareCodeDialog = new ShareCodeDialog(mContext);
        shareCodeDialog.setShareCodeContent(goodDetail);
        shareCodeDialog.show();
        shareCodeDialog.setOnShareCodeListener(new OnShareCodeListener() {
            @Override
            public void onSaveImage(View llQrCodeLayout) {
                requestPermission(llQrCodeLayout);
            }
        });
    }

    private void requestPermission(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .callback(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            SaveToAlbum(view);
                        }

                        @Override
                        public void onDenied() {
                        }
                    }).request();
        }
    }


    private void SaveToAlbum(View llQrCodeLayout) {
        Bitmap bitmapSrc = BitmapUtils.getCacheBitmapFromView(llQrCodeLayout);
        ThreadUtils.executeBySingle(new ThreadUtils.SimpleTask<File>() {
            @Override
            public File doInBackground() throws Throwable {
                return ImageUtils.save2Album(bitmapSrc, Bitmap.CompressFormat.JPEG);
            }

            @Override
            public void onSuccess(File result) {
                if (result != null) {
                    ToastUtils.showToast("保存成功");
                } else {
                    ToastUtils.showToast("保存失败");
                }
            }
        });
    }
}
