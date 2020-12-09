package com.zhowin.youmamall.home.activity;


import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.view.View;
import android.widget.RadioGroup;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SplitUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityConfirmOrderBinding;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mine.activity.MyCouponActivity;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseBindActivity<ActivityConfirmOrderBinding> implements RadioGroup.OnCheckedChangeListener {


    private GoodItem goodItem;

    public static void start(Context context, GoodItem goodItem) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra(ConstantValue.CONTNET, goodItem);
        context.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initView() {
        goodItem = getIntent().getParcelableExtra(ConstantValue.CONTNET);
        setOnClick(R.id.tvSubmitOrder, R.id.tvCouponText);
        mBinding.rgPaymentButton.setOnCheckedChangeListener(this::onCheckedChanged);
        if (goodItem != null) {
            GlideUtils.loadObjectImage(mContext, goodItem.getImage(), mBinding.ivLeftImage);
            mBinding.tvCardName.setText(goodItem.getName());
            mBinding.tvPrice.setText("¥" + goodItem.getPrice());

            String totalPrice = "合计: ¥" + goodItem.getPrice();
            SpannableString spannableText = SplitUtils.getTextColor(totalPrice, 4, totalPrice.length(), getBaseColor(R.color.color_E83219));
            mBinding.tvTotalAmount.setText(spannableText);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCouponText:
                MyCouponActivity.start(mContext, 2);
                break;
            case R.id.tvSubmitOrder:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbBalancePay:
                break;
            case R.id.rbWxPay:
                break;
            case R.id.rbZFBPay:
                break;
        }
    }
}
