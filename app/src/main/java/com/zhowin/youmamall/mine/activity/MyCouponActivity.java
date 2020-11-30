package com.zhowin.youmamall.mine.activity;


import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.zhowin.base_library.adapter.HomePageAdapter;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityMyCouponBinding;
import com.zhowin.youmamall.mine.fragment.AccountTurnoverFragment;
import com.zhowin.youmamall.mine.fragment.CouponListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券 和 账号流水
 */
public class MyCouponActivity extends BaseBindActivity<ActivityMyCouponBinding> {


    private int classType;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, MyCouponActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_coupon;
    }

    @Override
    public void initView() {
        classType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
    }

    @Override
    public void initData() {
        String[] mTitles;
        List<Fragment> fragments = new ArrayList<>();
        switch (classType) {
            case 1:
                mBinding.tvTitleView.setTitle("流水账号");
                mTitles = new String[]{"账号流水", "提现记录"};
                for (int i = 0; i < mTitles.length; i++) {
                    fragments.add(AccountTurnoverFragment.Instance(i));
                }
                break;
            case 2:
                mBinding.tvTitleView.setTitle("我的优惠券");
                mTitles = new String[]{"可使用", "已使用", "已失效"};
                for (int i = 0; i < mTitles.length; i++) {
                    fragments.add(CouponListFragment.Instance(i));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + classType);
        }
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager(), fragments, mTitles);
        mBinding.noScrollViewPager.setAdapter(homePageAdapter);
        mBinding.slidingTabLayout.setViewPager(mBinding.noScrollViewPager);
    }
}
