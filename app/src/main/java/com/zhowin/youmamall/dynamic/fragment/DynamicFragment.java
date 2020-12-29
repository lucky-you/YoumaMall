package com.zhowin.youmamall.dynamic.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
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
import com.zhowin.youmamall.circle.adapter.CircleFragmentAdapter;
import com.zhowin.youmamall.databinding.IncludeDynamicFragmentLayoutBinding;
import com.zhowin.youmamall.download.DownloadStatusListener;
import com.zhowin.youmamall.download.DownloadUtil;
import com.zhowin.youmamall.dynamic.adapter.DynamicFragmentAdapter;
import com.zhowin.youmamall.dynamic.callback.OnDynamicItemClickListener;
import com.zhowin.youmamall.dynamic.model.DynamicList;
import com.zhowin.youmamall.http.ApiRequest;
import com.zhowin.youmamall.http.HttpRequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.Context.CLIPBOARD_SERVICE;

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

        MiteDownLoad(images);
//        for (String url : images) {
//            Log.e("xy", "imagesUrl:" + url);
//            downloadFile(url, "image");
////            downLoadImage(url);
//        }
    }

    /**
     * 指定线程下载文件(异步)，非阻塞式下载
     *
     * @param url 图片url
     */
    public void downloadFile(String url, String code) {
        String savePatch = Environment.getExternalStorageDirectory() + File.separator + "aYouMa";

        String PATH_CHALLENGE_VIDEO = Environment.getExternalStorageDirectory() + "/" + BaseApplication.getInstance().getString(R.string.app_name);


        RetrofitFactory.getInstance().initRetrofit(BuildConfig.API_HOST).create(ApiRequest.class)
                .downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.e("xy", "responseBody:" + responseBody);
                        Bitmap bitmap = null;
                        byte[] bys;
                        try {
                            bys = responseBody.bytes();
                            bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.length);
                            Log.e("xy", "bitmap:" + bitmap);
                            if (FileUtils.createOrExistsDir(PATH_CHALLENGE_VIDEO))
                                ImageUtils.save(bitmap, PATH_CHALLENGE_VIDEO, Bitmap.CompressFormat.JPEG);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        Log.e("xy", "下载完成");
                    }
                });
    }

    private void downLoadImage(String url) {
        DownloadUtil downloadUtil = new DownloadUtil();
        downloadUtil.downloadFile(url, new DownloadStatusListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onProgress(int currentLength) {
                Log.e("xy", "currentLength：" + currentLength);
            }

            @Override
            public void onFinish(String localPath) {
                Log.e("xy", "localPath：" + localPath);

            }

            @Override
            public void onFailure(String errorInfo) {
                Log.e("xy", "errorInfo：" + errorInfo);
            }
        });
    }

    private void MiteDownLoad(List<String> images) {
        String PATH_CHALLENGE_VIDEO = Environment.getExternalStorageDirectory() + "/" + BaseApplication.getInstance().getString(R.string.app_name);

        ApiRequest apiService = RetrofitFactory.getInstance().initRetrofit(BuildConfig.API_HOST).create(ApiRequest.class);
        //注意：此处是保存多张图片，可以采用异步线程
        ArrayList<Observable<Boolean>> observables = new ArrayList<>();
        final AtomicInteger count = new AtomicInteger();
        for (String image : images) {
            observables.add(apiService.downloadFile(image)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<ResponseBody, Boolean>() {
                        @Override
                        public Boolean apply(ResponseBody responseBody) throws Exception {
                            InputStream inputStream = responseBody.byteStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            ImageUtils.save(bitmap, PATH_CHALLENGE_VIDEO, Bitmap.CompressFormat.JPEG);
//                            File mFile = new File(PATH_CHALLENGE_VIDEO);
//                            if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile))
//                                writeFileToDisk(responseBody, mFile);
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
                            Log.e("xy", "download is succcess");

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

    private void writeFileToDisk(ResponseBody response, File file) {
        long currentLength = 0;
        OutputStream os = null;
        if (response == null) {
            return;
        }
        InputStream is = response.byteStream();
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e("xy", "当前进度:" + currentLength);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
