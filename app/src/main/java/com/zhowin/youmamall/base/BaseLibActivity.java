package com.zhowin.youmamall.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.zhowin.base_library.R;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.KeyboardUtils;
import com.zhowin.base_library.view.LoadProgressDialog;
import com.zhowin.youmamall.login.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseLibActivity extends SupportActivity implements View.OnClickListener {
    protected AppCompatActivity mContext;
    protected LoadProgressDialog progressDialog;

    protected int totalPage;//总页数
    protected int currentPage = 0;//当前页数
    protected int pageNumber = 10; //每一页的个数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   //设定为竖屏
        setContent();
        ActivityManager.getAppInstance().addActivity(this);
        mContext = this;
        initView();
        initImmersionBar();
        initData();
        initListener();
        KeyboardUtils.fixAndroidBug5497(mContext);
        KeyboardUtils.fixSoftInputLeaks(mContext);
    }

    public void setContent() {
        setContentView(getLayoutId());
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public void initListener() {

    }

    public <E extends View> E get(int id) {
        return (E) findViewById(id);
    }


    public void registerEvent() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    public void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.color_FFDE00)
                .keyboardEnable(true)
                .statusBarDarkFont(true, 0f)
                .init();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        ActivityManager.getAppInstance().removeActivity(this);
    }

    public int getBaseColor(int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    public Resources getBaseResources() {
        return mContext.getResources();
    }


    public LoadProgressDialog showLoadDialog() {
        return showLoadDialog(mContext.getString(R.string.Loading));
    }

    /**
     * 显示对话框
     */
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

    /**
     * 跳转外部链接
     */
    protected void jumpToAndroidBrowser(String webUrl) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_uri_browsers = Uri.parse(webUrl);
        intent.setData(content_uri_browsers);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }

    public void setOnClick(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            get(ids[i]).setOnClickListener(this);
        }
    }
}
