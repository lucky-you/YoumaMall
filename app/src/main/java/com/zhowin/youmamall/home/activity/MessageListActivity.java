package com.zhowin.youmamall.home.activity;


import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityMessageListBinding;
import com.zhowin.youmamall.home.adapter.MessageListAdapter;
import com.zhowin.youmamall.home.model.MessageList;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 消息列表
 */
public class MessageListActivity extends BaseBindActivity<ActivityMessageListBinding> {


    private MessageListAdapter messageListAdapter;
    private String categoryTitle;
    private int categoryType;

    public static void start(Context context, String title, int type) {
        Intent intent = new Intent(context, MessageListActivity.class);
        intent.putExtra(ConstantValue.TITLE, title);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    public void initView() {
        categoryTitle = getIntent().getStringExtra(ConstantValue.TITLE);
        categoryType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        mBinding.tvTitleView.setTitle(categoryTitle);
        getMessageList(true);
    }

    @Override
    public void initData() {
        messageListAdapter = new MessageListAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(messageListAdapter);
    }

    private void getMessageList(boolean isRefresh) {
        if (isRefresh) currentPage = 1;
        showLoadDialog();
        HttpRequest.getMessageList(this, categoryType, currentPage, pageNumber, new HttpCallBack<BaseResponse<MessageList>>() {
            @Override
            public void onSuccess(BaseResponse<MessageList> baseResponse) {
                dismissLoadDialog();
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        messageListAdapter.setNewData(baseResponse.getData());
                    } else {
                        messageListAdapter.addData(baseResponse.getData());
                    }
                    if (baseResponse.getData().size() <= pageNumber) {
                        messageListAdapter.loadMoreEnd(true);
                    } else {
                        messageListAdapter.loadMoreComplete();
                    }
                } else {
                    EmptyViewUtils.bindEmptyView(mContext, messageListAdapter);
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
                getMessageList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        messageListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMessageList(false);
            }
        }, mBinding.recyclerView);
    }
}
