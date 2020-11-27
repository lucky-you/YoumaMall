package com.zhowin.youmamall.dynamic.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.circle.adapter.CircleFragmentAdapter;
import com.zhowin.youmamall.databinding.IncludeDynamicFragmentLayoutBinding;
import com.zhowin.youmamall.dynamic.adapter.DynamicFragmentAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class DynamicFragment extends BaseBindFragment<IncludeDynamicFragmentLayoutBinding> {
    private DynamicFragmentAdapter dynamicFragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.include_dynamic_fragment_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<String> imageList = Arrays.asList("", "", "", "", "");
        dynamicFragmentAdapter = new DynamicFragmentAdapter(imageList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(dynamicFragmentAdapter);
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
