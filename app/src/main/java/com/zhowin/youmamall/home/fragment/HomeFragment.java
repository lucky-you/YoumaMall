package com.zhowin.youmamall.home.fragment;

import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhowin.base_library.adapter.HomePageAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.circle.fragment.CircleFragment;
import com.zhowin.youmamall.databinding.IncludeHomeFragmentLayoutBinding;
import com.zhowin.youmamall.home.activity.MessageCategoryActivity;
import com.zhowin.youmamall.home.activity.MessageListActivity;
import com.zhowin.youmamall.home.activity.SearchActivity;
import com.zhowin.youmamall.home.dialog.LatestNewDialog;
import com.zhowin.youmamall.home.model.UnreadMessageInfo;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：资源
 */
public class HomeFragment extends BaseBindFragment<IncludeHomeFragmentLayoutBinding> implements OnTabSelectListener {


    private String[] mTitles = {"资源", "首页", "圈子"};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.include_home_fragment_layout;
    }

    @Override
    public void initView() {
        setOnClick(R.id.ivSearch, R.id.clMessageLayout);
    }

    @Override
    public void initData() {
        fragments.add(new HomeTaskFragment());
        fragments.add(new HomePageFragment());
        fragments.add(CircleFragment.newInstance(2));
        HomePageAdapter homePageAdapter = new HomePageAdapter(getChildFragmentManager(), fragments, mTitles);
        mBinding.noScrollViewPager.setAdapter(homePageAdapter);
        mBinding.noScrollViewPager.setOffscreenPageLimit(mTitles.length);
        mBinding.slidingTabLayout.setViewPager(mBinding.noScrollViewPager);
        mBinding.slidingTabLayout.setOnTabSelectListener(this);
        mBinding.slidingTabLayout.setCurrentTab(1);
        setTabSelect(1, true);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(UserInfo.getUserToken()))
            getUnreadMessageInfo();
    }

    private void setTabSelect(int position, boolean select) {
        mBinding.slidingTabLayout.getTitleView(position).setTextSize(TypedValue.COMPLEX_UNIT_PX, SizeUtils.sp2px(select ? 18 : 15));
        mBinding.slidingTabLayout.getTitleView(position).getPaint().setFakeBoldText(select);
    }

    @Override
    public void onTabSelect(int position) {
        int count = mBinding.slidingTabLayout.getTabCount();
        for (int i = 0; i < count; i++) {
            setTabSelect(i, false);
        }
        setTabSelect(position, true);

    }

    @Override
    public void onTabReselect(int position) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSearch:
                if (!isLogin())
                    startActivity(SearchActivity.class);
                break;
            case R.id.clMessageLayout:
                if (!isLogin())
                    startActivity(MessageCategoryActivity.class);
                break;
        }
    }

    private void getUnreadMessageInfo() {
        HttpRequest.getUnreadMessageInfo(this, new HttpCallBack<UnreadMessageInfo>() {
            @Override
            public void onSuccess(UnreadMessageInfo unreadMessageInfo) {
                if (unreadMessageInfo != null) {
                    mBinding.msvMessage.setVisibility(unreadMessageInfo.getRead_num() > 0 ? View.VISIBLE : View.GONE);
                    mBinding.msvMessage.setText(String.valueOf(unreadMessageInfo.getRead_num()));

                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

            }
        });
    }

}
