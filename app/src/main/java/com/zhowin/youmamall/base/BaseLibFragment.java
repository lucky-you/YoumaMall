package com.zhowin.youmamall.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.zhowin.base_library.R;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SPUtils;
import com.zhowin.base_library.view.LoadProgressDialog;
import com.zhowin.youmamall.login.activity.LoginActivity;

import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseLibFragment extends SupportFragment implements View.OnClickListener {

    public BaseLibActivity mContext;
    public BaseLibActivity mActivity;
    public View rootView;
    protected LoadProgressDialog progressDialog;
    protected int totalPage;//总页数
    protected int currentPage = 1;//当前页数
    protected int pageNumber = 10; //每一页的个数

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (BaseLibActivity) getActivity();
        mActivity = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setContent(inflater, container);
        return rootView;
    }

    public void setContent(LayoutInflater inflater, ViewGroup container) {
        rootView = View.inflate(mContext, getLayoutId(), null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.color_FFDE00)
                .statusBarDarkFont(true, 0f)
                .init();
    }


    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();


    public void initListener() {
    }

    public <E extends View> E get(int id) {
        return (E) rootView.findViewById(id);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initImmersionBar();
    }

    @Override
    public void onClick(View v) {

    }

    public void setOnClick(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            get(ids[i]).setOnClickListener(this);
        }
    }

    public int getBaseColor(int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.destroy(this);
    }

    public void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(mContext, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public boolean isLogin() {
        String userToken = UserInfo.getUserToken();
        if (TextUtils.isEmpty(userToken)) {
            startActivity(LoginActivity.class);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 显示对话框
     */
    public LoadProgressDialog showLoadDialog() {

        return showLoadDialog("");
    }

    public LoadProgressDialog showLoadDialog(String hitMessage) {
        if (progressDialog == null) {
            progressDialog = new LoadProgressDialog(mContext);
            if (TextUtils.isEmpty(hitMessage)) {
                progressDialog = progressDialog.createLoadingDialog(mContext.getString(R.string.Loading));
            } else {
                progressDialog = progressDialog.createLoadingDialog(hitMessage);
            }
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        return progressDialog;
    }

    /**
     * 关闭提示框
     */
    public void dismissLoadDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.stopAnimator();
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
    }
}
