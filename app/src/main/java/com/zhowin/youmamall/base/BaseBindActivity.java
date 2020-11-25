package com.zhowin.youmamall.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseBindActivity <VDB extends ViewDataBinding>extends BaseLibActivity {
    protected VDB mBinding;
    @Override
    public void setContent() {
        mBinding = DataBindingUtil.setContentView(this,getLayoutId());
    }
}
