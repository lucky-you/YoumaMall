package com.zhowin.youmamall.login.activity;


import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.zhowin.base_library.adapter.HomePageAdapter;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityLoginBinding;
import com.zhowin.youmamall.login.fragment.LoginFragment;
import com.zhowin.youmamall.login.fragment.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录
 */
public class LoginActivity extends BaseBindActivity<ActivityLoginBinding> {


    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);

    }

    public static LoginActivity getInstance;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        getInstance = this;
        ActivityManager.getAppInstance().finishAllButCurrentActivity(LoginActivity.class);
    }

    @Override
    public void initData() {
        String[] mTitles = {"登录", "注册"};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager(), fragments, mTitles);
        mBinding.noScrollViewPager.setAdapter(homePageAdapter);
        mBinding.noScrollViewPager.setOffscreenPageLimit(2);
        mBinding.slidingTabLayout.setViewPager(mBinding.noScrollViewPager);
    }

    public void showFragment(int position) {
        mBinding.slidingTabLayout.setCurrentTab(position);
    }

    @Override
    protected void onDestroy() {
        getInstance = null;
        super.onDestroy();
    }
}
