package com.zhowin.youmamall.dynamic.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;
import com.zhowin.base_library.base.BaseApplication;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.http.RetrofitFactory;
import com.zhowin.base_library.model.BaseResponse;
import com.zhowin.base_library.permission.AndPermissionListener;
import com.zhowin.base_library.permission.AndPermissionUtils;
import com.zhowin.base_library.pictureSelect.PictureSelectorUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.youmamall.BuildConfig;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindFragment;
import com.zhowin.youmamall.databinding.IncludeDynamicFragmentLayoutBinding;
import com.zhowin.youmamall.download.DownloadStatusListener;
import com.zhowin.youmamall.download.DownloadUtil;
import com.zhowin.youmamall.dynamic.adapter.DynamicFragmentAdapter;
import com.zhowin.youmamall.dynamic.callback.OnDynamicItemClickListener;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.download.DownFileListener;
import com.zhowin.youmamall.download.FileDownloadUtils;
import com.zhowin.youmamall.http.ApiRequest;
import com.zhowin.youmamall.http.HttpRequest;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author : zho
 * date  ：2020/11/26
 * desc ：
 */
public class DynamicFragment extends BaseBindFragment<IncludeDynamicFragmentLayoutBinding> implements OnDynamicItemClickListener {
    private DynamicFragmentAdapter dynamicFragmentAdapter;

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
                if (baseResponse != null) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission(images, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE);
        } else {
            createDownLoadImage(images);
        }
    }


    @Override
    public void onCopyContent(String content) {
        ClipboardUtils.copyText(content);
        ToastUtils.showToast("已复制到粘贴板");
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

    private int count=0;

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


    private void onMiteDownLoad(List<String> images) {
        ApiRequest apiService = RetrofitFactory.getInstance().initRetrofit(BuildConfig.API_HOST).create(ApiRequest.class);
        //注意：此处是保存多张图片，可以采用异步线程
        ArrayList<Observable<Boolean>> observables = new ArrayList<>();
        final AtomicInteger count = new AtomicInteger();
        for (String image : images) {
            observables.add(apiService.downloadFile(image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .map(new Function<ResponseBody, Boolean>() {
                        @Override
                        public Boolean apply(ResponseBody responseBody) throws Exception {
                            saveIo(responseBody.byteStream());
                            return true;
                        }
                    }));
        }
        // observable的merge 将所有的observable合成一个Observable，所有的observable同时发射数据
        Disposable subscribe = Observable.merge(observables).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean b) throws Exception {
                        if (b) {
                            count.addAndGet(1);
                            Log.e("xy", "download is succcess---size:" + count.get());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("xy", "download error");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("xy", "download complete");
                        // 下载成功的数量 和 图片集合的数量一致，说明全部下载成功了
                        if (images.size() == count.get()) {
                            ToastUtils.showToast("保存成功");
                        } else {
                            if (count.get() == 0) {
                                ToastUtils.showToast("保存失败");
                            } else {
                                ToastUtils.showToast("因网络问题 保存成功" + count + ",保存失败" + (images.size() - count.get()));
                            }
                        }
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.e("xy", "disposable");
                    }
                });
    }

    private void saveIo(InputStream inputStream) {
        String savePath = Environment.getExternalStorageDirectory() + "/" + BaseApplication.getInstance().getString(R.string.app_name);
        if (FileUtils.createOrExistsDir(savePath)) {
            String imgName = UUID.randomUUID().toString() + ".jpg"; //随机生成不同的名字
            File imageFile = new File(savePath + "/" + imgName);
            if (FileUtils.createOrExistsFile(imageFile)) {
                boolean isSaveSuccess = FileIOUtils.writeFileFromIS(imageFile, inputStream, true);
                sendBroadcastToAlbum(mContext, imageFile);
                Log.e("xy", "isSaveSuccess:" + isSaveSuccess);
            }
        }
    }

    public void sendBroadcastToAlbum(Context context, File imagePath) {
        if (imagePath != null && imagePath.length() > 0) {
            if (imagePath.exists()) {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(imagePath);
                if (uri != null && context != null) {
                    intent.setData(uri);
                    context.sendBroadcast(intent);
                }
            }
        }
    }
}
