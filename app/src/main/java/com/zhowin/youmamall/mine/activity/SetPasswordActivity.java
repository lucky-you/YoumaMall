package com.zhowin.youmamall.mine.activity;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivitySetPasswordBinding;
import com.zhowin.youmamall.http.HttpRequest;

import org.apache.http.params.HttpParams;

/**
 * 设置/修改 支付密码
 */
public class SetPasswordActivity extends BaseBindActivity<ActivitySetPasswordBinding> {


    private int classType;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, SetPasswordActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_password;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvConfirmSubmission);
        classType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        mBinding.tvTitleView.setTitle(1 == classType ? "修改密码" : "设置密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvConfirmSubmission:
                setPayPassword();
                break;
        }
    }

    private void setPayPassword() {
        String loginPassword = mBinding.editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(loginPassword)) {
            ToastUtils.showToast("请输入您的登录密码");
            return;
        }
        String editPayPassword = mBinding.editPayPassword.getText().toString().trim();
        if (TextUtils.isEmpty(editPayPassword)) {
            ToastUtils.showToast("请设置支付密码");
            return;
        }
        String editPayPasswordAgain = mBinding.editPayPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(editPayPasswordAgain)) {
            ToastUtils.showToast("请确认支付密码");
            return;
        }
        if (!TextUtils.equals(editPayPassword, editPayPasswordAgain)) {
            ToastUtils.showToast("两次输入的密码不一致");
            return;
        }
        showLoadDialog();
        HttpRequest.setPayPassword(this, loginPassword, editPayPassword, editPayPasswordAgain, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                ToastUtils.showToast("操作成功");
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
