package com.zhowin.base_library.qiniu;

/**
 * author      : Z_B
 * date       : 2018/10/10
 * function  : 七牛云上传的回调
 */
public interface QinIuUpLoadListener {
    /**
     * 上传成功
     */
    void upLoadSuccess(String path);

    /**
     * 上传失败
     */
    void upLoadFail(String errorMessage);

}
