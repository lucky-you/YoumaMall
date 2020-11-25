package com.zhowin.base_library.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * ItemDecoration的间距
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int bottom;
    private int right;
    private int top;

    public GridSpacesItemDecoration(int left, int bottom, int right, int top) {
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.bottom = bottom;
        outRect.right = right;
        outRect.top = top;

        if (parent.getChildPosition(view) == 0) {
            outRect.top = top;
        } else {
            outRect.top = 0;
        }
    }
}
