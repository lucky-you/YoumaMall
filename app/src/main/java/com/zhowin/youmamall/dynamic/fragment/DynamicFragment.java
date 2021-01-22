package com.zhowin.youmamall.dynamic.fragment;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;
import com.zhowin.base_library.base.BaseApplication;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.permission.AndPermissionListener;
import com.zhowin.base_library.permission.AndPermissionUtils;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.utils.EmptyViewUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeDynamicFragmentLayoutBinding;
import com.zhowin.youmamall.dynamic.adapter.DynamicFragmentAdapter;
import com.zhowin.youmamall.dynamic.callback.OnDynamicItemClickListener;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.download.DownFileListener;
import com.zhowin.youmamall.download.FileDownloadUtils;
import com.zhowin.youmamall.http.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：动态
 */
public class DynamicFragment extends BaseBindFragment<IncludeDynamicFragmentLayoutBinding> implements OnDynamicItemClickListener {
    private DynamicFragmentAdapter dynamicFragmentAdapter;
    private int count = 0;

    @Override
    public int getLayoutId() {
        return R.layout.include_dynamic_fragment_layout;
    }

    @Override
    public void initView() {
        getDynamicList(true);
    }

    @Override
    public void initData() {
        dynamicFragmentAdapter = new DynamicFragmentAdapter(new ArrayList<>());
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.setAdapter(dynamicFragmentAdapter);
        dynamicFragmentAdapter.setOnDynamicItemClickListener(this);
    }


    private void getDynamicList(boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        }
        HttpRequest.getDynamicList(this, currentPage, pageNumber, new HttpCallBack<BaseResponse<DynamicList>>() {
            @Override
            public void onSuccess(BaseResponse<DynamicList> baseResponse) {
                if (baseResponse != null && !baseResponse.getData().isEmpty()) {
                    currentPage++;
                    mBinding.refreshLayout.setRefreshing(false);
                    if (isRefresh) {
                        dynamicFragmentAdapter.setNewData(baseResponse.getData());
                    } else {
                        dynamicFragmentAdapter.addData(baseResponse.getData());
                    }
                    if (baseResponse.getData().size() < pageNumber) {
                        dynamicFragmentAdapter.loadMoreEnd(true);
                    } else {
                        dynamicFragmentAdapter.loadMoreComplete();
                    }
                } else {
                    EmptyViewUtils.bindEmptyView(mContext, dynamicFragmentAdapter);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtils.showToast(errorMsg);
                dismissLoadDialog();
            }
        });
    }

    @Override
    public void initListener() {
        mBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDynamicList(true);
                mBinding.refreshLayout.setRefreshing(false);
            }
        });
        dynamicFragmentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDynamicList(false);
            }
        }, mBinding.recyclerView);
    }

    @Override
    public void onSavePhoto(List<String> images) {
        if (!isLogin())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission(images, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
            } else {
                createDownLoadImage(images);
            }
    }


    @Override
    public void onCopyContent(String content) {
        if (!isLogin()) {
            ClipboardUtils.copyText(content);
            ToastUtils.showToast("已复制到粘贴板");

        }
    }

    @Override
    public void onImageItemClick(List<String> imageList, int position) {
        PictureSelectorUtils.previewPicture(mContext, position, imageList);
    }

    private void requestPermission(List<String> images, @PermissionDef String... permissions) {
        AndPermissionUtils.requestPermission(mContext, new AndPermissionListener() {
            @Override
            public void PermissionSuccess(List<String> permissions) {
                createDownLoadImage(images);
            }

            @Override
            public void PermissionFailure(List<String> permissions) {
                ToastUtils.showToast("权限未开启");
            }
        }, permissions);
    }

    private void createDownLoadImage(List<String> images) {
        for (int i = 0; i < images.size(); i++) {
            downLoadImageFile(images.get(i), i + 1, images.size());
        }
    }


    /**
     * 下载图片
     */
    private void downLoadImageFile(String url, int id, int imgSize) {
        String savePath = Environment.getExternalStorageDirectory() + "/" + BaseApplication.getInstance().getString(R.string.app_name);
        String mUrlPath = null;
        if (FileUtils.createOrExistsDir(savePath)) {
            String imgName = url;
            int i = imgName.lastIndexOf('/');//一定是找最后一个'/'出现的位置
            if (i != -1) {
                imgName = imgName.substring(i);
                mUrlPath = savePath + imgName;
            }
            FileDownloadUtils.downLoadFile(url, mUrlPath, id, new DownFileListener() {
                @Override
                public void loadSuccess() {
                    count++;
                    Log.e("xy", "success");
                    if (count == imgSize) {
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast("保存成功");
                            }
                        });
                    }
                }

                @Override
                public void loadProgress(int progress) {
                }

                @Override
                public void fail(String error) {
                    Log.e("xy", "error");
                }
            });
        }
    }

}
