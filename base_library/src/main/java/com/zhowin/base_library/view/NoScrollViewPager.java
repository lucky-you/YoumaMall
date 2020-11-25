package com.zhowin.base_library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 描述： 禁止滑动的ViewPager
 */

public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }


    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {

        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    /**
     * dispatchTouchEvent一般情况不做处理
     * ,如果修改了默认的返回值,子孩子都无法收到事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll && super.onInterceptTouchEvent(ev);
    }

    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往父控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !isScroll || super.onTouchEvent(ev);
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

}
