package com.zhowin.youmamall.mine.activity;


import android.app.Dialog;
import android.view.View;

import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.callback.OnPasswordEditListener;
import com.zhowin.base_library.dialog.PasswordDialog;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SPUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityDepositBinding;
import com.zhowin.youmamall.home.activity.WebViewActivity;
import com.zhowin.youmamall.home.model.ConfirmOrderInfo;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.callback.OnSelectPaymentClickListener;
import com.zhowin.youmamall.mine.dialog.SelectPaymentTypeDialog;
import com.zhowin.youmamall.mine.model.DepositMessage;

/**
 * 缴纳保证金
 */
public class DepositActivity extends BaseBindActivity<ActivityDepositBinding> {

    private int paymentType = 1;


    @Override
    public int getLayoutId() {
        return R.layout.activity_deposit;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvPaymentType, R.id.tvConfirmSubmission);
    }

    @Override
    public void initData() {
        getDepositMessage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPaymentType:
                showPaymentDialog();
                break;
            case R.id.tvConfirmSubmission:
                if (1 == paymentType) {
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


    private void getDepositMessage() {
        showLoadDialog();
        HttpRequest.getDepositMessage(this, new HttpCallBack<DepositMessage>() {
            @Override
            public void onSuccess(DepositMessage depositMessage) {
                dismissLoadDialog();
                if (depositMessage != null) {
                    boolean isOpenMerchant = 1 == depositMessage.getIs_open_merchant();
                    SPUtils.set(ConstantValue.IS_OPEN_MERCHANT, isOpenMerchant);
                    mBinding.tvDepositMoney.setText(depositMessage.getApply_merchant_money());
                    mBinding.tvHitDepositMessage.setText(depositMessage.getBond_detail());
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void showPaymentDialog() {
        SelectPaymentTypeDialog paymentTypeDialog = SelectPaymentTypeDialog.newInstance(paymentType);
        paymentTypeDialog.show(getSupportFragmentManager(), "payment");
        paymentTypeDialog.setOnSelectPaymentClickListener(new OnSelectPaymentClickListener() {
            @Override
            public void onSelectPayment(int type) {
                paymentType = type;
                switch (type) {
                    case 1:
                        mBinding.tvPaymentType.setText("余额支付");
                        break;
                    case 2:
                        mBinding.tvPaymentType.setText("支付宝支付");
                        break;
                    case 3:
                        mBinding.tvPaymentType.setText("微信支付");
                        break;
                }
            }
        });
    }


    private void setCommissionPaymentPassword() {
        new CenterHitMessageDialog(mContext, "您尚未设置支付密码", new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {
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


    private void confirmOrder(String password) {
        showLoadDialog();
        HttpRequest.startDepositPayment(this, paymentType, password, new HttpCallBack<ConfirmOrderInfo>() {
            @Override
            public void onSuccess(ConfirmOrderInfo confirmOrderInfo) {
                dismissLoadDialog();
                switch (paymentType) {
                    case 1:
                        ToastUtils.showToast("支付成功");
                        break;
                    case 2: //支付宝支付
                    case 3://微信支付
                        String paymentUrl = confirmOrderInfo.getUrl();
                        String paymentTitle = 2 == paymentType ? "支付宝支付" : "微信支付";
                        WebViewActivity.start(mContext, paymentTitle, paymentUrl,true);
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
