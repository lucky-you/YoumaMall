package com.zhowin.youmamall.mine.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.BitmapUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityShareMaterialBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.adapter.ShareMaterialAdapter;
import com.zhowin.youmamall.mine.callback.OnShareMaterialListener;
import com.zhowin.youmamall.mine.dialog.ShareMaterialDialog;
import com.zhowin.youmamall.mine.model.ShareMaterialList;

import java.io.File;
import java.util.ArrayList;
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
        getShareMaterial();
    }

    @Override
    public void initData() {
        shareMaterialAdapter = new ShareMaterialAdapter(new ArrayList<>());
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
                getShareMaterial();
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getShareMaterial() {
        showLoadDialog();
        HttpRequest.getShareMaterial(this, new HttpCallBack<List<ShareMaterialList>>() {
            @Override
            public void onSuccess(List<ShareMaterialList> shareMaterialLists) {
                dismissLoadDialog();
                shareMaterialAdapter.setNewData(shareMaterialLists);
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String imageUrl = shareMaterialAdapter.getItem(position).getImage();
        ShareMaterialDialog shareMaterialDialog = new ShareMaterialDialog(mContext);
        shareMaterialDialog.setImageUrl(imageUrl);
        shareMaterialDialog.setOnShareMaterialListener(new OnShareMaterialListener() {
            @Override
            public void onStartShare(View rootView) {
                startShare(rootView);
            }
        });
        shareMaterialDialog.show();
    }

    private void startShare(View view) {
        Bitmap bitmapSrc = BitmapUtils.getCacheBitmapFromView(view);
        File file = new File(BitmapUtils.bitmapFileToString(bitmapSrc));
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Uri uri = Uri.fromFile(file);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, BitmapUtils.bitmapFileToString(bitmapSrc));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }

    }
}
