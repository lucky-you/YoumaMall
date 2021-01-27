package com.zhowin.youmamall.mine.activity;


import android.text.TextUtils;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.pickerview.OnSelectConditionsClickListener;
import com.zhowin.base_library.pickerview.PickerViewConditionsUtils;
import com.zhowin.base_library.pickerview.SelectPickerList;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.SpanUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.circle.utils.UserLevelHelper;
import com.zhowin.youmamall.databinding.ActivityWithdrawBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.model.AccountMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 佣金提现
 */
public class WithdrawActivity extends BaseBindActivity<ActivityWithdrawBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvWithdrawType, R.id.tvConfirmSubmission);
        SpanUtils.with(mBinding.tvHitMessage)
                .appendLine("温馨提示：").setBold()
                .appendLine()
                .appendLine("1.不限次数，不限金额，单笔提现费2元；")
                .appendLine("2.请正确填写支付宝账号和姓名，避免造成提现不到账；")
                .appendLine("3.提现时间：周一至周五9:00-17:30，提现当天到账。非工作日内提现，将由下一个工作日到账。")
                .create();
    }

    @Override
    public void initData() {
        AccountMessage accountMessage = AccountMessage.getUserAccountMessage();
        if (accountMessage != null) {
            if (!TextUtils.isEmpty(accountMessage.getAccountNumber())) {
                mBinding.editAccount.setText(accountMessage.getAccountNumber());
                mBinding.editAccount.setSelection(accountMessage.getAccountNumber().length());
            }
            if (!TextUtils.isEmpty(accountMessage.getUserName())) {
                mBinding.editUserName.setText(accountMessage.getUserName());
                mBinding.editUserName.setSelection(accountMessage.getUserName().length());
            }
        }
        getUserInfoMessage();
    }

    private void getUserInfoMessage() {
        showLoadDialog();
        HttpRequest.getUserInfoMessage(this, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                dismissLoadDialog();
                if (userInfo != null) {
                    mBinding.tvBalance.setText(userInfo.getMoney());
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
            case R.id.tvWithdrawType:
                break;
            case R.id.tvConfirmSubmission:
                startApplyWithdraw();
                break;
        }
    }

    private void startApplyWithdraw() {
        String applyMoney = mBinding.editWithdrawMoney.getText().toString().trim();
        String applyAccount = mBinding.editAccount.getText().toString().trim();
        String applyUserName = mBinding.editUserName.getText().toString().trim();
        String applyUserPassword = mBinding.editPayPassword.getText().toString().trim();
        if (TextUtils.isEmpty(applyMoney)) {
            ToastUtils.showToast("请输入提现金额");
            return;
        }
        if (TextUtils.isEmpty(applyAccount)) {
            ToastUtils.showToast("请输入提现账号");
            return;
        }
        if (TextUtils.isEmpty(applyUserName)) {
            ToastUtils.showToast("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(applyUserPassword)) {
            ToastUtils.showToast("请输入支付密码");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("money", applyMoney);
        map.put("account", applyAccount);
        map.put("type", "1");
        map.put("realname", applyUserName);
        map.put("pay_password", applyUserPassword);
        showLoadDialog();
        HttpRequest.applyWithdraw(this, map, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                AccountMessage accountMessage = new AccountMessage(applyAccount, applyUserName);
                AccountMessage.setUserAccountMessage(accountMessage);
                ToastUtils.showCustomToast(mContext, "申请成功，请等待审核");
                ActivityManager.getAppInstance().finishActivity();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

}
