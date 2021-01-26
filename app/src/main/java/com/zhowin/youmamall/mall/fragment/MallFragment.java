package com.zhowin.youmamall.mall.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.GridSpacesItemDecoration;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeMallFragmentLayoutBinding;
import com.zhowin.youmamall.home.activity.ConfirmOrderActivity;
import com.zhowin.youmamall.home.activity.ProductDetailsActivity;
import com.zhowin.youmamall.home.activity.SearchActivity;
import com.zhowin.youmamall.home.callback.OnGoodCardItemClickListener;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.adapter.MallLeftListAdapter;
import com.zhowin.youmamall.mall.adapter.MallRightListAdapter;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mall.model.MallLeftList;
import com.zhowin.youmamall.mall.model.MallRightList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class MallFragment extends BaseBindFragment<IncludeMallFragmentLayoutBinding> implements OnGoodCardItemClickListener {

    private MallLeftListAdapter mallLeftListAdapter;
    private MallRightListAdapter mallRightListAdapter;
    private int rightCategoryId;

    @Override
    public int getLayoutId() {
        return R.layout.include_mall_fragment_layout;
    }

    @Override
    public void initView() {

        getMallLeftList();
    }

    @Override
    public void initData() {

        mallLeftListAdapter = new MallLeftListAdapter(new ArrayList<>(), 0);
        mBinding.leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.leftRecyclerView.setAdapter(mallLeftListAdapter);

        mallRightListAdapter = new MallRightListAdapter(new ArrayList<>());
        mBinding.rightRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mBinding.rightRecyclerView.setAdapter(mallRightListAdapter);
        mallRightListAdapter.setOnGoodCardItemClickListener(this);

    }

    private void getMallLeftList() {
        HttpRequest.getMallLeftList(this, new HttpCallBack<List<MallLeftList>>() {
            @Override
            public void onSuccess(List<MallLeftList> mallLeftLists) {
                if (mallLeftLists != null && !mallLeftLists.isEmpty()) {
                    mallLeftListAdapter.setNewData(mallLeftLists);
                    rightCategoryId = mallLeftLists.get(0).getId();
                    getMallRightList(true, rightCategoryId);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }


    private void getMallRightList(boolean isRefresh, int categoryId) {
        if (isRefresh) {
            currentPage = 1;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("shop_category_id", categoryId);
        map.put("page", currentPage);
        map.put("size", pageNumber);
        HttpRequest.getMallRightList(this, 1, map, new HttpCallBack<BaseResponse<MallRightList>>() {
            @Override
            public void onSuccess(BaseResponse<MallRightList> baseResponse) {
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        mallRightListAdapter.setNewData(baseResponse.getData());
                    } else {
                        mallRightListAdapter.addData(baseResponse.getData());
                    }
                    if (baseResponse.getData().size() <= pageNumber) {
                        mallRightListAdapter.loadMoreEnd(true);
                    } else {
                        mallRightListAdapter.loadMoreComplete();
                    }
                    currentPage++;
                } else {
                    EmptyViewUtils.bindEmptyView(mContext, mallRightListAdapter);
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
        mBinding.tvTitleView.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin())
                    startActivity(SearchActivity.class);
            }
        });
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMallRightList(true, rightCategoryId);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        mallLeftListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mallLeftListAdapter.setCurrentPosition(position);
                rightCategoryId = mallLeftListAdapter.getItem(position).getId();
                getMallRightList(true, rightCategoryId);
            }
        });

        mallRightListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMallRightList(false, rightCategoryId);
            }
        }, mBinding.rightRecyclerView);
    }

    @Override
    public void onClickBuyCard(GoodItem goodItem) {
        ConfirmOrderActivity.start(mContext, goodItem);
    }

    @Override
    public void onClickRootLayout(int itemId) {
        ProductDetailsActivity.start(mContext, itemId, false);
    }
}
