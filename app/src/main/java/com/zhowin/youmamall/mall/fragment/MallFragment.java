package com.zhowin.youmamall.mall.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.GridSpacesItemDecoration;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeMallFragmentLayoutBinding;
import com.zhowin.youmamall.home.activity.SearchActivity;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.adapter.MallLeftListAdapter;
import com.zhowin.youmamall.mall.adapter.MallRightListAdapter;
import com.zhowin.youmamall.mall.model.MallLeftList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class MallFragment extends BaseBindFragment<IncludeMallFragmentLayoutBinding> {

    private MallLeftListAdapter mallLeftListAdapter;
    private MallRightListAdapter mallRightListAdapter;

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
        List<String> leftItemList = Arrays.asList("安卓软件", "电脑软件", "云端软件", "云端秒抢", "快手抖音", "会员福利", "图视工具", "直播影视", "云端秒抢", "快手抖音", "云端秒抢", "会员福利", "图视工具", "直播影视");

        mallLeftListAdapter = new MallLeftListAdapter(new ArrayList<>(), 0);
        mBinding.leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.leftRecyclerView.setAdapter(mallLeftListAdapter);

        mallRightListAdapter = new MallRightListAdapter(leftItemList);
        mBinding.rightRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mBinding.rightRecyclerView.setAdapter(mallRightListAdapter);


    }

    private void getMallLeftList() {
        HttpRequest.getMallLeftList(this, new HttpCallBack<List<MallLeftList>>() {
            @Override
            public void onSuccess(List<MallLeftList> mallLeftLists) {
                mallLeftListAdapter.setNewData(mallLeftLists);
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
                startActivity(SearchActivity.class);
            }
        });

        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        mallLeftListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mallLeftListAdapter.setCurrentPosition(position);
            }
        });
    }
}
