package com.zhowin.youmamall.home.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeHomeTaskFragmentBinding;
import com.zhowin.youmamall.home.activity.WebViewActivity;
import com.zhowin.youmamall.home.adapter.ResourcesCategoryAdapter;
import com.zhowin.youmamall.home.adapter.ResourcesListAdapter;
import com.zhowin.youmamall.home.model.ResourcesCategory;
import com.zhowin.youmamall.home.model.ResourcesDetailsInfo;
import com.zhowin.youmamall.home.model.ResourcesList;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：资源
 */
public class HomeTaskFragment extends BaseBindFragment<IncludeHomeTaskFragmentBinding> {

    private ResourcesCategoryAdapter resourcesCategoryAdapter;
    private ResourcesListAdapter resourcesListAdapter;
    private int categoryId;

    @Override
    public int getLayoutId() {
        return R.layout.include_home_task_fragment;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        getResourcesCategory();
        resourcesCategoryAdapter = new ResourcesCategoryAdapter(new ArrayList<>());
        mBinding.categoryRecycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        mBinding.categoryRecycler.setAdapter(resourcesCategoryAdapter);

        resourcesListAdapter = new ResourcesListAdapter(new ArrayList<>());
        mBinding.resourcesListRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.resourcesListRecycler.setAdapter(resourcesListAdapter);

    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
                getResourcesList(categoryId, true);
            }
        });
        resourcesCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                categoryId = resourcesCategoryAdapter.getItem(position).getId();
                resourcesCategoryAdapter.setCurrentPosition(position);
                getResourcesList(categoryId, true);
            }
        });
        resourcesListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getResourcesList(categoryId, false);
            }
        }, mBinding.resourcesListRecycler);
        resourcesListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int itemId = resourcesListAdapter.getItem(position).getId();
                getResourcesDetails(itemId);
            }
        });
    }

    private void getResourcesDetails(int itemId) {
        HttpRequest.getResourcesDetails(this, itemId, new HttpCallBack<ResourcesDetailsInfo>() {
            @Override
            public void onSuccess(ResourcesDetailsInfo resourcesDetailsInfo) {
                if (resourcesDetailsInfo != null) {
                    String title = resourcesDetailsInfo.getTitle();
                    String url = resourcesDetailsInfo.getContent();
                    WebViewActivity.start(mContext, title, url, false);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void getResourcesCategory() {
        HttpRequest.getResourcesCategory(this, new HttpCallBack<List<ResourcesCategory>>() {
            @Override
            public void onSuccess(List<ResourcesCategory> resourcesCategories) {
                if (resourcesCategories != null && !resourcesCategories.isEmpty()) {
                    categoryId = resourcesCategories.get(0).getId();
                    resourcesCategoryAdapter.setNewData(resourcesCategories);
                    getResourcesList(categoryId, true);
                }

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void getResourcesList(int categoryId, boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        HttpRequest.getResourcesList(this, categoryId, currentPage, pageNumber, new HttpCallBack<BaseResponse<ResourcesList>>() {
            @Override
            public void onSuccess(BaseResponse<ResourcesList> baseResponse) {
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        resourcesListAdapter.setNewData(baseResponse.getData());
                    } else {
                        resourcesListAdapter.addData(baseResponse.getData());
                    }
                    if (baseResponse.getData().size() < pageNumber) {
                        resourcesListAdapter.loadMoreEnd(true);
                    } else {
                        resourcesListAdapter.loadMoreComplete();
                    }
                } else {
                    EmptyViewUtils.bindEmptyView(mContext, resourcesListAdapter);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
