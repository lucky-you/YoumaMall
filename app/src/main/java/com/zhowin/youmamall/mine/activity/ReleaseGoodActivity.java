package com.zhowin.youmamall.mine.activity;

/**
 * 发布商品
 */

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.permission.AndPermissionListener;
import com.zhowin.base_library.permission.AndPermissionUtils;
import com.zhowin.base_library.pickerview.OnSelectConditionsClickListener;
import com.zhowin.base_library.pickerview.PickerViewConditionsUtils;
import com.zhowin.base_library.pickerview.SelectPickerList;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.qiniu.QiNiuYunBean;
import com.zhowin.base_library.qiniu.QinIuUpLoadListener;
import com.zhowin.base_library.qiniu.QinIuUtils;
import com.zhowin.base_library.utils.ActivityManager;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.FullyGridLayoutManager;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityReleaseGoodBinding;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mall.model.MallLeftList;
import com.zhowin.youmamall.mine.model.GoodInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 发布商品
 */
public class ReleaseGoodActivity extends BaseBindActivity<ActivityReleaseGoodBinding> {

    private NineGridItemListAdapter postGridImageAdapter;
    public static final int MAX_NUM = 1; //选择图片最大数目
    private ArrayList<LocalMedia> selectList = new ArrayList<>();//选中图片的集合
    private List<String> qinIuImages = new ArrayList<>(); //保存从七牛云返回的图片路径的集合
    private String qiNiuYunToken, qiNiuYunBaseUrl, imagePaths;
    private int statusPosition, statusType, goodCategoryPosition, goodCategoryId;
    private List<SelectPickerList> goodCategoryList = new ArrayList<>();
    private GoodInfo goodInfo;
    private boolean isChangeGoodInfo; //是否是修改商品


    public static void start(Context context, boolean isChangeGoodInfo, GoodInfo goodInfo) {
        Intent intent = new Intent(context, ReleaseGoodActivity.class);
        intent.putExtra(ConstantValue.TYPE, isChangeGoodInfo);
        intent.putExtra(ConstantValue.CONTNET, goodInfo);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_release_good;
    }

    @Override
    public void initView() {
        isChangeGoodInfo = getIntent().getBooleanExtra(ConstantValue.TYPE, false);
        goodInfo = getIntent().getParcelableExtra(ConstantValue.CONTNET);
        setOnClick(R.id.tvSelectCategory, R.id.tvSelectStatus, R.id.tvRelease);
        mBinding.tvTitleView.setTitle(isChangeGoodInfo ? "修改商品信息" : "发布商品");
        getMallLeftList();
        getQiNiuYunBean();

    }


    @Override
    public void initData() {
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        postGridImageAdapter = new NineGridItemListAdapter(mContext);
        postGridImageAdapter.setSelectMax(MAX_NUM);
        mBinding.goodRecyclerView.setLayoutManager(fullyGridLayoutManager);
        postGridImageAdapter.setOnNineGridItemClickListener(new OnNineGridItemClickListener() {
            @Override
            public void onAddPictureClick() {
                if (!selectList.isEmpty()) {
                    selectList.clear();
                    qinIuImages.clear();
                }
                requestPermission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
            }

            @Override
            public void onItemClick(int position, View view) {

            }

            @Override
            public void onItemClickDelete(int position, List<LocalMedia> localMediaList) {

            }
        });
        mBinding.goodRecyclerView.setAdapter(postGridImageAdapter);

        setGoodDataToView();
    }

    private void setGoodDataToView() {
        if (isChangeGoodInfo && goodInfo != null) {
            goodCategoryId = goodInfo.getCategoryId();
            statusType = goodInfo.getGoodStatusId();
            mBinding.editGoodName.setText(goodInfo.getGoodName());
            mBinding.editPrice.setText(goodInfo.getGoodPrice());
            mBinding.editContact.setText(goodInfo.getGoodContact());
            mBinding.editGoodContent.setText(goodInfo.getGoodContent());
            mBinding.tvSelectCategory.setText(goodInfo.getCategoryName());
            mBinding.tvSelectStatus.setText(goodInfo.getGoodStatusName());
            mBinding.tvSelectCategory.setTextColor(getBaseColor(R.color.color_333));
            mBinding.tvSelectStatus.setTextColor(getBaseColor(R.color.color_333));
            ArrayList<LocalMedia> selectList = new ArrayList<>();
            imagePaths = goodInfo.getGoodImage();
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(imagePaths);
            selectList.add(localMedia);
            postGridImageAdapter.setNewDataList(selectList);
        }
    }


