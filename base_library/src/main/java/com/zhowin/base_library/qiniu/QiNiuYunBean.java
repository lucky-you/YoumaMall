package com.zhowin.base_library.qiniu;


/**
 * 七牛云的信息
 */
public class QiNiuYunBean {


    /**
     * cdn : https://png.lxxxin.com
     * upload : z0
     * token : 20lS9OkmR4ToWp6YvKaHiBSe4HbkwezoVcZ0NeCf:TiKlcRCX9ozHEwrJypHJ7pIzQW0=:eyJzY29wZSI6InlvdW1hc2hhbmdjaGFuZy1oZCIsImRlYWRsaW5lIjoxNjA3NDg3NTgxfQ==
     */

    private String cdn;
    private String upload;
    private String token;

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
