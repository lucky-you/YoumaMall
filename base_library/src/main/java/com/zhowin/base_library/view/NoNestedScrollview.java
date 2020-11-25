package com.zhowin.base_library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.core.widget.NestedScrollView;

import com.zhowin.base_library.callback.NestedScrollViewListener;


/**
 * function  : ScrollView屏蔽 滑动事件
 */
public class NoNestedScrollview extends NestedScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;
    private NestedScrollViewListener scrollViewListener = null;

    public NoNestedScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoNestedScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public NoNestedScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setFadingEdgeLength(0);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(e);
    }


    public void setScrollViewListener(NestedScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, scrollX, scrollY, oldScrollX, oldScrollY);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scrollViewListener != null) {
            scrollViewListener.onScroll(getScrollY());
        }
    }


}
