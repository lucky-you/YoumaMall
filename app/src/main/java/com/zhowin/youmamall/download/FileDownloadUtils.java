package com.zhowin.youmamall.download;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;


/**
 * author : zho
 * date  ：2020/12/25
 * desc ：下载
 */
public class FileDownloadUtils {

    public static int downLoadFile(String fileUrl, String targetPath, long id, DownFileListener listener) {
        return FileDownloader.getImpl().create(fileUrl)
                .setPath(targetPath, false)
                .setMinIntervalUpdateSpeed(400)
                .setCallbackProgressMinInterval(600)
                .setTag(id)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("xy", "pending taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int progress = (int) (soFarBytes * 1.0 / totalBytes * 100);
                        Log.i("xy", "下载进度" + progress + "  task.getTargetFilePath()" + task.getTargetFilePath());
                        listener.loadProgress(progress);

                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.d("xy", "blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" +
                                task.getSpeed() + ",isReuse:" + task.reuse() + ",Url:" + task.getUrl());
                        listener.loadSuccess();
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.d("xy", "completed taskId:" + task.getId() + ",isReuse:" + task.reuse());

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("xy", "paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d("xy", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        listener.fail(e.getMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d("xy", "warn taskId:" + task.getId());
                    }
                }).start();

    }

}
