package com.zhowin.base_library.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zhowin.base_library.R;
import com.zhowin.base_library.utils.ScreenUtils;
import com.zhowin.base_library.view.LoadProgressDialog;

/**
 * author Z_B
 * date :2018/11/25 8:47
 * description:BottomSheetDialogFragment 的基类
 */
public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    protected Context mContext;
    protected View rootView;
    protected BottomSheetBehavior mBehavior;
    protected Dialog dialog;
    protected LoadProgressDialog progressDialog;
    private long lastClick = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (rootView == null) {
            //缓存下来的View 当为空时才需要初始化 并缓存
            rootView = View.inflate(mContext, getLayoutResId(), null);
            initView();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (rootView == null) {
            //缓存下来的View 当为空时才需要初始化 并缓存
            rootView = View.inflate(mContext, getLayoutResId(), null);
            initView();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mBehavior != null)
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除缓存View和当前ViewGroup的关联
        ((ViewGroup) (rootView.getParent())).removeView(rootView);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (isRemoveMask()) {
            //设置弹起时去掉背景的遮罩效果
            setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.TransBottomSheetDialogStyle);
        }
        //每次打开都调用该方法 类似于onCreateView 用于返回一个Dialog实例
        dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        if (isFixedHeight()) {
            //固定高度
            int screenHeight = ScreenUtils.getScreenHeight();
            int height = (screenHeight * 3) >> 2;//屏幕高的75%
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, height);
            dialog.setContentView(rootView, layoutParams);//设置View 并带有布局参数的
        } else {
            //自适应高度,设置View重新关联
            setContentView(dialog);
        }
        mBehavior = BottomSheetBehavior.from((View) rootView.getParent());
        mBehavior.setHideable(true);
        //让父View背景透明 是圆角边的关键
        ((View) rootView.getParent()).setBackgroundColor(Color.TRANSPARENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        rootView.post(() -> {
            /**
             * PeekHeight默认高度256dp 会在该高度上悬浮
             * 设置等于view的高 就不会卡住
             */
            mBehavior.setPeekHeight(rootView.getHeight());
        });
        resetView();
        return dialog;
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


    public <E extends View> E get(int id) {
        return (E) rootView.findViewById(id);
    }

    /**
     * 设置显示的View到Dialog中
     * 抽象方法 子类可重写
     * 默认添加的View 高度为Wrap 某些场景需要固定高度
     */
    protected void setContentView(Dialog dialog) {
        dialog.setContentView(rootView);
    }

    /**
     * 获取资源id
     */
    public abstract int getLayoutResId();

    /**
     * 初始化View 和 设置数据等操作的方法
     */
    public abstract void initView();


    public abstract void onViewClick(View view);
    /**
     * 高度是否固定,默认不固定
     */
    protected boolean isFixedHeight() {
        return false;
    }

    /**
     * 是否去掉遮罩,默认不去掉
     */
    protected boolean isRemoveMask() {
        return false;
    }

    /**
     * 重置的View和数据的空方法 子类可以选择实现
     * 为避免多次inflate 父类缓存rootView
     * 所以不会每次打开都调用{@link #initView()}方法
     * 但是每次都会调用该方法 给子类能够重置View和数据
     */
    public void resetView() {

    }

    /**
     * 是否开启
     */
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 使用关闭弹框 是否使用动画可选
     * 使用动画 同时切换界面Aty会卡顿 建议直接关闭
     */
    public void close(boolean isAnimation) {
        if (isAnimation) {
            if (mBehavior != null) {
                mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        } else {
            dismiss();
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!this.isAdded()) {
            super.show(manager, tag);
        }
    }


    @Override
    public void onClick(View view) {
        if (!isFastClick())
            onViewClick(view);
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    protected boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 500) {
            lastClick = now;
            return false;
        }
        return true;
    }

}
