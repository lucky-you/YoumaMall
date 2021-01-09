package com.zhowin.youmamall.main.activity;


import com.gyf.immersionbar.ImmersionBar;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SPUtils;
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
        boolean isStartMain = SPUtils.getBoolean(ConstantValue.START_MAIN, false);
        if (isStartMain) {
            startActivity(MainActivity.class);
        } else {
            startActivity(GuidePageActivity.class);
        }
        ActivityManager.getAppInstance().finishActivity();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.white)
                .keyboardEnable(true)
                .statusBarDarkFont(true)
                .init();
    }

}
