package com.zhowin.youmamall.mine.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeMineFragmentLayoutBinding;
import com.zhowin.youmamall.home.adapter.ColumnListAdapter;
import com.zhowin.youmamall.home.model.ColumnList;
import com.zhowin.youmamall.mine.activity.SettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class MineFragment extends BaseBindFragment<IncludeMineFragmentLayoutBinding> {

    private ColumnListAdapter columnListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.include_mine_fragment_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<ColumnList> columnList = new ArrayList<>();
        columnList.add(new ColumnList(R.drawable.icon_mine_ktdl, "开通代理"));
        columnList.add(new ColumnList(R.drawable.icon_mine_ewm, "推广二维码"));
        columnList.add(new ColumnList(R.drawable.icon_mine_xyzp, "幸运转盘"));
        columnList.add(new ColumnList(R.drawable.icon_mine_wdtd, "我的团队"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yjtx, "佣金提现"));
        columnList.add(new ColumnList(R.drawable.icon_mine_zhls, "账号流水"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yhj, "我的优惠券"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yqm, "邀请码"));
        columnList.add(new ColumnList(R.drawable.icon_mine_zhmm, "账号密码"));
        columnList.add(new ColumnList(R.drawable.icon_mine_yjfk, "意见反馈"));
        columnList.add(new ColumnList(R.drawable.icon_mine_lxkf, "联系客服"));
        columnList.add(new ColumnList(R.drawable.icon_mine_tcdl, "退出登录"));
        columnListAdapter = new ColumnListAdapter(columnList, 2);
        mBinding.moreRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mBinding.moreRecyclerView.setAdapter(columnListAdapter);
    }

    @Override
    public void initListener() {
        mBinding.tvTitleView.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity.class);
            }
        });
    }
}
