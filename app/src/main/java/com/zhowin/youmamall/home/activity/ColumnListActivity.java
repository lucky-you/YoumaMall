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
import java.util.HashMap;
import java.util.List;

/**
 * 分类   /  VIP  商品列表
 * categoryType :  1：商品列表  2：福利中心商品列表 3：商品热销列表 4:复购商品列表 5：新品首发
 */
public class ColumnListActivity extends BaseBindActivity<ActivityColumnListBinding> implements OnGoodCardItemClickListener {


    private String categoryTitle;
    private int categoryType, categoryId;
    private HomeXPSFAdapter homeXPSFAdapter;
    private boolean isHideShareButton;


    public static void start(Context context, int type, String categoryTitle, int categoryId, boolean isHideShare) {
        Intent intent = new Intent(context, ColumnListActivity.class);
        intent.putExtra(ConstantValue.INDEX, type);
        intent.putExtra(ConstantValue.TITLE, categoryTitle);
        intent.putExtra(ConstantValue.ID, categoryId);
        intent.putExtra(ConstantValue.TYPE, isHideShare);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_column_list;
    }

    @Override
    public void initView() {
        categoryType = getIntent().getIntExtra(ConstantValue.INDEX, -1);
        categoryTitle = getIntent().getStringExtra(ConstantValue.TITLE);
        categoryId = getIntent().getIntExtra(ConstantValue.ID, -1);
        isHideShareButton = getIntent().getBooleanExtra(ConstantValue.TYPE, false);
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
        HashMap<String, Object> map = new HashMap<>();
        switch (categoryType) {
            case 1:  //商品列表
            case 2:  //会员VIP商品
            case 4: // 复购商品
                map.put("shop_category_id", categoryId);
                map.put("page", currentPage);
                map.put("size", pageNumber);
                break;
            case 3: //热销榜
                map.put("page", currentPage);
                map.put("size", pageNumber);
                map.put("sort", 2);
                break;
            case 5://新品首发
                map.put("page", currentPage);
                map.put("size", pageNumber);
                map.put("sort", 1);
                break;
        }
        showLoadDialog();
        HttpRequest.getMallRightList(this, categoryType, map, new HttpCallBack<BaseResponse<MallRightList>>() {
            @Override
            public void onSuccess(BaseResponse<MallRightList> baseResponse) {
                dismissLoadDialog();
                if (baseResponse != null) {
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
                }
                if (homeXPSFAdapter.getData() == null || homeXPSFAdapter.getData().isEmpty()) {
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
        if (!isLogin())
            ConfirmOrderActivity.start(mContext, goodItem);

    }

    @Override
    public void onClickRootLayout(int itemId) {
        ProductDetailsActivity.start(mContext, itemId, isHideShareButton);
    }
}
