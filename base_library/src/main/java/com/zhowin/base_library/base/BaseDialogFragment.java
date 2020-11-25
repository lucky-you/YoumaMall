package com.zhowin.base_library.base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import com.zhowin.base_library.R;


/**
 * author Z_B
 * date :2019/7/8 10:49
 * description: DialogFragment 的基类
 */
public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener {

    protected Activity mContext;
    protected Dialog createDialog;
    protected View rootView;
    private long lastClick = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.mContext = this.getActivity();
        createDialog = new Dialog(mContext, R.style.DialogFragmentStyle);
        rootView = View.inflate(mContext, getLayoutId(), null);
        createDialog.setContentView(rootView);
        initView();
        Window window = createDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initData();
        return createDialog;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    public abstract void onViewClick(View view);


    public <E extends View> E get(int id) {
        return (E) rootView.findViewById(id);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (createDialog != null && createDialog.isShowing())
            createDialog.dismiss();
        this.mContext = null;
    }

}
