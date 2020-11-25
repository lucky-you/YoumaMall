package com.zhowin.youmamall.login.activity;


import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityLoginBinding;

/**
 * 登录
 */
public class LoginActivity extends BaseBindActivity<ActivityLoginBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ActivityManager.getAppInstance().finishAllButCurrentActivity(LoginActivity.class);
    }

    @Override
    public void initData() {

    }
}
