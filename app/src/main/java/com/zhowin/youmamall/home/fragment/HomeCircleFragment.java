package com.zhowin.youmamall.home.fragment;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.circle.adapter.CircleFragmentAdapter;
import com.zhowin.youmamall.circle.callback.OnCircleItemClickListener;
import com.zhowin.youmamall.circle.model.CircleList;
import com.zhowin.youmamall.databinding.IncludeHomeCircleFragmentBinding;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class HomeCircleFragment extends BaseBindFragment<IncludeHomeCircleFragmentBinding> implements OnCircleItemClickListener {

    private CircleFragmentAdapter circleFragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.include_home_circle_fragment;
    }

    @Override
    public void initView() {
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
                if (baseResponse != null) {
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
                }
                if (circleFragmentAdapter.getData() == null || circleFragmentAdapter.getData().isEmpty()) {
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

    }

    @Override
    public void onImageItemClick(List<String> imageList, int position) {
        PictureSelectorUtils.previewPicture(mContext, position, imageList);
    }
}
