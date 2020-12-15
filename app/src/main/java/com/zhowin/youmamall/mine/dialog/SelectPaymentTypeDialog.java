package com.zhowin.youmamall.mine.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhowin.base_library.base.BaseDialogFragment;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.callback.OnSelectPaymentClickListener;

/**
 * author : zho
 * date  ：2020/12/14
 * desc ：选择支付方式的dialog
 */
public class SelectPaymentTypeDialog extends BaseDialogFragment implements RadioGroup.OnCheckedChangeListener {

    private int payType;
    private RadioButton rbBalancePay, rbZFBPay, rbWxPay;
    private OnSelectPaymentClickListener onSelectPaymentClickListener;

    public static SelectPaymentTypeDialog newInstance(int type) {
        SelectPaymentTypeDialog selectPaymentTypeDialog = new SelectPaymentTypeDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.TYPE, type);
        selectPaymentTypeDialog.setArguments(bundle);
        return selectPaymentTypeDialog;
    }


    @Override
    public int getLayoutId() {
        return R.layout.include_select_payment_type_dialog;
    }


    public void setOnSelectPaymentClickListener(OnSelectPaymentClickListener onSelectPaymentClickListener) {
        this.onSelectPaymentClickListener = onSelectPaymentClickListener;
    }

    @Override
    public void initView() {
        payType = getArguments().getInt(ConstantValue.TYPE);
        Log.e("xy", "payType:" + payType);
        get(R.id.tvPayment).setOnClickListener(this::onViewClick);
        RadioGroup radioGroup = get(R.id.rgPaymentButton);
        rbBalancePay = get(R.id.rbBalancePay);
        rbZFBPay = get(R.id.rbZFBPay);
        rbWxPay = get(R.id.rbWxPay);
        switch (payType) {
            case 1:
                radioGroup.check(R.id.rbBalancePay);
                break;
            case 2:
                radioGroup.check(R.id.rbZFBPay);
                break;
            case 3:
                radioGroup.check(R.id.rbWxPay);
                break;
        }
        radioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvPayment:

                break;
        }
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
        if (onSelectPaymentClickListener != null) {
            onSelectPaymentClickListener.onSelectPayment(payType);
        }
        dismiss();
    }
}
