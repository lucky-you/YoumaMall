package com.zhowin.youmamall.mine.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.EmptyView;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeCouponListFragmentBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.adapter.AccountTurnoverAdapter;
import com.zhowin.youmamall.mine.adapter.CouponListAdapter;
import com.zhowin.youmamall.mine.model.AccountTurnoverList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：账号流水
 */
public class AccountTurnoverFragment extends BaseBindFragment<IncludeCouponListFragmentBinding> {


    private AccountTurnoverAdapter accountTurnoverAdapter;
    private int fragmentIndex;

    public static AccountTurnoverFragment Instance(int index) {
        AccountTurnoverFragment couponListFragment = new AccountTurnoverFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.INDEX, index);
        couponListFragment.setArguments(bundle);
        return couponListFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.include_coupon_list_fragment;
    }

    @Override
    public void initView() {
        fragmentIndex = getArguments().getInt(ConstantValue.INDEX);
    }

    @Override
    public void initData() {

        getAccountTurnoverList(true);

        accountTurnoverAdapter = new AccountTurnoverAdapter(new ArrayList<>());
        accountTurnoverAdapter.setFragmentIndex(fragmentIndex);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(accountTurnoverAdapter);

    }

    private void getAccountTurnoverList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        showLoadDialog();
        HttpRequest.getAccountTurnoverList(this, 1 == fragmentIndex, currentPage, pageNumber, new HttpCallBack<BaseResponse<AccountTurnoverList>>() {
            @Override
            public void onSuccess(BaseResponse<AccountTurnoverList> baseResponse) {
                dismissLoadDialog();
                if (baseResponse != null) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (baseResponse.getData() != null && !baseResponse.getData().isEmpty()) {
                        if (isRefresh) {
                            accountTurnoverAdapter.setNewData(baseResponse.getData());
                        } else {
                            accountTurnoverAdapter.addData(baseResponse.getData());
                        }
                        if (baseResponse.getData().size() < pageNumber) {
                            accountTurnoverAdapter.loadMoreEnd(true);
                        } else {
                            accountTurnoverAdapter.loadMoreComplete();
                        }
                    }
                    if (accountTurnoverAdapter.getData() == null || accountTurnoverAdapter.getData().isEmpty()) {
                        EmptyViewUtils.bindEmptyView(mContext, accountTurnoverAdapter);
                    }
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
                dismissLoadDialog();
            }
        });
    }


    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAccountTurnoverList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        accountTurnoverAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getAccountTurnoverList(false);
            }
        }, mBinding.recyclerView);
    }
}
