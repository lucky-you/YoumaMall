package com.zhowin.youmamall.login.fragment;

import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.PhoneUtils;
import com.zhowin.base_library.utils.SplitUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeRegisterFragmentLayoutBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.login.activity.LoginActivity;
import com.zhowin.youmamall.main.activity.MainActivity;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class RegisterFragment extends BaseBindFragment<IncludeRegisterFragmentLayoutBinding> {

    private Disposable mdDisposable;

    @Override
    public int getLayoutId() {
        return R.layout.include_register_fragment_layout;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvGetVerificationCode, R.id.tvRegister, R.id.tvLogin);
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
            case R.id.tvRegister:
                registerFromPhoneNumber();
                break;
            case R.id.tvLogin:
                LoginActivity.getInstance.showFragment(0);
                break;
        }
    }

    public void registerFromPhoneNumber() {
        String mobile = mBinding.editMobile.getText().toString().trim();
        if (!PhoneUtils.checkPhone(mobile, true)) {
            return;
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
        String invitationCode = mBinding.editInvitationCode.getText().toString().trim();
        if (TextUtils.isEmpty(invitationCode)) {
            ToastUtils.showToast("请输入邀请码");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("password", editPassword);
        map.put("again_password", editPasswordAgain);
        map.put("mobile", mobile);
        map.put("code", captchaCode);
        map.put("invitation_code", invitationCode);
        HttpRequest.registerFromPhoneNumber(this, map, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                dismissLoadDialog();
                if (userInfo != null) {
                    UserInfo.setUserInfo(userInfo);
                    JPushInterface.setAlias(mContext, 2, String.valueOf(userInfo.getUser_id()));
                    startActivity(MainActivity.class);
                    ActivityManager.getAppInstance().finishActivity();
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
        HttpRequest.getVerificationCode(this, "register", mobile, new HttpCallBack<Object>() {
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
