package com.zhowin.youmamall.mine.activity;


import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;

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
import com.zhowin.youmamall.databinding.ActivityChangeMobileBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.login.activity.LoginActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ChangeMobileActivity extends BaseBindActivity<ActivityChangeMobileBinding> {

    private Disposable mdDisposable;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_mobile;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvGetVerificationCode, R.id.tvConfirmSubmission);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvGetVerificationCode:
                String mobile = mBinding.editMobile.getText().toString().trim();
                if (!PhoneUtils.checkPhone(mobile, true)) {
                    return;
                }
                getVerificationCode(mobile);
                countdownTime();
                break;
            case R.id.tvConfirmSubmission:
                changeMobile();
                break;
        }
    }

    private void changeMobile() {
        String password = mBinding.editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast("请输入您的登录密码");
            return;
        }
        String mobile = mBinding.editMobile.getText().toString().trim();
        if (!PhoneUtils.checkPhone(mobile, true)) {
            return;
        }
        String captchaCode = mBinding.editVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(captchaCode)) {
            ToastUtils.showToast("请输入短信验证码");
            return;
        }
        showLoadDialog();
        HttpRequest.changUserMobile(this, password, mobile, captchaCode, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                UserInfo.setUserInfo(new UserInfo());
                SPUtils.set(ConstantValue.REMEMBER_PASSWORD, false);
                startActivity(LoginActivity.class);
                ActivityManager.getAppInstance().finishActivity();
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
        HttpRequest.getVerificationCode(this, "changepwd", mobile, new HttpCallBack<Object>() {
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
