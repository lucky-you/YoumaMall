package com.zhowin.youmamall.mine.activity;


import android.content.Intent;
import android.os.Build;
import android.util.Log;
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
import com.zhowin.youmamall.circle.utils.UserLevelHelper;
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


    private String qiNiuYunToken, qiNiuYunBaseUrl, userAvatar, userNickName, userQRCode;
    private int passwordType;

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
    protected void onResume() {
        super.onResume();
        getUserInfoMessage();
    }

    private void getUserInfoMessage() {
        HttpRequest.getUserInfoMessage(this, new HttpCallBack<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo != null) {
                    UserInfo.setUserInfo(userInfo);
                    userAvatar = userInfo.getAvatar();
                    userNickName = userInfo.getNickname();
                    userQRCode = userInfo.getWechat_qrcode();
                    passwordType = userInfo.getIs_pay_pwd();
                    GlideUtils.loadUserPhotoForLogin(mContext, userAvatar, mBinding.civUserHead);
                    mBinding.tvUserNickName.setText(userNickName);
                    mBinding.tvUserMobile.setText(userInfo.getMobile());
                    mBinding.tvSetPayPassword.setText(1 == userInfo.getIs_pay_pwd() ? "已设置" : "未设置");
                    GlideUtils.loadObjectImage(mContext, userQRCode, mBinding.ivWxQrCodeImage);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civUserHead:
                showSelectUserHeadPhotoDialog(true);
                break;
            case R.id.tvUserNickName:
                showChangeNIckName();
                break;
            case R.id.tvUserMobile:
                showChangeMobile();
                break;
            case R.id.tvSetPayPassword:
                SetPasswordActivity.start(mContext, passwordType);
                break;
            case R.id.llQRCodeLayout:
                showSelectUserHeadPhotoDialog(false);
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
        changeNickNameDialog.setEditNickName(userNickName);
        changeNickNameDialog.setOnHitMessageClickListener(new OnHitMessageClickListener() {
            @Override
            public void onDetermineClick(String text) {
                userNickName = text;
                changeUserMessageInfo();
            }
        });
    }


    private void showSelectUserHeadPhotoDialog(boolean isSelectHead) {
        SelectUserImageDialog selectUserImageDialog = new SelectUserImageDialog();
        selectUserImageDialog.setOnSelectUserHeadDialogListener(new OnSelectUserHeadDialogListener() {
            @Override
            public void onTakePicture() {
                //拍照
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission(isSelectHead, 1, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    PictureSelectorUtils.takingPictures(mContext, isSelectHead ? 888 : 999);
                }
            }

            @Override
            public void onAlbumSelection() {
                //相册中选择
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission(isSelectHead, 2, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
                } else {
                    PictureSelectorUtils.selectImageOfOne(mContext, isSelectHead ? 888 : 999, false);
                }
            }
        });
        selectUserImageDialog.show(getSupportFragmentManager(), "sc");
    }

    private void requestPermission(boolean isSelectHead, int type, @PermissionDef String... permissions) {
        AndPermissionUtils.requestPermission(mContext, new AndPermissionListener() {
            @Override
            public void PermissionSuccess(List<String> permissions) {
                switch (type) {
                    case 1:
                        PictureSelectorUtils.takingPictures(mContext, isSelectHead ? 888 : 999);
                        break;
                    case 2:
                        PictureSelectorUtils.selectImageOfOne(mContext, isSelectHead ? 888 : 999, false);
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
                    qiNiuYunBaseUrl = qiNiuYunBean.getCdn();
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
            case 888: //
                //上传七牛云
                List<LocalMedia> selectHeadList = PictureSelector.obtainMultipleResult(data);
                if (selectHeadList.isEmpty()) return;
                String imageUrl = PictureSelectorUtils.getPhotoPath(selectHeadList.get(0));
                qinIuUpLoad(imageUrl, true);
                break;
            case 999: //
                //上传七牛云
                List<LocalMedia> selectQrList = PictureSelector.obtainMultipleResult(data);
                if (selectQrList.isEmpty()) return;
                String imageQrUrl = PictureSelectorUtils.getPhotoPath(selectQrList.get(0));
                qinIuUpLoad(imageQrUrl, false);
                break;
        }
    }

    private void qinIuUpLoad(String ImageUrl, boolean isSelectHead) {
        QinIuUtils.qinIuUpLoad(ImageUrl, qiNiuYunToken, new QinIuUpLoadListener() {
            @Override
            public void upLoadSuccess(String path) {
                if (isSelectHead) {
                    userAvatar = "/" + path;
                    String url = qiNiuYunBaseUrl + userAvatar;
                    GlideUtils.loadObjectImage(mContext, url, mBinding.civUserHead);
                } else {
                    userQRCode = "/" + path;
                    String qrCodeUrl = qiNiuYunBaseUrl + userQRCode;
                    GlideUtils.loadObjectImage(mContext, qrCodeUrl, mBinding.ivWxQrCodeImage);
                }
                changeUserMessageInfo();
            }

            @Override
            public void upLoadFail(String errorMessage) {
                ToastUtils.showToast("图片上传失败:" + errorMessage);
            }
        });
    }


    private void changeUserMessageInfo() {
        showLoadDialog();
        HttpRequest.changeUserMessageInfo(this, userAvatar, userNickName, userQRCode, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object userInfo) {
                dismissLoadDialog();
                getUserInfoMessage();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }
}
