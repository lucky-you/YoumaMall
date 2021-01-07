package com.zhowin.youmamall.circle.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.circle.activity.ReleaseCircleActivity;
import com.zhowin.youmamall.circle.adapter.CircleFragmentAdapter;
import com.zhowin.youmamall.circle.callback.OnCircleItemClickListener;
import com.zhowin.youmamall.circle.model.CircleList;
import com.zhowin.youmamall.databinding.IncludeCircleFragmentLayoutBinding;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：圈子
 */
public class CircleFragment extends BaseBindFragment<IncludeCircleFragmentLayoutBinding> implements OnCircleItemClickListener {

    private CircleFragmentAdapter circleFragmentAdapter;
    private int fragmentIndex;

    public static CircleFragment newInstance(int type) {
        CircleFragment circleFragment = new CircleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantValue.TYPE, type);
        circleFragment.setArguments(bundle);
        return circleFragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.include_circle_fragment_layout;
    }

    @Override
    public void initView() {
        fragmentIndex = getArguments().getInt(ConstantValue.TYPE);
        mBinding.tvTitleView.setVisibility(1 == fragmentIndex ? View.VISIBLE : View.GONE);
        getCircleList(true);
    }

    @Override
    public void initData() {
        circleFragmentAdapter = new CircleFragmentAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(circleFragmentAdapter);
        circleFragmentAdapter.setOnCircleItemClickListener(this::onImageItemClick);
    }


    private void getCircleList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        HttpRequest.getCircleList(this, currentPage, pageNumber, new HttpCallBack<BaseResponse<CircleList>>() {
            @Override
            public void onSuccess(BaseResponse<CircleList> baseResponse) {
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        circleFragmentAdapter.setNewData(baseResponse.getData());
                    } else {
                        circleFragmentAdapter.addData(baseResponse.getData());
                    }

                    if (baseResponse.getData().size() < pageNumber) {
                        circleFragmentAdapter.loadMoreEnd(true);
                    } else {
                        circleFragmentAdapter.loadMoreComplete();
                    }
                } else {
                    EmptyViewUtils.bindEmptyView(mContext, circleFragmentAdapter);
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
                getCircleList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        circleFragmentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getCircleList(false);
            }
        }, mBinding.recyclerView);

        mBinding.tvTitleView.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin())
                    startActivity(ReleaseCircleActivity.class);
            }
        });
    }

    @Override
    public void onImageItemClick(List<String> imageList, int position) {
        PictureSelectorUtils.previewPicture(mContext, position, imageList);
    }
}
