package com.zhowin.base_library.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhowin.base_library.R;



public class TitleView extends RelativeLayout {
    private View mRootView;
    private ImageView mBack;
    private TextView mTitle;
    private TextView mLeftText;
    private TextView mRightText;
    private ImageView mRightImage;
    private LinearLayout mRightLl;

    private int titleColor = Color.WHITE;
    private int mRightTextColor = Color.WHITE;
    private int mLeftTextColor = Color.WHITE;
    private int mBackGroundColor = Color.WHITE;
    private float titleSize = 16;
    private String title;
    private Drawable ivRightSrc;
    private Drawable ivLeftSrc;
    private boolean showBack;
    private String rightText;
    private String leftText;
    private int leftMargin;
    private InputMethodManager imm;

    public TitleView(Context context) {
        super(context);
        initView(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        titleColor = mTypedArray.getColor(R.styleable.TitleView_mTitleColor, Color.BLACK);
        titleSize = mTypedArray.getDimension(R.styleable.TitleView_mTitleSize, 16);
        title = mTypedArray.getString(R.styleable.TitleView_mTitle);
        ivRightSrc = mTypedArray.getDrawable(R.styleable.TitleView_mRightImage);
        ivLeftSrc = mTypedArray.getDrawable(R.styleable.TitleView_mLeftSrc);
        leftText = mTypedArray.getString(R.styleable.TitleView_mLeftText);
        rightText = mTypedArray.getString(R.styleable.TitleView_mRightText);
        mLeftTextColor = mTypedArray.getColor(R.styleable.TitleView_mLeftTextColor, Color.WHITE);
        leftMargin = (int) mTypedArray.getDimension(R.styleable.TitleView_mLeftTextMarginLeft, 0);
        mRightTextColor = mTypedArray.getColor(R.styleable.TitleView_mRightTextColor, Color.WHITE);
        mBackGroundColor = mTypedArray.getColor(R.styleable.TitleView_mBackgroundColor, Color.WHITE);
        showBack = mTypedArray.getBoolean(R.styleable.TitleView_mLeftVisible, true);
        mTypedArray.recycle();
        initView(context);
    }

    private void initView(final Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.include_title_view_layout, this, true);
        mRootView.setBackgroundColor(mBackGroundColor);
        mBack = findViewById(R.id.back);
        mTitle = findViewById(R.id.title);
        mLeftText = findViewById(R.id.left_tv);
        mRightText = findViewById(R.id.ritht_tv);
        mRightLl = findViewById(R.id.ll_right);
        mRightImage = findViewById(R.id.ritht_iv);
        mBack.setVisibility(showBack ? VISIBLE : GONE);
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mTitle.setText(title);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
        mTitle.setTextColor(titleColor);
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(((Activity) context));
                ((Activity) context).finish();
            }
        });
        setMargins(mLeftText, leftMargin, 0, 0, 0);
        mLeftText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(((Activity) context));
                ((Activity) context).finish();
            }
        });
        if (ivRightSrc != null) {
            mRightImage.setImageDrawable(ivRightSrc);
        }
        if (rightText != null) {
            mRightText.setText(rightText);
            mRightText.setTextColor(mRightTextColor);
        }
        if (leftText != null) {
            mLeftText.setText(leftText);
            mLeftText.setTextColor(mLeftTextColor);
        }
        if (ivLeftSrc != null) {
            mBack.setImageDrawable(ivLeftSrc);
        }
        if (showBack) {
            setMargins(mLeftText, -30, 0, 0, 0);
        }
    }

    public TextView setRightTextViewClick(OnClickListener listener) {
        mRightText.setOnClickListener(listener);
        return mRightText;
    }

    public TextView setLeftTextViewClick(OnClickListener listener) {
        mLeftText.setOnClickListener(listener);
        return mLeftText;
    }

    public void setRightImageViewClick(OnClickListener listener) {
        mRightImage.setOnClickListener(listener);
    }

    public TextView getLeftTextView() {
        return mLeftText;
    }

    public TextView getRightTextView() {
        return mRightText;
    }

    public ImageView getRightImage() {
        return mRightImage;
    }

    public TextView getTitle() {
        return mTitle;
    }

    public LinearLayout getRightLl() {
        return mRightLl;
    }

    public void setTitle(String title) {
        mTitle.setText(title);
        this.title = title;
    }

    public void setLeftText(String str) {
        mLeftText.setText(str);
        this.leftText = str;
    }

    public void setLeftTxMargin(int margin) {
        setMargins(mLeftText, margin, 0, 0, 0);
        this.leftMargin = margin;
    }

    public void setLeftImgShow(boolean isShow) {
        mBack.setVisibility(isShow ? VISIBLE : GONE);
        this.showBack = isShow;
    }

    public void setRightTxColor(int color) {
        mRightText.setTextColor(color);
        this.mRightTextColor = color;
    }

    public void setRightTxColor(ColorStateList color) {
        mRightText.setTextColor(color);
    }

    public void setRightText(String str) {
        mRightText.setText(str);
        this.rightText = str;
    }

    public void setBackListener(OnClickListener listener) {
        mBack.setOnClickListener(listener);
    }

    public void setmRootViewBackGround(int color) {
        mRootView.setBackgroundColor(color);
    }

    private void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams p = (MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    private void hideSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
