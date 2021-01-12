package com.zhowin.youmamall.home.activity;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityMessageCategoryBinding;
import com.zhowin.youmamall.home.adapter.MessageCategoryAdapter;
import com.zhowin.youmamall.home.model.MessageCategory;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息分类
 */
public class MessageCategoryActivity extends BaseBindActivity<ActivityMessageCategoryBinding> implements BaseQuickAdapter.OnItemClickListener {


    private MessageCategoryAdapter messageCategoryAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_category;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        int[] leftDrawableId = {R.drawable.settlement_icon, R.drawable.profittip_icon, R.drawable.registered_icon, R.drawable.notice_icon};
        messageCategoryAdapter = new MessageCategoryAdapter(new ArrayList<>());
        messageCategoryAdapter.setLeftDrawableId(leftDrawableId);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(messageCategoryAdapter);
        messageCategoryAdapter.setOnItemClickListener(this::onItemClick);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessageCategory();
    }

    private void getMessageCategory() {
        showLoadDialog();
        HttpRequest.getMessageCategory(this, new HttpCallBack<List<MessageCategory>>() {
            @Override
            public void onSuccess(List<MessageCategory> messageCategories) {
                dismissLoadDialog();
                messageCategoryAdapter.setNewData(messageCategories);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String itemTitle = messageCategoryAdapter.getItem(position).getName();
        int itemType = messageCategoryAdapter.getItem(position).getType();
        MessageListActivity.start(mContext, itemTitle, itemType);
    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessageCategory();
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        mBinding.tvTitleView.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllReadMessage();
            }
        });
    }

    private void setAllReadMessage() {
        showLoadDialog();
        HttpRequest.setAllReadMessage(this, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                getMessageCategory();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
