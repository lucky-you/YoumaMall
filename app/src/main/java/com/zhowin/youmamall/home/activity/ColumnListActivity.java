package com.zhowin.youmamall.home.activity;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityColumnListBinding;
import com.zhowin.youmamall.home.adapter.HomeXPSFAdapter;
import com.zhowin.youmamall.home.callback.OnGoodCardItemClickListener;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mall.model.MallRightList;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类商品
 */
public class ColumnListActivity extends BaseBindActivity<ActivityColumnListBinding> implements OnGoodCardItemClickListener {


    private String categoryTitle;
    private int categoryId;
    private HomeXPSFAdapter homeXPSFAdapter;

    public static void start(Context context, String categoryTitle, int categoryId) {
        Intent intent = new Intent(context, ColumnListActivity.class);
        intent.putExtra(ConstantValue.TITLE, categoryTitle);
        intent.putExtra(ConstantValue.ID, categoryId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_column_list;
    }

    @Override
    public void initView() {
        categoryTitle = getIntent().getStringExtra(ConstantValue.TITLE);
        categoryId = getIntent().getIntExtra(ConstantValue.ID, -1);
        mBinding.tvTitleView.setTitle(categoryTitle);

        getColumnDataList(true);
    }

    @Override
    public void initData() {
        homeXPSFAdapter = new HomeXPSFAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        mBinding.recyclerView.setAdapter(homeXPSFAdapter);
        homeXPSFAdapter.setOnGoodCardItemClickListener(this);
    }


    private void getColumnDataList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        HttpRequest.getMallRightList(this, false, categoryId, currentPage, pageNumber, new HttpCallBack<BaseResponse<MallRightList>>() {
            @Override
            public void onSuccess(BaseResponse<MallRightList> baseResponse) {
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
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getColumnDataList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        homeXPSFAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getColumnDataList(false);
            }
        }, mBinding.recyclerView);
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
