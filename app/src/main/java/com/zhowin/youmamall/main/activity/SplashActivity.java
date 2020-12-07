package com.zhowin.youmamall.main.activity;

import android.text.TextUtils;

import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivitySplashBinding;
import com.zhowin.youmamall.login.activity.LoginActivity;

/**
 * 引导页
 */
public class SplashActivity extends BaseBindActivity<ActivitySplashBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        String userToken = UserInfo.getUserToken();
        if (TextUtils.isEmpty(userToken)) {
            startActivity(LoginActivity.class);
        } else {
            startActivity(MainActivity.class);
        }
        ActivityManager.getAppInstance().finishActivity();
    }

    @Override
    public void initData() {

    }


}
