package com.zhowin.youmamall.mine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityMyTeamBinding;
import com.zhowin.youmamall.home.activity.SearchActivity;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.adapter.CouponListAdapter;
import com.zhowin.youmamall.mine.adapter.MyTeamAdapter;
import com.zhowin.youmamall.mine.model.MyTeamInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的团队
 */
public class MyTeamActivity extends BaseBindActivity<ActivityMyTeamBinding> implements BaseQuickAdapter.OnItemClickListener {

    private MyTeamAdapter myTeamAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    public void initView() {
        getMyTeamList(true);
    }

    @Override
    public void initData() {
        myTeamAdapter = new MyTeamAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(myTeamAdapter);
        myTeamAdapter.setOnItemClickListener(this::onItemClick);
    }

    private void getMyTeamList(boolean isRefresh) {
        showLoadDialog();
        if (isRefresh) currentPage = 1;
        HttpRequest.getMyTeamList(this, "", currentPage, pageNumber, new HttpCallBack<MyTeamInfo>() {
            @Override
            public void onSuccess(MyTeamInfo baseResponse) {
                dismissLoadDialog();
                mBinding.mvZXNumber.setVisibility(baseResponse.getTeam_size() > 0 ? View.VISIBLE : View.GONE);
                mBinding.mvZXNumber.setText(String.valueOf(baseResponse.getDirectly_size()));
                mBinding.mvTDNumber.setVisibility(baseResponse.getDirectly_size() > 0 ? View.VISIBLE : View.GONE);
                mBinding.mvTDNumber.setText(String.valueOf(baseResponse.getTeam_size()));
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        myTeamAdapter.setNewData(baseResponse.getData());
                    } else {
                        myTeamAdapter.addData(baseResponse.getData());
                    }
                    if (baseResponse.getData().size() < pageNumber) {
                        myTeamAdapter.loadMoreEnd(true);
                    } else {
                        myTeamAdapter.loadMoreComplete();
                    }
                } else {
                    View emptyView = View.inflate(mContext, R.layout.include_empty_view_layout, null);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(500));
                    emptyView.setLayoutParams(layoutParams);
                    myTeamAdapter.setEmptyView(emptyView);
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
                getMyTeamList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        myTeamAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMyTeamList(false);
            }
        }, mBinding.recyclerView);

        mBinding.tvTitleView.getRightImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TeamSearchActivity.class);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String mobile = myTeamAdapter.getItem(position).getMobile();
        String hitMessage = "确定要拨打" + mobile + "吗?";
        new CenterHitMessageDialog(mContext, hitMessage, new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {

            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                callPhone(mobile);
            }
        }).show();
    }


}
