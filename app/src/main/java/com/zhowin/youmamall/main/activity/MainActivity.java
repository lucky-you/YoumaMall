package com.zhowin.youmamall.main.activity;


import androidx.fragment.app.Fragment;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.circle.fragment.CircleFragment;
import com.zhowin.youmamall.databinding.ActivityMainBinding;
import com.zhowin.youmamall.dynamic.fragment.DynamicFragment;
import com.zhowin.youmamall.home.fragment.HomeFragment;
import com.zhowin.youmamall.main.entity.TabEntity;
import com.zhowin.youmamall.mall.fragment.MallFragment;
import com.zhowin.youmamall.mine.fragment.MineFragment;

import java.util.ArrayList;

public class MainActivity extends BaseBindActivity<ActivityMainBinding> implements OnTabSelectListener {


    private String[] mTitles = {"首页", "商城", "圈子", "动态", "我的"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private int[] mIconSelectIds = {
            R.drawable.icon_tab_home_y, R.drawable.icon_tab_mall_y, R.drawable.icon_tab_circle_y, R.drawable.icon_tab_dynamic_y, R.drawable.icon_tab_mine_y};

    private int[] mIconUnSelectIds = {
            R.drawable.icon_tab_home_n, R.drawable.icon_tab_mall_n, R.drawable.icon_tab_circle_n, R.drawable.icon_tab_dynamic_n, R.drawable.icon_tab_mine_n};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        mFragments.add(new HomeFragment());
        mFragments.add(new MallFragment());
        mFragments.add(CircleFragment.newInstance(1));
        mFragments.add(new DynamicFragment());
        mFragments.add(new MineFragment());
        mBinding.commonTabLayout.setTabData(mTabEntities, mContext, R.id.container, mFragments);
        mBinding.commonTabLayout.setOnTabSelectListener(this);
    }


    @Override
    public void onTabSelect(int position) {
        if (4 == position) {
            if (!isLogin()) {
                mBinding.commonTabLayout.setCurrentTab(position);
            } else {
                mBinding.commonTabLayout.setCurrentTab(0);
            }
        }
    }

    @Override
    public void onTabReselect(int position) {

    }
}
