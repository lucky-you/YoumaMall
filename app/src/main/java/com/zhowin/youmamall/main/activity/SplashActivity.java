package com.zhowin.youmamall.main.activity;


import android.os.Handler;

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

    private Handler mHandler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isStartMain = SPUtils.getBoolean(ConstantValue.START_MAIN, false);
                if (isStartMain) {
                    startActivity(MainActivity.class);
                } else {
                    startActivity(GuidePageActivity.class);
                }
                ActivityManager.getAppInstance().finishActivity();
            }
        }, 2000);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.color_FFFDDB)
                .keyboardEnable(true)
                .statusBarDarkFont(true)
                .init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
