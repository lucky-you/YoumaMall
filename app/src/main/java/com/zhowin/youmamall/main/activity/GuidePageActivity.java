package com.zhowin.youmamall.main.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.zhowin.base_library.adapter.GuidePagesAdapter;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SPUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityGuidePageBinding;
import com.zhowin.youmamall.login.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 */
public class GuidePageActivity extends BaseBindActivity<ActivityGuidePageBinding> implements ViewPager.OnPageChangeListener {


    private List<View> viewList = new ArrayList<>();
    private ImageView[] viewPointList;
    private GuidePagesAdapter guidePagesAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    public void initView() {
        setOnClick(R.id.tvJumpSkip, R.id.tvStartUsing);
    }

    @Override
    public void initData() {
        viewList.add(View.inflate(mContext, R.layout.include_guide_item_one_layout, null));
        viewList.add(View.inflate(mContext, R.layout.include_guide_item_two_layout, null));
        viewList.add(View.inflate(mContext, R.layout.include_guide_item_three_layout, null));
        if (!viewList.isEmpty()) {
            viewPointList = new ImageView[viewList.size()];
            for (int i = 0; i < viewList.size(); i++) {
                ImageView viewPoint = new ImageView(mContext);
                if (i == 0) {
                    viewPoint.setBackgroundResource(R.drawable.shape_guide_page_select_bg);
                } else {
                    viewPoint.setBackgroundResource(R.drawable.shape_guide_page_not_select_bg);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i != 0) params.leftMargin = SizeUtils.dp2px(10);
                viewPoint.setLayoutParams(params);
                viewPointList[i] = viewPoint;
                mBinding.llPointsLayout.addView(viewPoint);
            }
        }

        guidePagesAdapter = new GuidePagesAdapter(viewList);
        mBinding.mViewPager.setAdapter(guidePagesAdapter);
        mBinding.mViewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvJumpSkip:
            case R.id.tvStartUsing:
                SPUtils.set(ConstantValue.START_MAIN, true);
                startActivity(MainActivity.class);
                ActivityManager.getAppInstance().finishActivity();
                break;
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.white)
                .keyboardEnable(true)
                .statusBarDarkFont(true)
                .init();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < viewList.size(); i++) {
            if (position == i) {
                viewPointList[i].setBackgroundResource(R.drawable.shape_guide_page_select_bg);
            } else {
                viewPointList[i].setBackgroundResource(R.drawable.shape_guide_page_not_select_bg);
            }
        }
        mBinding.tvStartUsing.setVisibility(position == viewList.size() - 1 ? View.VISIBLE : View.GONE);
        mBinding.llPointsLayout.setVisibility(position == viewList.size() - 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
