package com.zhowin.youmamall.mine.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityMyTeamBinding;
import com.zhowin.youmamall.mine.adapter.CouponListAdapter;
import com.zhowin.youmamall.mine.adapter.MyTeamAdapter;

import java.util.Arrays;
import java.util.List;

public class MyTeamActivity extends BaseBindActivity<ActivityMyTeamBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        List<String> stringList = Arrays.asList("", "", "", "", "", "");

        MyTeamAdapter myTeamAdapter = new MyTeamAdapter(stringList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(myTeamAdapter);

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
