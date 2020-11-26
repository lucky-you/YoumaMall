package com.zhowin.youmamall.home.fragment;

import androidx.fragment.app.Fragment;

import com.zhowin.base_library.adapter.HomePageAdapter;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeHomeFragmentLayoutBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class HomeFragment extends BaseBindFragment<IncludeHomeFragmentLayoutBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.include_home_fragment_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        String[] mTitles = {"任务", "首页", "注册"};
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeTaskFragment());
        fragments.add(new HomePageFragment());
        fragments.add(new HomeCircleFragment());
        HomePageAdapter homePageAdapter = new HomePageAdapter(getChildFragmentManager(), fragments, mTitles);
        mBinding.noScrollViewPager.setAdapter(homePageAdapter);
        mBinding.noScrollViewPager.setOffscreenPageLimit(mTitles.length);
        mBinding.slidingTabLayout.setViewPager(mBinding.noScrollViewPager);
    }
}
