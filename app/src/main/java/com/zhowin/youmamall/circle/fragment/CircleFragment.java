package com.zhowin.youmamall.circle.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.circle.activity.ReleaseCircleActivity;
import com.zhowin.youmamall.circle.adapter.CircleFragmentAdapter;
import com.zhowin.youmamall.databinding.IncludeCircleFragmentLayoutBinding;

import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class CircleFragment extends BaseBindFragment<IncludeCircleFragmentLayoutBinding> {

    private CircleFragmentAdapter circleFragmentAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.include_circle_fragment_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<String> imageList = Arrays.asList("", "", "", "", "", "", "");
        circleFragmentAdapter = new CircleFragmentAdapter(imageList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(circleFragmentAdapter);

    }


    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        mBinding.tvTitleView.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ReleaseCircleActivity.class);
            }
        });
    }
}
