package com.zhowin.youmamall.circle.activity;


import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;
import com.zhowin.base_library.adapter.NineGridItemListAdapter;
import com.zhowin.base_library.callback.OnNineGridItemClickListener;
import com.zhowin.base_library.permission.AndPermissionListener;
import com.zhowin.base_library.permission.AndPermissionUtils;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.qiniu.QinIuUpLoadListener;
import com.zhowin.base_library.qiniu.QinIuUtils;
import com.zhowin.base_library.utils.SpanUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.FullyGridLayoutManager;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityReleaseCircleBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布圈子
 */
public class ReleaseCircleActivity extends BaseBindActivity<ActivityReleaseCircleBinding> {
    private NineGridItemListAdapter postGridImageAdapter;
    public static final int MAX_NUM = 9; //选择图片最大数目
    private ArrayList<LocalMedia> selectList = new ArrayList<>();//选中图片的集合
    private List<String> qinIuImages = new ArrayList<>(); //保存从七牛云返回的图片路径的集合
    private String imagePaths, qinIuToken;

    @Override
    public int getLayoutId() {
        return R.layout.activity_release_circle;
    }

    @Override
    public void initView() {
        SpanUtils.with(mBinding.tvHitMessage)
                .appendLine("必看:").setBold()
                .appendLine()
                .appendLine("1.禁止发布色情内容、酷咔相关产品内容，以上内容直接永久冻结账号，永不解封；")
                .appendLine()
                .appendLine("2.发布的文案内容不能存在特殊表情符号，否则会发送失败。")
                .create();

    }

    @Override
    public void initData() {
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        postGridImageAdapter = new NineGridItemListAdapter(mContext);
        postGridImageAdapter.setSelectMax(MAX_NUM);
        mBinding.circleRecyclerView.setLayoutManager(fullyGridLayoutManager);
        postGridImageAdapter.setOnNineGridItemClickListener(new OnNineGridItemClickListener() {
            @Override
            public void onAddPictureClick() {
                if (!selectList.isEmpty()) {
                    selectList.clear();
                    qinIuImages.clear();
                }
                requestPermission(2, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
            }

            @Override
            public void onItemClick(int position, View view) {

            }

            @Override
            public void onItemClickDelete(int position, List<LocalMedia> localMediaList) {

            }
        });
        mBinding.circleRecyclerView.setAdapter(postGridImageAdapter);
    }


    private void requestPermission(int type, @PermissionDef String... permissions) {
        AndPermissionUtils.requestPermission(mContext, new AndPermissionListener() {
            @Override
            public void PermissionSuccess(List<String> permissions) {
                PictureSelectorUtils.selectImageOfMore(mContext, MAX_NUM, true, selectList);
            }

            @Override
            public void PermissionFailure(List<String> permissions) {
                ToastUtils.showToast("权限未开启");
            }
        }, permissions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList = (ArrayList<LocalMedia>) PictureSelector.obtainMultipleResult(data);
                    if (selectList.isEmpty()) return;
                    postGridImageAdapter.setNewDataList(selectList);
                    postGridImageAdapter.notifyDataSetChanged();
                    break;
                case PictureConfig.REQUEST_CAMERA:
                    ArrayList<LocalMedia> resultdata = (ArrayList<LocalMedia>) PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(resultdata);
                    postGridImageAdapter.setNewDataList(selectList);
                    postGridImageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private void qinIuUpLoad(String imagePath, String content) {
        QinIuUtils.qinIuUpLoad(imagePath, qinIuToken, new QinIuUpLoadListener() {
            @Override
            public void upLoadSuccess(String path) {
                String imageUrl = "/" + path;
                qinIuImages.add(imageUrl);
                //先要把图片上传到七牛云,然后在提交
                if (qinIuImages.size() == selectList.size()) { //集合数量相等，说明，选择的图片全部上传了
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < qinIuImages.size(); i++) {
                        stringBuffer.append(qinIuImages.get(i) + ",");
                    }
                    if (stringBuffer != null && stringBuffer.length() > 0) {
                        imagePaths = stringBuffer.substring(0, stringBuffer.length() - 1);
                    }
                }
            }

            @Override
            public void upLoadFail(String errorMessage) {
                ToastUtils.showToast("图片上传失败:" + errorMessage);
            }
        });
    }
}
