package com.zhowin.youmamall.home.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.home.callback.OnGoodCardItemClickListener;
import com.zhowin.youmamall.home.callback.OnHomeSeeMoreListener;
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

    private OnGoodCardItemClickListener onGoodCardItemClickListener;

    public void setOnGoodCardItemClickListener(OnGoodCardItemClickListener onGoodCardItemClickListener) {
        this.onGoodCardItemClickListener = onGoodCardItemClickListener;
    }

    private OnHomeSeeMoreListener onHomeSeeMoreListener;

    public void setOnHomeSeeMoreListener(OnHomeSeeMoreListener onHomeSeeMoreListener) {
        this.onHomeSeeMoreListener = onHomeSeeMoreListener;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, HomePageList item) {
        RecyclerView FeaturesRecyclerView = helper.getView(R.id.FeaturesRecyclerView);
        helper.setText(R.id.tvLeftTitle, item.getLeftTitle())
                .setText(R.id.tvLeftDesc, item.getLeftDesc())
                .setGone(R.id.tvRightSeeMore, item.isShowRight())
                .getView(R.id.tvRightSeeMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHomeSeeMoreListener != null) {
                    onHomeSeeMoreListener.onRightSeeMore();
                }
            }
        });
        switch (item.getItemType()) {
            case 1: //热销榜
                HomeRXBAdapter homeRXBAdapter = new HomeRXBAdapter(item.getGoodDataList());
                FeaturesRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                FeaturesRecyclerView.setAdapter(homeRXBAdapter);
                homeRXBAdapter.setOnGoodCardItemClickListener(onGoodCardItemClickListener);
                break;
            case 2: //新品首发
                HomeXPSFAdapter homeXPSFAdapter = new HomeXPSFAdapter(item.getGoodDataList());
                FeaturesRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
                FeaturesRecyclerView.setAdapter(homeXPSFAdapter);
                homeXPSFAdapter.setOnGoodCardItemClickListener(onGoodCardItemClickListener);
                break;
            case 3: //福利功能
                HomeFLGNAdapter homeFLGNAdapter = new HomeFLGNAdapter(item.getVipWelfareList());
                FeaturesRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                FeaturesRecyclerView.setAdapter(homeFLGNAdapter);
                homeFLGNAdapter.setOnHomeSeeMoreListener(onHomeSeeMoreListener);
                break;
        }
    }
}
