package com.zhowin.base_library.qiniu;

import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * author      : Z_B
 * date       : 2018/10/10
 * function  : 七牛云的工具类
 */
public class QinIuUtils {

    private static QinIuUtils qinIuUtils;
    private QiNiuYunBean qinIuBean;

    public static QinIuUtils getInstance() {
        if (qinIuUtils == null) {
            synchronized (QinIuUtils.class) {
                if (qinIuUtils == null) {
                    qinIuUtils = new QinIuUtils();
                }
            }
        }
        return qinIuUtils;
    }

    /**
     * 随机数
     */
    public static String getRandomAlphaDigitString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getStringDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HHmmss");
        return format.format(date);
    }


    /**
     * @param data                File对象、或 文件路径、或 字节数组
     * @param key                 指定七牛服务上的文件名，或   null
     * @param token               String token = <从服务端获取>
     * @param qinIuUpLoadListener 上传结果的回调
     */
    public static void qinIuUpLoad(String data, String token, final QinIuUpLoadListener qinIuUpLoadListener) {
        String qinKey = "KuKa/" + QinIuUtils.getStringDate() + "/" + System.currentTimeMillis();
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager(config);
        uploadManager.put(data, qinKey, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            if (qinIuUpLoadListener != null) {
                                try {
                                    String fileHash = res.getString("hash");
                                    String fileKey = res.getString("key");
                                    qinIuUpLoadListener.upLoadSuccess(fileKey);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            if (qinIuUpLoadListener != null) {
                                qinIuUpLoadListener.upLoadFail(info.error);
                            }
                        }
                        Log.e("xy", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }

    public void setQinIuBean(QiNiuYunBean qinIuBean) {
        this.qinIuBean = qinIuBean;
    }

    public QiNiuYunBean getQinIuBean() {
        if (qinIuBean == null) return new QiNiuYunBean();
        return qinIuBean;
    }
}
