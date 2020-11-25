package com.zhowin.base_library.qiniu;

import android.text.TextUtils;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.GsonUtils;
import com.zhowin.base_library.utils.SPUtils;

/**
 * 七牛云的信息
 */
public class QiNiuYunBean {


    /**
     * token : H7CC2KfaoBnDnzbpzzzvf7zjvawC9B9X1ZKZKHtS:bHoUTycrxXOTJoBn-tkSY8kLWIA=:eyJzY29wZSI6ImFudC12b2ljZSIsImRlYWRsaW5lIjoxNTk5NTMxODQ5fQ==
     * prefix : null
     * address : http://qfah2px93.hn-bkt.clouddn.com/
     */

    private String token;
    private String prefix;
    private String address;

    public static void setQiNiuInfo(QiNiuYunBean data) {
        String userInfo = GsonUtils.toJson(data);
        SPUtils.set(ConstantValue.QI_NIU_INFO, userInfo);
        setQiNiuToken(data.getToken());
    }

    public static QiNiuYunBean getQiNiuInfo() {
        QiNiuYunBean qiNiuYunBean = GsonUtils.fromJson(SPUtils.getString(ConstantValue.QI_NIU_INFO), QiNiuYunBean.class);
        if (qiNiuYunBean != null) {
            return qiNiuYunBean;
        } else {
            return new QiNiuYunBean();
        }
    }

    /**
     * 设置token
     */
    public static void setQiNiuToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            SPUtils.set(ConstantValue.QI_NIU_TOKEN, token);
        } else {
            SPUtils.set(ConstantValue.QI_NIU_TOKEN, "");
        }
    }

    /**
     * 获取token
     */
    public static String getQiNiuToken() {
        return (String) SPUtils.get(ConstantValue.QI_NIU_TOKEN, "");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
