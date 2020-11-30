package com.zhowin.youmamall.mine.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeCouponListFragmentBinding;
import com.zhowin.youmamall.mine.adapter.CouponListAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class CouponListFragment extends BaseBindFragment<IncludeCouponListFragmentBinding> {

    public static CouponListFragment Instance(int index) {
        CouponListFragment couponListFragment = new CouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, index);
        couponListFragment.setArguments(bundle);
        return couponListFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_coupon_list_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        List<String> stringList = Arrays.asList("", "", "", "", "", "");

        CouponListAdapter couponListAdapter = new CouponListAdapter(stringList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(couponListAdapter);

    }


    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
    }
}
