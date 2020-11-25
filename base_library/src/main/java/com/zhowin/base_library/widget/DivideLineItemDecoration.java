package com.zhowin.base_library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Effect: RecyclerView的分割线
 */

public class DivideLineItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private int mOrientation;//方向

    private int mSize;//线宽

    private Paint mPaint; //画笔

    /**
     * 默认是垂直方向
     *
     * @param context   上下文
     * @param color     颜色
     * @param mItemSize 线宽
     */
    public DivideLineItemDecoration(Context context, int color, int mItemSize) {
        this.mSize = mItemSize;
        mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mItemSize, context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mOrientation = VERTICAL_LIST;   //默认是垂直方向
    }

    /**
     * @param context     上下文
     * @param orientation 方向
     * @param color       颜色
     * @param mItemSize   线宽
     */
    public DivideLineItemDecoration(Context context, int orientation, int color, int mItemSize) {
        this.mSize = mItemSize;
        mSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mItemSize, context.getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);  //设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        setOrientation(orientation);
    }

    /**
     * 设置方向
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(canvas, parent);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    /**
     * 绘制纵向
     *
     * @param canvas
     * @param parent
     */
    public void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 绘制横向
     *
     * @param canvas
     * @param parent
     */
    public void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount-1; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }


    }

    /**
     * 偏移量
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            int lastPosition = state.getItemCount() - 1;
            int position = parent.getChildAdapterPosition(view);
            if (position < lastPosition) {
                outRect.set(0, 0, 0, mSize);
            } else {
                outRect.set(0, 0, 0, 0);
            }
        } else {
            outRect.set(0, 0, mSize, 0);
        }
    }
}
