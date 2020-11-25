package com.zhowin.base_library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhowin.base_library.R;


/**
 * 空布局
 */
public class EmptyView extends FrameLayout {

    private ImageView ivHint;
    private TextView tvHint;

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View.inflate(getContext(), R.layout.include_empty_view_layout, this);
        ivHint = findViewById(R.id.ivHitImage);
        tvHint = findViewById(R.id.tvHitText);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    /**
     * 设置提示信息
     */
    public EmptyView setText(String text) {
        tvHint.setText(text);
        return this;
    }

    /**
     * 设置资源图片
     */
    public EmptyView setImageResource(int resId) {
        ivHint.setImageResource(resId);
        return this;
    }

}
