package com.zhowin.base_library.pickerview;

/**
 * author      : Z_B
 * date       : 2018/10/12
 * function  : 地区选择器结果的回调
 */
public interface OnCitySelectClickListener {

    void onSelectCityResult(String provinceName, String cityName, String areaName, int provinceId, int cityId, int areaId);
}
