package com.zhowin.base_library.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.zhowin.base_library.utils.SizeUtils;


public abstract class BaseDialogView extends Dialog implements View.OnClickListener {


    private long lastClick = 0;
    protected View rootView;
    protected Context mContext;

    public BaseDialogView(@NonNull Context context) {
        super(context);
        mContext = context;
        rootView = View.inflate(context, getLayoutId(), null);
        setContentView(rootView);
        initView();
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = SizeUtils.dp2px(300);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initData();
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
}
