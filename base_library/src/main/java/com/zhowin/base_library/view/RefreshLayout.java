package com.zhowin.base_library.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.zhowin.base_library.R;


public class RefreshLayout extends SwipeRefreshLayout {

    public RefreshLayout(@NonNull Context context) {
        super(context);
    }

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.color_FFDE00, R.color.color_FFDE00, R.color.color_FFDE00);
    }
}
