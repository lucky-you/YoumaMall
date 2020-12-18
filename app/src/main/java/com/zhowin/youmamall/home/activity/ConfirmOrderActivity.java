package com.zhowin.youmamall.home.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.view.View;
import android.widget.RadioGroup;

import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.callback.OnPasswordEditListener;
import com.zhowin.base_library.dialog.PasswordDialog;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SplitUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.base_library.view.PasswordEditText;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityConfirmOrderBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mine.activity.MallOrderListActivity;
import com.zhowin.youmamall.mine.activity.MyCouponActivity;
import com.zhowin.youmamall.mine.activity.SetPasswordActivity;
import com.zhowin.youmamall.wxapi.PaymentReqInfo;

/**
 * 确认订单
 */
public class ConfirmOrderActivity extends BaseBindActivity<ActivityConfirmOrderBinding> implements RadioGroup.OnCheckedChangeListener {


    private GoodItem goodItem;
    private int payType = 1;

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
                if (1 == payType) {
                    int isSetPassword = UserInfo.getUserInfo().getIs_pay_pwd();
                    if (1 == isSetPassword) {
                        showPayPasswordDialog();
                    } else {
                        setCommissionPaymentPassword();
                    }
                } else {
                    confirmOrder("");
                }
                break;
        }
    }

    private void setCommissionPaymentPassword() {
        new CenterHitMessageDialog(mContext, "您尚未设置支付密码", new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {
                showPayPasswordDialog();
            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                SetPasswordActivity.start(mContext, 0);
            }
        }).setNegativeButton("再想想")
                .setPositiveButton("去设置")
                .show();
    }

    private void showPayPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(mContext);
        dialog.setOnPasswordEditListener(new OnPasswordEditListener() {
            @Override
            public void onCancelPayment() {

            }

            @Override
            public void onDeterminePayment(String password) {
                confirmOrder(password);
            }
        });
        dialog.show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbBalancePay:
                payType = 1;
                break;
            case R.id.rbWxPay:
                payType = 3;
                break;
            case R.id.rbZFBPay:
                payType = 2;
                break;
        }
    }

    private void confirmOrder(String password) {
        showLoadDialog();
        HttpRequest.confirmOrder(this, goodItem.getId(), payType, password, new HttpCallBack<PaymentReqInfo>() {
            @Override
            public void onSuccess(PaymentReqInfo o) {
                dismissLoadDialog();
                switch (payType) {
                    case 1:
                        MallOrderListActivity.start(mContext, 0);
                        break;
                    case 2: //支付宝支付
                        break;
                    case 3://微信支付
                        break;
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
