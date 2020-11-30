package com.zhowin.youmamall.mine.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;


import com.zhowin.base_library.adapter.HomePageAdapter;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityMallOrderListBinding;
import com.zhowin.youmamall.mine.fragment.AccountTurnoverFragment;
import com.zhowin.youmamall.mine.fragment.MallOrderListFragment;

import java.util.ArrayList;
import java.util.List;

public class MallOrderListActivity extends BaseBindActivity<ActivityMallOrderListBinding> {

    private int jumpPosition;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, MallOrderListActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mall_order_list;
    }

    @Override
    public void initView() {
        jumpPosition = getIntent().getIntExtra(ConstantValue.TYPE, -1);
    }

    @Override
    public void initData() {
        String[] mTitles = {"全部", "待付款", "待发货", "待收货", "已完成"};
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            fragments.add(MallOrderListFragment.Instance(i));
        }
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager(), fragments, mTitles);
        mBinding.noScrollViewPager.setAdapter(homePageAdapter);
        mBinding.noScrollViewPager.setScroll(true);
        mBinding.slidingTabLayout.setViewPager(mBinding.noScrollViewPager);
        mBinding.slidingTabLayout.setCurrentTab(jumpPosition);
    }
}
