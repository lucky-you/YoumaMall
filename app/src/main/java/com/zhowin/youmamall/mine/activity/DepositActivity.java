package com.zhowin.youmamall.mine.activity;


import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityDepositBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.callback.OnSelectPaymentClickListener;
import com.zhowin.youmamall.mine.dialog.SelectPaymentTypeDialog;
import com.zhowin.youmamall.mine.model.DepositMessage;
import com.zhowin.youmamall.wxapi.PaymentReqInfo;

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
                startDepositPayment();
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
        SelectPaymentTypeDialog paymentTypeDialog = SelectPaymentTypeDialog.newInstance(1);
        paymentTypeDialog.show(getSupportFragmentManager(), "payment");
        paymentTypeDialog.setOnSelectPaymentClickListener(new OnSelectPaymentClickListener() {
            @Override
            public void onSelectPayment(int type) {
                paymentType = type;
                ToastUtils.showToast("type:" + type);
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


    private void startDepositPayment() {
        showLoadDialog();
        HttpRequest.startDepositPayment(this, paymentType, "", new HttpCallBack<PaymentReqInfo>() {
            @Override
            public void onSuccess(PaymentReqInfo paymentReqInfo) {
                dismissLoadDialog();
                if (paymentReqInfo != null) {

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
