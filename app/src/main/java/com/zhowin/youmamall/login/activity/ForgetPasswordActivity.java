package com.zhowin.youmamall.login.activity;


import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.PhoneUtils;
import com.zhowin.base_library.utils.SPUtils;
import com.zhowin.base_library.utils.SplitUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityForgetPasswordBinding;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * 忘记密码 和 修改密码 共用
 * type: 1 忘记密码  2 修改密码
 */
public class ForgetPasswordActivity extends BaseBindActivity<ActivityForgetPasswordBinding> {

    private Disposable mdDisposable;
    private int classType;


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvGetVerificationCode, R.id.tvConfirmSubmission);
        classType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        mBinding.tvTitleView.setTitle(1 == classType ? "忘记密码" : "修改密码");
        if (2 == classType) {
            String mobile = UserInfo.getUserInfo().getMobile();
            mBinding.editMobile.setText(PhoneUtils.hitCenterMobilNumber(mobile));
            mBinding.editMobile.setEnabled(false);
            mBinding.editMobile.setFocusable(false);
        }
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGetVerificationCode:
                String mobile = mBinding.editMobile.getText().toString().trim();
                if (!RegexUtils.isMobileSimple(mobile)) {
                    ToastUtils.showToast("手机号格式不对");
                    return;
                }
                getVerificationCode(mobile);
                countdownTime();
                break;
            case R.id.tvConfirmSubmission:
                submitPasswordData();
                break;
        }
    }

    private void submitPasswordData() {
        String mobile;
        if (1 == classType) {
            mobile = mBinding.editMobile.getText().toString().trim();
            if (!RegexUtils.isMobileSimple(mobile)) {
                ToastUtils.showToast("手机号格式不对");
                return;
            }
        } else {
            mobile = UserInfo.getUserInfo().getMobile();
        }
        String editPassword = mBinding.editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(editPassword)) {
            ToastUtils.showToast("请输入您的密码");
            return;
        }
        String editPasswordAgain = mBinding.editPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(editPasswordAgain)) {
            ToastUtils.showToast("请确认您的密码");
            return;
        }
        if (!TextUtils.equals(editPassword, editPasswordAgain)) {
            ToastUtils.showToast("两次输入的密码不一致");
            return;
        }
        String captchaCode = mBinding.editVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(captchaCode)) {
            ToastUtils.showToast("请输入短信验证码");
            return;
        }
        showLoadDialog();
        HttpRequest.setResetPassword(this, mobile, editPassword, captchaCode, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                if (1 == classType) {
                    SPUtils.set(ConstantValue.REMEMBER_PASSWORD, false);
                    startActivity(LoginActivity.class);
                    ActivityManager.getAppInstance().finishActivity();
                } else {
                    UserInfo.setUserPassword(editPassword);
                    ToastUtils.showToast("修改成功");
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void getVerificationCode(String mobile) {
        showLoadDialog();
        HttpRequest.getVerificationCode(this, 1 == classType ? "register" : "resetpwd", mobile, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
                dismissLoadDialog();
            }
        });
    }

    /**
     * 倒计时
     */
    private void countdownTime() {
        final int count = 60;//倒计时60秒
        mdDisposable = Flowable.intervalRange(0, count, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mBinding.tvGetVerificationCode.setEnabled(false);
                        int countdownNumber = (int) (count - aLong);
                        String showText = countdownNumber + "s后重新获取";
                        SpannableString colorNumberText = SplitUtils.getTextColor(showText, 0, String.valueOf(countdownNumber).length(), mContext.getResources().getColor(R.color.white));
                        mBinding.tvGetVerificationCode.setText(colorNumberText);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //倒计时完毕置为可点击状态
                        mBinding.tvGetVerificationCode.setEnabled(true);
                        mBinding.tvGetVerificationCode.setText("再次获取");
                    }
                })
                .subscribe();
    }

    @Override
    public void onDestroy() {
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
        super.onDestroy();
    }

}
