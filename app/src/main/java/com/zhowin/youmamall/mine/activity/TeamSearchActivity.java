package com.zhowin.youmamall.mine.activity;


import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.KeyboardUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityTeamSearchBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.adapter.MyTeamAdapter;
import com.zhowin.youmamall.mine.model.MyTeamInfo;

import java.util.ArrayList;

/**
 * 搜索团队
 */
public class TeamSearchActivity extends BaseBindActivity<ActivityTeamSearchBinding> implements BaseQuickAdapter.OnItemClickListener {

    private MyTeamAdapter myTeamAdapter;
    private String mobile;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_search;
    }

    @Override
    public void initView() {
        setOnClick(R.id.ivBackTeam, R.id.tvSearch);
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
        HttpRequest.getMyTeamList(this, mobile, currentPage, pageNumber, new HttpCallBack<MyTeamInfo>() {
            @Override
            public void onSuccess(MyTeamInfo baseResponse) {
                dismissLoadDialog();
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
                }
                if (myTeamAdapter.getData() == null || myTeamAdapter.getData().isEmpty()) {
                    EmptyViewUtils.bindEmptyView(mContext, myTeamAdapter);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBackTeam:
                ActivityManager.getAppInstance().finishActivity();
                break;
            case R.id.tvSearch:
                mobile = mBinding.editKeyWord.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtils.showToast("请输入您要搜索的手机号");
                    return;
                }
                KeyboardUtils.hideSoftInput(mContext);
                getMyTeamList(true);
                break;
        }
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
