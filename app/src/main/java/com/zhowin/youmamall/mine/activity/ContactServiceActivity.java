package com.zhowin.youmamall.mine.activity;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ClipboardUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityContactServiceBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.adapter.ContactServiceAdapter;
import com.zhowin.youmamall.mine.model.ContactServiceList;

import java.util.ArrayList;
import java.util.List;

public class ContactServiceActivity extends BaseBindActivity<ActivityContactServiceBinding> implements BaseQuickAdapter.OnItemClickListener {

    private ContactServiceAdapter contactServiceAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_contact_service;
    }

    @Override
    public void initView() {
        getContactServiceList();
        contactServiceAdapter = new ContactServiceAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(contactServiceAdapter);
        contactServiceAdapter.setOnItemClickListener(this::onItemClick);
    }

    @Override
    public void initData() {

    }

    private void getContactServiceList() {
        showLoadDialog();
        HttpRequest.getContactServiceList(this, new HttpCallBack<List<ContactServiceList>>() {
            @Override
            public void onSuccess(List<ContactServiceList> contactServiceLists) {
                dismissLoadDialog();
                contactServiceAdapter.setNewData(contactServiceLists);
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
        String content = contactServiceAdapter.getItem(position).getValue();
        ClipboardUtils.copyText(content);
        ToastUtils.showToast("已复制到粘贴板");
    }
}
