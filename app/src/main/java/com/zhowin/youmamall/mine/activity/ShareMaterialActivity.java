package com.zhowin.youmamall.mine.activity;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.BitmapUtils;
import com.zhowin.base_library.utils.SizeUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.GridSpacingItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityShareMaterialBinding;
import com.zhowin.youmamall.home.utils.QRCodeUtils;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.adapter.ShareMaterialAdapter;
import com.zhowin.youmamall.mine.callback.OnShareMaterialListener;
import com.zhowin.youmamall.mine.dialog.ShareMaterialDialog;
import com.zhowin.youmamall.mine.model.MineItemConfig;
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
    private String shareUrlHttp, shareUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share_material;
    }

    @Override
    public void initView() {
        getShareMaterial();
        getMineItemConfig();
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

    private void getMineItemConfig() {
        HttpRequest.getMineItemConfig(this, new HttpCallBack<MineItemConfig>() {
            @Override
            public void onSuccess(MineItemConfig mineItemConfig) {
                if (mineItemConfig != null) {
                    shareUrlHttp = mineItemConfig.getH5_domain();
                    if (!TextUtils.isEmpty(shareUrlHttp)) {
                        shareUrl = shareUrlHttp + "/reg.html?invitation_code=" + UserInfo.getUserInfo().getInvitation_code();
                        if (!TextUtils.isEmpty(shareUrl))
                            shareMaterialAdapter.setShareUrl(shareUrl);
                    }
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String imageUrl = shareMaterialAdapter.getItem(position).getImage();
        ShareMaterialDialog shareMaterialDialog = new ShareMaterialDialog(mContext);
        shareMaterialDialog.setImageUrl(imageUrl,shareUrl);
        shareMaterialDialog.setOnShareMaterialListener(new OnShareMaterialListener() {
            @Override
            public void onStartShare(View rootView) {
                requestPermission(rootView);
            }
        });
        shareMaterialDialog.show();
    }

    private void requestPermission(View rootView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .callback(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            saveQrCodeToAlbum(rootView);
                        }

                        @Override
                        public void onDenied() {
                        }
                    }).request();
        }
    }

    private void saveQrCodeToAlbum(View rootView) {
        Bitmap bitmapSrc = BitmapUtils.getCacheBitmapFromView(rootView);
        ThreadUtils.executeBySingle(new ThreadUtils.SimpleTask<File>() {
            @Override
            public File doInBackground() throws Throwable {
                return ImageUtils.save2Album(bitmapSrc, Bitmap.CompressFormat.JPEG);
            }

            @Override
            public void onSuccess(File result) {
                if (result != null) {
                    com.blankj.utilcode.util.ToastUtils.showLong("保存成功");
                } else {
                    com.blankj.utilcode.util.ToastUtils.showLong("保存失败");
                }
            }
        });
    }
}
