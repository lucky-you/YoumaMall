package com.zhowin.base_library.qiniu;


/**
 * 七牛云的信息
 */
public class QiNiuYunBean {

    /**
     * uploadurl : https://upload-z0.qiniup.com
     * cdnurl : https://qiniu.mwc.mowan.cn/
     * token : be_BUN1Aq8y_MFDQby-sbnx2LwLTiXPBbkXmiej6:mI343NEM2ug0B_OrXIU2d_A9bhk=:eyJzY29wZSI6Im10YmlrZSIsImRlYWRsaW5lIjoxNTk2Njg3MzAyfQ==
     */

    private String uploadurl;
    private String cdnurl;
    private String token;

    public String getUploadurl() {
        return uploadurl;
    }

    public void setUploadurl(String uploadurl) {
        this.uploadurl = uploadurl;
    }

    public String getCdnurl() {
        return cdnurl;
    }

    public void setCdnurl(String cdnurl) {
        this.cdnurl = cdnurl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
