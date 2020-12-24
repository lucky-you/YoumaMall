package com.zhowin.youmamall.mine.activity;


import android.text.TextUtils;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityFeedbackBinding;
import com.zhowin.youmamall.http.HttpRequest;

/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseBindActivity<ActivityFeedbackBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvConfirmSubmission);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvConfirmSubmission:
                submitFeedback();
                break;
        }
    }

    private void submitFeedback() {
        String content = mBinding.editContent.getText().toString().trim();
        String contact = mBinding.editContact.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast("请输入反馈内容");
            return;
        }
        if (TextUtils.isEmpty(contact)) {
            ToastUtils.showToast("请输入联系方式");
            return;
        }
        showLoadDialog();
        HttpRequest.submitFeedback(this, content, contact, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                ToastUtils.showCustomToast(mContext, "提交成功");
                ActivityManager.getAppInstance().finishActivity();
            }
            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
