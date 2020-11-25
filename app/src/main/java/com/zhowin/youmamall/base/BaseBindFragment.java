package com.zhowin.youmamall.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


public abstract class BaseBindFragment<VDB extends ViewDataBinding> extends BaseLibFragment {
    protected VDB mBinding;
    @Override
    public void setContent(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        rootView=mBinding.getRoot();
    }
}
