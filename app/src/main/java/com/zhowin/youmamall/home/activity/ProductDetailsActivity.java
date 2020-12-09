package com.zhowin.youmamall.home.activity;


import android.content.Context;
import android.content.Intent;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityProductDetailsBinding;
import com.zhowin.youmamall.home.model.GoodDetailsInfo;
import com.zhowin.youmamall.http.HttpRequest;

/**
 * 商品详情
 */
public class ProductDetailsActivity extends BaseBindActivity<ActivityProductDetailsBinding> {

    private int goodId;

    public static void start(Context context, int goodId) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(ConstantValue.ID, goodId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initView() {
        goodId = getIntent().getIntExtra(ConstantValue.ID, -1);
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

                }

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
