package com.zhowin.youmamall.home.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityColumnListBinding;
import com.zhowin.youmamall.home.adapter.HomeXPSFAdapter;
import com.zhowin.youmamall.mall.model.MallRightList;

import java.util.ArrayList;
import java.util.List;

public class ColumnListActivity extends BaseBindActivity<ActivityColumnListBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_column_list;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<MallRightList> stringListTwo = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            stringListTwo.add(new MallRightList());
        }

        HomeXPSFAdapter homeXPSFAdapter = new HomeXPSFAdapter(stringListTwo);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        mBinding.recyclerView.setAdapter(homeXPSFAdapter);
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
