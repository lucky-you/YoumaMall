package com.zhowin.youmamall.home.fragment;

import android.app.Dialog;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.banner.BannerImageAdapter;
import com.zhowin.base_library.banner.BannerList;
import com.zhowin.base_library.callback.OnBannerItemClickListener;
import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.http.ApiResponse;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.http.RetrofitFactory;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.youmamall.BuildConfig;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeHomePageFragmentBinding;
import com.zhowin.youmamall.home.activity.ColumnListActivity;
import com.zhowin.youmamall.home.activity.ConfirmOrderActivity;
import com.zhowin.youmamall.home.activity.ProductDetailsActivity;
import com.zhowin.youmamall.home.activity.WebViewActivity;
import com.zhowin.youmamall.home.adapter.HomeCategoryListAdapter;
import com.zhowin.youmamall.home.adapter.HomeFragmentAdapter;
import com.zhowin.youmamall.home.callback.OnGoodCardItemClickListener;
import com.zhowin.youmamall.home.callback.OnHomeSeeMoreListener;
import com.zhowin.youmamall.home.dialog.LatestNewDialog;
import com.zhowin.youmamall.home.model.HomeDynamicInfo;
import com.zhowin.youmamall.home.model.HomePageData;
import com.zhowin.youmamall.home.model.HomePageList;
import com.zhowin.youmamall.home.model.LatestNewInfo;
import com.zhowin.youmamall.home.model.UnreadMessageInfo;
import com.zhowin.youmamall.home.model.VipWelfareList;
import com.zhowin.youmamall.http.ApiRequest;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.main.activity.MainActivity;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mall.model.MallLeftList;
import com.zhowin.youmamall.mine.activity.OpenAgentActivity;

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
    private List<BannerList> slideshowList;

    @Override
    public int getLayoutId() {
        return R.layout.include_home_page_fragment;
    }

    @Override
    public void initView() {
        setOnClick(R.id.rivImageOne, R.id.rivImageTwo, R.id.rivImageThree);
    }

    @Override
    public void initData() {

        getHomeDynamic();

        getUnreadMessageInfo();

        homeCategoryListAdapter = new HomeCategoryListAdapter(new ArrayList<>());
        mBinding.ColumnRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mBinding.ColumnRecyclerView.setAdapter(homeCategoryListAdapter);

        homeFragmentAdapter = new HomeFragmentAdapter(homePageLists);
        mBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.homeRecyclerView.setAdapter(homeFragmentAdapter);
        homeFragmentAdapter.setOnGoodCardItemClickListener(this);
        homeFragmentAdapter.setOnHomeSeeMoreListener(this);
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
                                slideshowList = homeDynamicInfo.getSlide_list();
                                if (slideshowList != null && !slideshowList.isEmpty()) {
                                    BannerImageAdapter bannerAdapter = new BannerImageAdapter(bannerList, 2);
                                    mBinding.leftSlideshow.setAdapter(bannerAdapter).start();
                                    bannerAdapter.setOnBannerItemClickListener(new OnBannerItemClickListener() {
                                        @Override
                                        public void onBannerClick(BannerList bannerList) {
                                            ProductDetailsActivity.start(mContext, bannerList.getId());
                                        }
                                    });
                                }
                                showLatestNewDialog(homeDynamicInfo.getNotice());
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

    private void getUnreadMessageInfo() {
        HttpRequest.getUnreadMessageInfo(this, new HttpCallBack<UnreadMessageInfo>() {
            @Override
            public void onSuccess(UnreadMessageInfo unreadMessageInfo) {
                if (unreadMessageInfo != null) {
                    mBinding.homeMarqueeView.startWithList(unreadMessageInfo.getMoney_log());
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

            }
        });
    }

    private void showLatestNewDialog(LatestNewInfo latestNewInfo) {
        if (latestNewInfo != null) {
            LatestNewDialog latestNewDialog = new LatestNewDialog(mContext);
            latestNewDialog.setLatestNewData(latestNewInfo);
            latestNewDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rivImageOne:
                if (UserInfo.getUserInfo().getLevel() > 0) {
                    ColumnListActivity.start(mContext, 2, "会员代理", 0);
                } else {
                    showHitLevelDialog();
                }
                break;
            case R.id.rivImageTwo:
                ColumnListActivity.start(mContext, 1, "商品热销", 0);
                break;
            case R.id.rivImageThree:
                ColumnListActivity.start(mContext, 3, "商品复购", 0);
                break;
        }
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
                String categoryTitle = homeCategoryListAdapter.getItem(position).getName();
                int categoryId = homeCategoryListAdapter.getItem(position).getId();
                ColumnListActivity.start(mContext, 1, categoryTitle, categoryId);
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

    @Override
    public void onFLGNItemClick(VipWelfareList item) {
        if (UserInfo.getUserInfo().getLevel() > 0) {
            WebViewActivity.start(mContext, item.getName(), item.getUrl());
        } else {
            showHitLevelDialog();
        }
    }

    private void showHitLevelDialog() {
        new CenterHitMessageDialog(mContext, "您当前非会员,是否开通会员?", new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {
            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                OpenAgentActivity.start(mContext, 2);
            }
        }).setNegativeButton("我再想想")
                .setPositiveButton("立即开通")
                .show();
    }

}
