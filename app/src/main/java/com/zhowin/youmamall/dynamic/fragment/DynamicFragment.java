package com.zhowin.youmamall.dynamic.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.circle.adapter.CircleFragmentAdapter;
import com.zhowin.youmamall.databinding.IncludeDynamicFragmentLayoutBinding;
import com.zhowin.youmamall.dynamic.adapter.DynamicFragmentAdapter;
import com.zhowin.youmamall.dynamic.callback.OnDynamicItemClickListener;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class DynamicFragment extends BaseBindFragment<IncludeDynamicFragmentLayoutBinding> implements OnDynamicItemClickListener {
    private DynamicFragmentAdapter dynamicFragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.include_dynamic_fragment_layout;
    }

    @Override
    public void initView() {
        getDynamicList(true);
    }

    @Override
    public void initData() {
        dynamicFragmentAdapter = new DynamicFragmentAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(dynamicFragmentAdapter);
        dynamicFragmentAdapter.setOnDynamicItemClickListener(this);
    }


    private void getDynamicList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        HttpRequest.getDynamicList(this, currentPage, pageNumber, new HttpCallBack<BaseResponse<DynamicList>>() {
            @Override
            public void onSuccess(BaseResponse<DynamicList> baseResponse) {
                if (baseResponse != null) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        dynamicFragmentAdapter.setNewData(baseResponse.getData());
                    } else {
                        dynamicFragmentAdapter.addData(baseResponse.getData());
                    }

                    if (baseResponse.getData().size() < pageNumber) {
                        dynamicFragmentAdapter.loadMoreEnd(true);
                    } else {
                        dynamicFragmentAdapter.loadMoreComplete();
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
                getDynamicList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        dynamicFragmentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDynamicList(false);
            }
        }, mBinding.recyclerView);
    }

    @Override
    public void onSavePhoto(List<String> images) {

    }

    @Override
    public void onCopyContent(String content) {

    }

    @Override
    public void onImageItemClick(List<String> imageList, int position) {
        PictureSelectorUtils.previewPicture(mContext, position, imageList);
    }
}