    private void getMallLeftList() {
        HttpRequest.getMallLeftList(this, new HttpCallBack<List<MallLeftList>>() {
            @Override
            public void onSuccess(List<MallLeftList> mallLeftLists) {
                goodCategoryList.clear();
                if (mallLeftLists != null && !mallLeftLists.isEmpty()) {
                    for (MallLeftList item : mallLeftLists) {
                        goodCategoryList.add(new SelectPickerList(item.getId(), item.getName()));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSelectCategory:
                selectGoodCategoryDialog();
                break;
            case R.id.tvSelectStatus:
                selectStatusDialog();
                break;
            case R.id.tvRelease:
                if (isChangeGoodInfo) {
                    releaseOrChangeGood();
                } else {
                    if (!selectList.isEmpty()) {
                        showLoadDialog();
                        for (int i = 0; i < selectList.size(); i++) {
                            String imagePath = PictureSelectorUtils.getPhotoPath(selectList.get(i));
                            qinIuUpLoad(imagePath);
                        }
                    }
                }
                break;
        }
    }


    private void selectGoodCategoryDialog() {
        PickerViewConditionsUtils.selectConditionsView(mContext, goodCategoryList, goodCategoryPosition, new OnSelectConditionsClickListener() {
            @Override
            public void onConditionsSelect(int position, String selectName, int selectId) {
                goodCategoryPosition = position;
                goodCategoryId = selectId;
                mBinding.tvSelectCategory.setText(selectName);
                mBinding.tvSelectCategory.setTextColor(getBaseColor(R.color.color_333));
            }
        });
    }

    private void selectStatusDialog() {
        List<SelectPickerList> selectPickerList = new ArrayList<>();
        selectPickerList.add(new SelectPickerList(1, "推荐"));
        selectPickerList.add(new SelectPickerList(2, "热销"));
        PickerViewConditionsUtils.selectConditionsView(mContext, selectPickerList, statusPosition, new OnSelectConditionsClickListener() {
            @Override
            public void onConditionsSelect(int position, String selectName, int selectId) {
                statusPosition = position;
                statusType = selectId;
                mBinding.tvSelectStatus.setText(selectName);
                mBinding.tvSelectStatus.setTextColor(getBaseColor(R.color.color_333));
            }
        });
    }

    private void releaseOrChangeGood() {
        String goodName = mBinding.editGoodName.getText().toString().trim();
        String goodPrice = mBinding.editPrice.getText().toString().trim();
        String userContact = mBinding.editContact.getText().toString().trim();
        String goodContent = mBinding.editGoodContent.getText().toString().trim();
        if (goodCategoryId == 0) {
            ToastUtils.showToast("请选择商品分类");
            return;
        }
        if (TextUtils.isEmpty(goodName)) {
            ToastUtils.showToast("请输入商品名称");
            return;
        }
        if (TextUtils.isEmpty(imagePaths)) {
            ToastUtils.showToast("请选择商品图片");
            return;
        }
        if (TextUtils.isEmpty(goodPrice)) {
            ToastUtils.showToast("请输入商品价格");
            return;
        }
        if (statusType == 0) {
            ToastUtils.showToast("请选择商品状态");
            return;
        }
        if (TextUtils.isEmpty(userContact)) {
            ToastUtils.showToast("请输入联系方式");
            return;
        }
        if (TextUtils.isEmpty(goodContent)) {
            ToastUtils.showToast("请输入商品详情");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("shop_category_id", goodCategoryId);
        map.put("name", goodName);
        map.put("image", imagePaths);
        map.put("price", goodPrice);
        map.put("type", statusType);
        map.put("contact", userContact);
        map.put("content", goodContent);
        if (isChangeGoodInfo)
            map.put("id", goodInfo.getGoodId());
        showLoadDialog();
        HttpRequest.releaseOrChangeGood(this, map, new HttpCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                dismissLoadDialog();
                ToastUtils.showToast("发布成功");
                ActivityManager.getAppInstance().finishActivity();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void requestPermission(@PermissionDef String... permissions) {
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
                    break;
                case PictureConfig.REQUEST_CAMERA:
                    ArrayList<LocalMedia> resultdata = (ArrayList<LocalMedia>) PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(resultdata);
                    postGridImageAdapter.setNewDataList(selectList);
                    break;
            }
        }
    }

    private void qinIuUpLoad(String imagePath) {
        QinIuUtils.qinIuUpLoad(imagePath, qiNiuYunToken, new QinIuUpLoadListener() {
            @Override
            public void upLoadSuccess(String path) {
                String imageUrl = "/" + path;
                qinIuImages.add(imageUrl);
                //先要把图片上传到七牛云,然后在提交
                if (qinIuImages.size() == selectList.size()) { //集合数量相等，说明，选择的图片全部上传了
                    dismissLoadDialog();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < qinIuImages.size(); i++) {
                        stringBuffer.append(qinIuImages.get(i) + ",");
                    }
                    if (stringBuffer != null && stringBuffer.length() > 0) {
                        imagePaths = stringBuffer.substring(0, stringBuffer.length() - 1);
                    }
                    releaseOrChangeGood();
                }
            }

            @Override
            public void upLoadFail(String errorMessage) {
                ToastUtils.showToast("图片上传失败:" + errorMessage);
            }
        });
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
}
