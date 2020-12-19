package com.zhowin.youmamall.mine.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.model.ShareMaterialList;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/1
 * desc ： 分享素材
 */
public class ShareMaterialAdapter extends BaseQuickAdapter<ShareMaterialList, BaseViewHolder> {
    public ShareMaterialAdapter(@Nullable List<ShareMaterialList> data) {
        super(R.layout.include_share_materia_item_view, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShareMaterialList item) {
        GlideUtils.loadObjectImage(mContext, item.getImage(), helper.getView(R.id.ivTopQrCode));
    }
}
