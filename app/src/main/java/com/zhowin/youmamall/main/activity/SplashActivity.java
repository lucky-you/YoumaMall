package com.zhowin.youmamall.main.activity;

import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivitySplashBinding;

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
        UserInfo userInfo = UserInfo.getUserInfo();
//        if (TextUtils.isEmpty(userInfo.getToken())) {
//            startActivity(GuidePageActivity.class);
//            ActivityManager.getAppInstance().finishActivity();
//        } else {
//            startActivity(LoginActivity.class);
//        }
        startActivity(MainActivity.class);
        ActivityManager.getAppInstance().finishActivity();
    }

    @Override
    public void initData() {

    }


}
