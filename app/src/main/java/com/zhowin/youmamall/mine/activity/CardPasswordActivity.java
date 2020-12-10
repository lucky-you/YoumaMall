package com.zhowin.youmamall.mine.activity;


import android.text.TextUtils;
import android.view.View;

import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityCardPasswordBinding;
import com.zhowin.youmamall.http.HttpRequest;

/**
 * 卡密
 */
public class CardPasswordActivity extends BaseBindActivity<ActivityCardPasswordBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_card_password;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvCardPasswordLogin);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCardPasswordLogin:
                String password = mBinding.editPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.showToast("卡密不能为空");
                    return;
                }
                onEnterCardSecret(password);
                break;
        }
    }

    private void onEnterCardSecret(String content) {
        showLoadDialog();
        HttpRequest.onEnterCardSecret(this, 1, content, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                ToastUtils.showToast("录入成功");
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
