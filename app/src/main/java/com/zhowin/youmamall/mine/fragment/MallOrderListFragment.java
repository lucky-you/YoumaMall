package com.zhowin.youmamall.mine.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeMallOrderFragmentLayoutBinding;
import com.zhowin.youmamall.home.activity.ConfirmOrderActivity;
import com.zhowin.youmamall.home.activity.ProductDetailsActivity;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.GoodItem;
import com.zhowin.youmamall.mine.adapter.MallOrderListAdapter;
import com.zhowin.youmamall.mine.callback.OnMallOrderListClickListener;
import com.zhowin.youmamall.mine.model.MallOrderList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：商品订单
 */
public class MallOrderListFragment extends BaseBindFragment<IncludeMallOrderFragmentLayoutBinding> implements OnMallOrderListClickListener {


    private MallOrderListAdapter mallOrderListAdapter;
    private int fragmentIndex;

    public static MallOrderListFragment Instance(int index) {
        MallOrderListFragment couponListFragment = new MallOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, index);
        couponListFragment.setArguments(bundle);
        return couponListFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.include_mall_order_fragment_layout;
    }

    @Override
    public void initView() {
        fragmentIndex = getArguments().getInt(ConstantValue.INDEX);
        getMallOrderList(true);
    }

    @Override
    public void initData() {
        mallOrderListAdapter = new MallOrderListAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(mallOrderListAdapter);
        mallOrderListAdapter.setOnMallOrderListClickListener(this);
    }

    private void getMallOrderList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        HttpRequest.getMallOrderList(this, fragmentIndex, currentPage, pageNumber, new HttpCallBack<BaseResponse<MallOrderList>>() {
            @Override
            public void onSuccess(BaseResponse<MallOrderList> baseResponse) {
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        mallOrderListAdapter.setNewData(baseResponse.getData());
                    } else {
                        mallOrderListAdapter.addData(baseResponse.getData());
                    }
                    if (baseResponse.getData().size() < pageNumber) {
                        mallOrderListAdapter.loadMoreEnd(true);
                    } else {
                        mallOrderListAdapter.loadMoreComplete();
                    }
                } else {
                    EmptyViewUtils.bindEmptyView(mContext, mallOrderListAdapter);
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
                getMallOrderList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        mallOrderListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMallOrderList(false);
            }
        }, mBinding.recyclerView);
    }

    @Override
    public void onStartPayment(GoodItem goodItem) {
        ConfirmOrderActivity.start(mContext, goodItem);
    }

    @Override
    public void onGoodDetails(int goodId) {
        ProductDetailsActivity.start(mContext, goodId);
    }

    @Override
    public void onConfirmReceipt(int goodId) {
        new CenterHitMessageDialog(mContext, "确认收到货了吗？", new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {

            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                confirmReceiptGood(goodId);
            }
        }).show();
    }

    private void confirmReceiptGood(int goodId) {
        showLoadDialog();
        HttpRequest.goodConfirmReceipt(this, goodId, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                getMallOrderList(true);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
