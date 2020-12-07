package com.zhowin.youmamall.login.fragment;

import android.text.TextUtils;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.PhoneUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeLoginFragmentLayoutBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.main.activity.MainActivity;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class LoginFragment extends BaseBindFragment<IncludeLoginFragmentLayoutBinding> {
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRememberPassword:
                break;
            case R.id.tvForgetPassword:
                break;
            case R.id.tvLogin:
                String mobile = mBinding.editMobile.getText().toString().trim();
                if (!PhoneUtils.checkPhone(mobile, true)) {
                    return;
                }
                String editPassword = mBinding.editPassword.getText().toString().trim();
                if (TextUtils.isEmpty(editPassword)) {
                    ToastUtils.showToast("请输入密码");
                    return;
                }
                loginMobileAndPassword(mobile, editPassword);
                break;

        }
    }

    private void loginMobileAndPassword(String mobile, String password) {
        showLoadDialog();
        HttpRequest.loginMobileAndPassword(this, mobile, password, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                dismissLoadDialog();
                if (userInfo != null) {
                    UserInfo.setUserInfo(userInfo);
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
