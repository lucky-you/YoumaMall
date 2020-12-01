package com.zhowin.youmamall.mine.activity;


import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityShareMaterialBinding;
import com.zhowin.youmamall.mine.adapter.ShareMaterialAdapter;
import com.zhowin.youmamall.mine.dialog.ShareMaterialDialog;

import java.util.Arrays;
import java.util.List;

/**
 * 分享素材
 */
public class ShareMaterialActivity extends BaseBindActivity<ActivityShareMaterialBinding> implements BaseQuickAdapter.OnItemClickListener {


    private ShareMaterialAdapter shareMaterialAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_share_material;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        List<String> stringList = Arrays.asList("", "", "", "", "", "");
        shareMaterialAdapter = new ShareMaterialAdapter(stringList);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mBinding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, SizeUtils.dp2px(8), true));
        mBinding.recyclerView.setAdapter(shareMaterialAdapter);
        shareMaterialAdapter.setOnItemClickListener(this::onItemClick);
    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        showShareDialog();
    }

    private void showShareDialog() {
        ShareMaterialDialog shareMaterialDialog = new ShareMaterialDialog(mContext);
        shareMaterialDialog.show();
    }
}
