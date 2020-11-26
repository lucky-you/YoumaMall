package com.zhowin.youmamall.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.model.HomePageList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class HomeFragmentAdapter extends BaseQuickAdapter<HomePageList, BaseViewHolder> {

    public HomeFragmentAdapter(@Nullable List<HomePageList> data) {
        super(R.layout.include_home_fragment_list_item_layout, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomePageList item) {

        RecyclerView FeaturesRecyclerView = helper.getView(R.id.FeaturesRecyclerView);
        helper.setText(R.id.tvLeftTitle, item.getLeftTitle())
                .setText(R.id.tvLeftDesc, item.getLeftDesc())
                .setGone(R.id.tvRightSeeMore, item.isShowRight());

        switch (item.getItemType()) {
            case 1:
                List<String> stringListOne = Arrays.asList("20.50", "45.00", "30.00");
                HomeRXBAdapter homeRXBAdapter = new HomeRXBAdapter(stringListOne);
                FeaturesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                FeaturesRecyclerView.setAdapter(homeRXBAdapter);
                break;
            case 2:
                List<String> stringListTwo = Arrays.asList("20.50", "45.00");
                HomeXPSFAdapter homeXPSFAdapter = new HomeXPSFAdapter(stringListTwo);
                FeaturesRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                FeaturesRecyclerView.setAdapter(homeXPSFAdapter);
                break;
            case 3:
                List<String> stringListThree = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    stringListThree.add("20");
                }
                HomeFLGNAdapter homeFLGNAdapter = new HomeFLGNAdapter(stringListThree);
                FeaturesRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                FeaturesRecyclerView.setAdapter(homeFLGNAdapter);
                break;
        }


    }
}
