package com.zhowin.youmamall.home.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.banner.BannerImageAdapter;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeHomePageFragmentBinding;
import com.zhowin.youmamall.home.activity.ColumnListActivity;
import com.zhowin.youmamall.home.activity.ConfirmOrderActivity;
import com.zhowin.youmamall.home.activity.ProductDetailsActivity;
import com.zhowin.youmamall.home.adapter.ColumnListAdapter;
import com.zhowin.youmamall.home.adapter.HomeFragmentAdapter;
import com.zhowin.youmamall.home.callback.OnHomeFragmentClickListener;
import com.zhowin.youmamall.home.model.ColumnList;
import com.zhowin.youmamall.home.model.HomePageList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：首页
 */
public class HomePageFragment extends BaseBindFragment<IncludeHomePageFragmentBinding> implements OnHomeFragmentClickListener {

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
        List<String> bannerList = new ArrayList<>();
        bannerList.add("https://img.zcool.cn/community/013de756fb63036ac7257948747896.jpg");
        bannerList.add("https://img.zcool.cn/community/01270156fb62fd6ac72579485aa893.jpg");
        bannerList.add("https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg");

        BannerImageAdapter bannerImageAdapter = new BannerImageAdapter(bannerList, 2);
        mBinding.homeBanner.setAdapter(bannerImageAdapter).start();


        List<ColumnList> columnList = new ArrayList<>();
        columnList.add(new ColumnList(1, "安卓软件"));
        columnList.add(new ColumnList(2, "苹果软件"));
        columnList.add(new ColumnList(3, "电脑软件"));
        columnList.add(new ColumnList(4, "云端软件"));
        columnList.add(new ColumnList(5, "会员福利"));
        columnListAdapter = new ColumnListAdapter(columnList, 1);
        mBinding.ColumnRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mBinding.ColumnRecyclerView.setAdapter(columnListAdapter);

        List<HomePageList> homePageLists = new ArrayList<>();
        homePageLists.add(new HomePageList(1, "热销榜", "最受欢迎的应用软件", true));
        homePageLists.add(new HomePageList(2, "新品首发", "为您寻觅世间软件", true));
        homePageLists.add(new HomePageList(3, "福利功能", "会员永久免费试用", false));
        homeFragmentAdapter = new HomeFragmentAdapter(homePageLists);
        mBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.homeRecyclerView.setAdapter(homeFragmentAdapter);
        homeFragmentAdapter.setOnHomeFragmentClickListener(this);
    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        columnListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(ColumnListActivity.class);
            }
        });
    }

    @Override
    public void onClickBuyCard() {
        startActivity(ConfirmOrderActivity.class);
    }

    @Override
    public void onClickRootLayout() {
        startActivity(ProductDetailsActivity.class);

    }
}
