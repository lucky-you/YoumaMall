package com.zhowin.youmamall.home.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.banner.BannerImageAdapter;
import com.zhowin.base_library.banner.BannerList;
import com.zhowin.base_library.http.ApiResponse;
import com.zhowin.base_library.http.RetrofitFactory;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.youmamall.BuildConfig;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeHomePageFragmentBinding;
import com.zhowin.youmamall.home.activity.ColumnListActivity;
import com.zhowin.youmamall.home.activity.ConfirmOrderActivity;
import com.zhowin.youmamall.home.activity.ProductDetailsActivity;
import com.zhowin.youmamall.home.adapter.HomeCategoryListAdapter;
import com.zhowin.youmamall.home.adapter.HomeFragmentAdapter;
import com.zhowin.youmamall.home.callback.OnGoodCardItemClickListener;
import com.zhowin.youmamall.home.callback.OnHomeSeeMoreListener;
import com.zhowin.youmamall.home.model.HomeDynamicInfo;
import com.zhowin.youmamall.home.model.HomePageData;
import com.zhowin.youmamall.home.model.HomePageList;
import com.zhowin.youmamall.http.ApiRequest;
import com.zhowin.youmamall.main.activity.MainActivity;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mall.model.MallLeftList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：首页
 */
public class HomePageFragment extends BaseBindFragment<IncludeHomePageFragmentBinding> implements
        OnGoodCardItemClickListener, OnHomeSeeMoreListener {

    private HomeCategoryListAdapter homeCategoryListAdapter;
    private HomeFragmentAdapter homeFragmentAdapter;
    private List<HomePageList> homePageLists = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.include_home_page_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        getHomeDynamic();

        homeCategoryListAdapter = new HomeCategoryListAdapter(new ArrayList<>());
        mBinding.ColumnRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mBinding.ColumnRecyclerView.setAdapter(homeCategoryListAdapter);

        homeFragmentAdapter = new HomeFragmentAdapter(homePageLists);
        mBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.homeRecyclerView.setAdapter(homeFragmentAdapter);
        homeFragmentAdapter.setOnGoodCardItemClickListener(this);
        homeFragmentAdapter.setOnHomeSeeMoreListener(this::onRightSeeMore);
    }


    private void getHomeDynamic() {
        Observable<ApiResponse<HomePageData>> homePageData = RetrofitFactory.getInstance().initRetrofit(BuildConfig.API_HOST).create(ApiRequest.class).getHomePageDataInfo(UserInfo.getUserToken());
        Observable<ApiResponse<HomeDynamicInfo>> homeDynamicData = RetrofitFactory.getInstance().initRetrofit(BuildConfig.API_HOST).create(ApiRequest.class).getHomeDynamicDataInfo(UserInfo.getUserToken());
        if (homePageLists.isEmpty()) homePageLists.clear();
        Observable.merge(homePageData, homeDynamicData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse<? extends Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiResponse<?> apiResponse) {
                        if (apiResponse.getData() instanceof HomePageData) {
                            HomePageData homePageData = (HomePageData) apiResponse.getData();
                            if (homePageData != null) {
                                homePageLists.add(new HomePageList(1, "热销榜", "最受欢迎的应用软件", true, homePageData.getHome_sale_item()));
                                homePageLists.add(new HomePageList(2, "新品首发", "为您寻觅世间软件", true, homePageData.getNew_item_list()));
                            }
                        } else if (apiResponse.getData() instanceof HomeDynamicInfo) {
                            HomeDynamicInfo homeDynamicInfo = (HomeDynamicInfo) apiResponse.getData();
                            if (homeDynamicInfo != null) {
                                homePageLists.add(new HomePageList(3, "福利功能", "会员永久免费试用", false, new ArrayList<>(), homeDynamicInfo.getVip_rule_list()));

                                List<MallLeftList> categoryList = homeDynamicInfo.getCategory_list();
                                if (categoryList != null && !categoryList.isEmpty()) {
                                    homeCategoryListAdapter.setNewData(categoryList);
                                }
                                List<BannerList> bannerList = homeDynamicInfo.getHome_list();
                                if (bannerList != null && !bannerList.isEmpty()) {
                                    BannerImageAdapter bannerImageAdapter = new BannerImageAdapter(bannerList, 2);
                                    mBinding.homeBanner.setAdapter(bannerImageAdapter).start();
                                }
                                List<BannerList> slideshowList = homeDynamicInfo.getSlide_list();
                                if (slideshowList != null && !slideshowList.isEmpty()) {
                                    BannerImageAdapter bannerAdapter = new BannerImageAdapter(bannerList, 2);
                                    mBinding.leftSlideshow.setAdapter(bannerAdapter).start();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        if (homePageLists != null && !homePageLists.isEmpty()) {
                            homeFragmentAdapter.setNewData(homePageLists);
                        }
                    }
                });

    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        homeCategoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(ColumnListActivity.class);
            }
        });
    }

    @Override
    public void onClickBuyCard(GoodItem goodItem) {
        ConfirmOrderActivity.start(mContext, goodItem);
    }

    @Override
    public void onClickRootLayout(int itemId) {
        ProductDetailsActivity.start(mContext, itemId);
    }

    @Override
    public void onRightSeeMore() {
        MainActivity.Instance.showJumpFragment(1);

    }
}
