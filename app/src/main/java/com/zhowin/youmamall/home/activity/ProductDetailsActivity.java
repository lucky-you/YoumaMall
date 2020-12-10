package com.zhowin.youmamall.home.activity;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SpanUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityProductDetailsBinding;
import com.zhowin.youmamall.home.model.GoodDetailsInfo;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.GoodItem;

/**
 * 商品详情
 */
public class ProductDetailsActivity extends BaseBindActivity<ActivityProductDetailsBinding> {

    private int goodId;
    private GoodItem goodItem = new GoodItem();

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
        setOnClick(R.id.tvBuyNow);
        SpanUtils.with(mBinding.tvPurchaseNotes)
                .appendLine("购买须知").setForegroundColor(getBaseColor(R.color.color_E83219)).setBold()
                .appendLine("1.请先复制链接到浏览器打开下载好App,查看视频教程。确保是自己需要的辅助再进行下单")
                .appendLine("2.所有软件功能问题最终以安装后实际功能为准在购买先行查阅教程等教材后下单购买")
                .appendLine("3.已购买的用户如调软件查村，开发波抓跑路服务等不可抗柜的因素不退换")
                .appendLine("4.手机剧机恢复出厂设置系统升级个人行为操作导散软件无法使用的，不在售后范围内")
                .appendLine("5.既然选择了方便就要自己承担一切不可预测的风险早竟是第三方插件。请知晓以上种种")
                .create();

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
                    goodItem.setId(goodDetailsInfo.getId());
                    goodItem.setName(goodDetailsInfo.getName());
                    goodItem.setImage(goodDetailsInfo.getImage());
                    goodItem.setPrice(goodDetailsInfo.getPrice());
                    GlideUtils.loadObjectImage(mContext, goodDetailsInfo.getImage(), mBinding.ivGoodImage);
                    mBinding.tvProductName.setText(goodDetailsInfo.getName());
                    mBinding.tvNumberOfPayments.setText(goodDetailsInfo.getSale() + "人已付款");
                    mBinding.tvProductPrice.setText("¥" + goodDetailsInfo.getPrice());
                    mBinding.tvCommissionPrice.setText("佣金" + goodDetailsInfo.getCommission_money() + "元");
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
            case R.id.tvBuyNow:
                ConfirmOrderActivity.start(mContext, goodItem);
                break;
        }
    }
}
