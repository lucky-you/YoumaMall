package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.PhoneUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.model.MyTeamList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/11/30
 * desc ：我的团队
 */
public class MyTeamAdapter extends BaseQuickAdapter<MyTeamList, BaseViewHolder> {
    public MyTeamAdapter(@Nullable List<MyTeamList> data) {
        super(R.layout.include_my_team_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyTeamList item) {
        GlideUtils.loadUserPhotoForLogin(mContext, item.getAvatar(), helper.getView(R.id.civHeadImage));
        helper.setText(R.id.tvUserMobile, PhoneUtils.hitCenterMobilNumber(item.getMobile()))
                .setText(R.id.tvCreateTime, DateHelpUtils.getStringDate(item.getJointime()))
                .setText(R.id.tvZXNumber, "直营销售" + item.getVolume() + "张");
    }
}
