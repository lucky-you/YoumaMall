package com.zhowin.youmamall.home.activity;


import android.view.View;

import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivitySearchBinding;

/**
 * 搜索
 */
public class SearchActivity extends BaseBindActivity<ActivitySearchBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        setOnClick(R.id.ivBack, R.id.tvSearch);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                ActivityManager.getAppInstance().finishActivity();
                break;
            case R.id.tvSearch:
                break;
        }
    }
}
