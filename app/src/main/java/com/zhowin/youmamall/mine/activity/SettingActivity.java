package com.zhowin.youmamall.mine.activity;


import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.Nullable;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.permission.AndPermissionListener;
import com.zhowin.base_library.permission.AndPermissionUtils;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.qiniu.QiNiuYunBean;
import com.zhowin.base_library.qiniu.QinIuUpLoadListener;
import com.zhowin.base_library.qiniu.QinIuUtils;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GlideUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivitySettingBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.callback.OnHitMessageClickListener;
import com.zhowin.youmamall.mine.callback.OnSelectUserHeadDialogListener;
import com.zhowin.youmamall.mine.dialog.ChangeNickNameDialog;
import com.zhowin.youmamall.mine.dialog.HitMessageDialog;
import com.zhowin.youmamall.mine.dialog.SelectUserImageDialog;

import java.util.HashMap;
import java.util.List;

public class SettingActivity extends BaseBindActivity<ActivitySettingBinding> {


    private String qiNiuYunToken, qiNiuYunBaseUrl, userAvatar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setOnClick(R.id.civUserHead, R.id.tvUserNickName, R.id.tvUserMobile, R.id.tvSetPayPassword, R.id.llQRCodeLayout);

    }

    @Override
    public void initData() {
        getQiNiuYunBean();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civUserHead:
                break;
            case R.id.tvUserNickName:
                showChangeNIckName();
                break;
            case R.id.tvUserMobile:
                showChangeMobile();
                break;
            case R.id.tvSetPayPassword:
                startActivity(SetPasswordActivity.class);
                break;
            case R.id.llQRCodeLayout:
                break;

        }
    }

    private void showChangeMobile() {
        HitMessageDialog hitMessageDialog = new HitMessageDialog(mContext);
        hitMessageDialog.show();
        hitMessageDialog.setOnHitMessageClickListener(new OnHitMessageClickListener() {
            @Override
            public void onDetermineClick(String text) {
                startActivity(ChangeMobileActivity.class);
            }
        });
    }

    private void showChangeNIckName() {
        ChangeNickNameDialog changeNickNameDialog = new ChangeNickNameDialog(mContext);
        changeNickNameDialog.show();
        changeNickNameDialog.setEditNickName("周小川");
        changeNickNameDialog.setOnHitMessageClickListener(new OnHitMessageClickListener() {
            @Override
            public void onDetermineClick(String text) {

            }
        });
    }


    private void showSelectUserHeadPhotoDialog() {
        SelectUserImageDialog selectUserImageDialog = new SelectUserImageDialog();

        selectUserImageDialog.setOnSelectUserHeadDialogListener(new OnSelectUserHeadDialogListener() {
            @Override
            public void onTakePicture() {
                //拍照
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission(1, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    PictureSelectorUtils.takingPictures(mContext, 888);
                }
            }

            @Override
            public void onAlbumSelection() {
                //相册中选择
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission(2, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    PictureSelectorUtils.selectImageOfOne(mContext, 888, false);
                }
            }
        });
        selectUserImageDialog.show(getSupportFragmentManager(), "sc");
    }

    private void requestPermission(int type, @PermissionDef String... permissions) {
        AndPermissionUtils.requestPermission(mContext, new AndPermissionListener() {
            @Override
            public void PermissionSuccess(List<String> permissions) {
                switch (type) {
                    case 1:
                        PictureSelectorUtils.takingPictures(mContext, 888);
                        break;
                    case 2:
                        PictureSelectorUtils.selectImageOfOne(mContext, 888, false);
                        break;
                }
            }
            @Override
            public void PermissionFailure(List<String> permissions) {
                ToastUtils.showToast("权限未开启");
            }
        }, permissions);
    }

    private void getQiNiuYunBean() {
        HttpRequest.getQiNiuYunBean(this, new HttpCallBack<QiNiuYunBean>() {
            @Override
            public void onSuccess(QiNiuYunBean qiNiuYunBean) {
                if (qiNiuYunBean != null) {
                    qiNiuYunBaseUrl = qiNiuYunBean.getCdnurl();
                    qiNiuYunToken = qiNiuYunBean.getToken();
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 888:
                //上传七牛云
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.isEmpty()) return;
                String imageUrl = PictureSelectorUtils.getPhotoPath(selectList.get(0));
                qinIuUpLoad(imageUrl);
                break;
        }
    }

    private void qinIuUpLoad(String ImageUrl) {
        QinIuUtils.qinIuUpLoad(ImageUrl, qiNiuYunToken, new QinIuUpLoadListener() {
            @Override
            public void upLoadSuccess(String path) {
                userAvatar = "/" + path;
                GlideUtils.loadObjectImage(mContext, qiNiuYunBaseUrl + userAvatar, mBinding.civUserHead);
            }

            @Override
            public void upLoadFail(String errorMessage) {
                ToastUtils.showToast("图片上传失败:" + errorMessage);
            }
        });
    }


    private void changeUserMessageInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("avatar", "");
        map.put("nickname", "");
        map.put("", "");
        map.put("wechat_qrcode", "");

        HttpRequest.changeUserMessageInfo(this, null, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo != null) {
                    UserInfo.setUserInfo(userInfo);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

            }
        });


    }

}
