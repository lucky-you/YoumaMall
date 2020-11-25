package com.zhowin.base_library.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class HomePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    public String[] titles;
    public List<String> titleList;


    public HomePageAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> titleList) {
        super(fm);
        this.mFragments = mFragments;
        this.titleList = titleList;
    }

    public HomePageAdapter(FragmentManager fm, List<Fragment> mFragments, String[] titles) {
        super(fm);
        this.mFragments = mFragments;
        this.titles = titles;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles[position];
        } else {
            return titleList.get(position);
        }
    }
}
