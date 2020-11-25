package com.zhowin.base_library.utils;

import android.text.TextUtils;

/**
 * author      : Z_B
 * date       : 2018/11/16
 * function  : 手机号码的检测
 */
public final class PhoneUtils {


    /**
     * 隐藏电话号码中间的四位数
     */
    public static String hitCenterMobilNumber(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 手机号验证
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {

        return checkPhone(phone, false);
    }


    /**
     * 手机号验证
     * 已经匹配到最新的号码段
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone, boolean toast) {
        if (TextUtils.isEmpty(phone)) {
            if (toast) ToastUtils.showToast("手机号为空");
            return false;
        }
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (phone.length() != 11 || !phone
                .matches(PHONE_NUMBER_REG)) {
            if (toast) ToastUtils.showToast("手机号格式不对");
            return false;
        }
        return true;
    }



}
