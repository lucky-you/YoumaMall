package com.zhowin.youmamall.home.activity;


import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.KeyboardUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivitySearchBinding;
import com.zhowin.youmamall.home.adapter.HomeXPSFAdapter;
import com.zhowin.youmamall.home.callback.OnGoodCardItemClickListener;
import com.zhowin.youmamall.home.model.HotKeywordList;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mall.model.MallRightList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 搜索
 */
public class SearchActivity extends BaseBindActivity<ActivitySearchBinding> implements OnGoodCardItemClickListener {


    private HomeXPSFAdapter homeXPSFAdapter;
    private String keyWords;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        setOnClick(R.id.ivBack, R.id.tvSearch);
    }

    @Override
    public void initData() {

        homeXPSFAdapter = new HomeXPSFAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        mBinding.recyclerView.setAdapter(homeXPSFAdapter);
        homeXPSFAdapter.setOnGoodCardItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                ActivityManager.getAppInstance().finishActivity();
                break;
            case R.id.tvSearch:
                keyWords = mBinding.editKeyWord.getText().toString().trim();
                if (TextUtils.isEmpty(keyWords)) {
                    ToastUtils.showToast("请输入搜索关键词");
                    return;
                }
                KeyboardUtils.hideSoftInput(mContext);
                getSearchResultGood(true);
                break;
        }
    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSearchResultGood(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        homeXPSFAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getSearchResultGood(false);
            }
        }, mBinding.recyclerView);
    }

    /**
     * 搜索商品结果
     */
    private void getSearchResultGood(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", keyWords);
        map.put("page", currentPage);
        map.put("size", pageNumber);
        showLoadDialog();
        HttpRequest.getMallRightList(this, 1, map, new HttpCallBack<BaseResponse<MallRightList>>() {
            @Override
            public void onSuccess(BaseResponse<MallRightList> baseResponse) {
                dismissLoadDialog();
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        homeXPSFAdapter.setNewData(baseResponse.getData());
                    } else {
                        homeXPSFAdapter.addData(baseResponse.getData());
                    }
                    if (baseResponse.getData().size() < pageNumber) {
                        homeXPSFAdapter.loadMoreEnd(true);
                    } else {
                        homeXPSFAdapter.loadMoreComplete();
                    }
                } else {
                    EmptyViewUtils.bindEmptyView(mContext, homeXPSFAdapter);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void getHotKeywordList() {
        HttpRequest.getHotKeywordList(this, new HttpCallBack<List<HotKeywordList>>() {
            @Override
            public void onSuccess(List<HotKeywordList> hotKeywordLists) {
                if (hotKeywordLists != null && !hotKeywordLists.isEmpty()) {

                }

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
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
}
