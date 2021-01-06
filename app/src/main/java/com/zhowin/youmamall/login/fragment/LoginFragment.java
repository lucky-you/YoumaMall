package com.zhowin.youmamall.login.fragment;

import android.text.TextUtils;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.PhoneUtils;
import com.zhowin.base_library.utils.SPUtils;
import com.zhowin.base_library.utils.SetDrawableHelper;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeLoginFragmentLayoutBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.login.activity.ForgetPasswordActivity;
import com.zhowin.youmamall.main.activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class LoginFragment extends BaseBindFragment<IncludeLoginFragmentLayoutBinding> {

    private boolean isRememberPassword;

    @Override
    public int getLayoutId() {
        return R.layout.include_login_fragment_layout;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvRememberPassword, R.id.tvForgetPassword, R.id.tvLogin);
    }

    @Override
    public void initData() {
        boolean isRememberPass = SPUtils.getBoolean(ConstantValue.REMEMBER_PASSWORD, false);
        if (isRememberPass) {
            isRememberPassword = true;
            setRememberPasswordStatus();
            mBinding.editMobile.setText(UserInfo.getUserInfo().getMobile());
            mBinding.editMobile.setSelection(UserInfo.getUserInfo().getMobile().length());

            mBinding.editPassword.setText(UserInfo.getUserPassword());
            mBinding.editPassword.setSelection(UserInfo.getUserPassword().length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRememberPassword:
                isRememberPassword = !isRememberPassword;
                setRememberPasswordStatus();
                break;
            case R.id.tvForgetPassword:
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.tvLogin:
                loginMobileAndPassword();
                break;
        }
    }

    private void setRememberPasswordStatus() {
        SetDrawableHelper.setLeftDrawable(mContext, mBinding.tvRememberPassword,
                isRememberPassword, 4, R.drawable.icon_remember_password, R.drawable.icon_not_remember_password);
    }

    private void loginMobileAndPassword() {
        String mobile = mBinding.editMobile.getText().toString().trim();
        if (!PhoneUtils.checkPhone(mobile, true)) {
            return;
        }
        String password = mBinding.editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast("请输入密码");
            return;
        }
        showLoadDialog();
        HttpRequest.loginMobileAndPassword(this, mobile, password, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                dismissLoadDialog();
                if (userInfo != null) {
                    UserInfo.setUserInfo(userInfo);
                    JPushInterface.setAlias(mContext, 2, String.valueOf(userInfo.getUser_id()));
                    if (isRememberPassword) {
                        SPUtils.set(ConstantValue.REMEMBER_PASSWORD, true);
                        UserInfo.setUserPassword(mBinding.editPassword.getText().toString().trim());
                    }
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
}
