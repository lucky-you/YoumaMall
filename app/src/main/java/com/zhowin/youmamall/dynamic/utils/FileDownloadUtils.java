package com.zhowin.youmamall.dynamic.utils;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * author : zho
 * date  ：2020/12/25
 * desc ：
 */
public class FileDownloadUtils {

    public static int fileDown(String fileUrl, String targetPath, long id, DownFileListener listener) {
        int index = fileUrl.lastIndexOf("/");
        String url = fileUrl.substring(0, index) + "?fileName=" + fileUrl.substring(index + 1, fileUrl.length());
        return FileDownloader.getImpl().create(url)
                .setPath(targetPath, false)
                .setMinIntervalUpdateSpeed(400)
                .setCallbackProgressMinInterval(600)
                .setTag(id)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("feifei", "pending taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

//                        Log.d("feifei","progress taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes+",speed:"+task.getSpeed());
                        int progress = (int) (soFarBytes * 1.0 / totalBytes * 100);
                        Log.i("feifei", "下载进度" + progress + "  task.getTargetFilePath()" + task.getTargetFilePath());


                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.d("feifei", "blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" +
                                task.getSpeed() + ",isReuse:" + task.reuse() + ",Url:" + task.getUrl());


                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.d("feifei", "completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("feifei", "paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d("feifei", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d("feifei", "warn taskId:" + task.getId());
                    }
                }).start();

    }

    public static void downloadPhoto(String photoOnlinePath, String photoLocalPath, DownFileListener listener) {
        if (photoOnlinePath.startsWith("http") || photoOnlinePath.startsWith("https")) {
            File myPath = new File(photoLocalPath);
            String directory = myPath.getParent();
            File mydirectory = new File(directory);
            if (!mydirectory.exists()) {
                //若此目录不存在，则创建之
                mydirectory.mkdirs();
            }
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder().get().url(photoOnlinePath).build();

                Response response = okHttpClient.newCall(request).execute();
                if (!response.isSuccessful()) {
//                logger.debug("http request {} connect failed", photoOnlinePath);
                    Log.i("FileDownloadUtils", "!response.isSuccessful:" + !response.isSuccessful());
                } else {
//                logger.info("http request {} connect success，start write in", photoOnlinePath);
                    InputStream is;
                    is = response.body().byteStream();
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myPath);
                        int len;
                        byte[] bytes = new byte[1024];
                        while ((len = is.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);
                        }
                    } catch (IOException oue) {
                        Log.i("FileDownloadUtils", "close FileOutputStream failed");
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException isclose) {
                                Log.i("FileDownloadUtils", "close FileOutputStream failed");
                            }
                        }
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException fosclose) {
                                Log.i("FileDownloadUtils", "close FileOutputStream failed");
                            }
                        }
                        Log.i("FileDownloadUtils", "loadSuccess");
                        listener.loadSuccess();
                    }
                }
            } catch (IOException e) {
                Log.i("FileDownloadUtils", "close FileOutputStream failed" + "   " + photoOnlinePath);
            }
        }


    }
}
