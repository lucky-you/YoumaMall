package com.zhowin.youmamall.home.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeHomePageFragmentBinding;
import com.zhowin.youmamall.home.adapter.ColumnListAdapter;
import com.zhowin.youmamall.home.adapter.HomeFragmentAdapter;
import com.zhowin.youmamall.home.model.HomePageList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：首页
 */
public class HomePageFragment extends BaseBindFragment<IncludeHomePageFragmentBinding> {

    private ColumnListAdapter columnListAdapter;
    private HomeFragmentAdapter homeFragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.include_home_page_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<String> columnList = Arrays.asList("安卓软件", "苹果软件", "电脑软件", "云端软件", "会员福利");
        columnListAdapter = new ColumnListAdapter(columnList);
        mBinding.ColumnRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mBinding.ColumnRecyclerView.setAdapter(columnListAdapter);

        List<HomePageList> homePageLists = new ArrayList<>();
        homePageLists.add(new HomePageList(1, "热销榜", "最受欢迎的应用软件", true));
        homePageLists.add(new HomePageList(2, "新品首发", "为您寻觅世间软件", true));
        homePageLists.add(new HomePageList(3, "福利功能", "会员永久免费试用", false));
        homeFragmentAdapter = new HomeFragmentAdapter(homePageLists);
        mBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.homeRecyclerView.setAdapter(homeFragmentAdapter);

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
