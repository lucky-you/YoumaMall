package com.zhowin.youmamall.mine.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeMallOrderFragmentLayoutBinding;
import com.zhowin.youmamall.mine.adapter.MallOrderListAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：
 */
public class MallOrderListFragment extends BaseBindFragment<IncludeMallOrderFragmentLayoutBinding> {


    private MallOrderListAdapter mallOrderListAdapter;

    public static MallOrderListFragment Instance(int index) {
        MallOrderListFragment couponListFragment = new MallOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, index);
        couponListFragment.setArguments(bundle);
        return couponListFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.include_mall_order_fragment_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<String> stringList = Arrays.asList("", "", "", "", "", "");
        mallOrderListAdapter = new MallOrderListAdapter(stringList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(mallOrderListAdapter);
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
