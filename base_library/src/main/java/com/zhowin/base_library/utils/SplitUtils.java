package com.zhowin.base_library.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * author Z_B
 * date :2020/6/12 15:13
 * description: 分割字符串的帮助类
 */
public class SplitUtils {


    /**
     * 数组中是否包含某个元素
     */
    public static boolean containList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    public static String getStringTextId(List<String> stringList) {
        String returnStringTxt = "";
        StringBuffer stringBuffer = new StringBuffer();
        for (String item : stringList) {
            stringBuffer.append(item + ",");
        }
        if (stringBuffer != null && stringBuffer.length() > 0) {
            returnStringTxt = stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        return returnStringTxt;
    }


    /**
     * 分割字符串
     *
     * @param text 需要分割的字符串
     * @param type 根据什么类分割
     * @return 返回的数组
     */
    public static List<String> spiltCode(String text, String type) {
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(text, type);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }

    public static List<Integer> spiltIdOfData(String text, String type) {
        List<Integer> list = new ArrayList<Integer>();
        StringTokenizer st = new StringTokenizer(text, type);
        while (st.hasMoreTokens()) {
            list.add(Integer.parseInt(st.nextToken()));
        }
        return list;
    }

    /**
     * 比较两个集合数据是否相同
     */
    public static <T> boolean compareList(List<T> list1, List<T> list2) {
        if (list1 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        Set<Integer> hashCodeSet = new HashSet<>();
        for (T adInfoData : list1) {
            hashCodeSet.add(adInfoData.hashCode());
        }
        for (T adInfoData : list2) {
            if (!hashCodeSet.contains(adInfoData.hashCode())) {
                return false;
            }
        }
        return true;
    }


    public static SpannableString getTextColor(String text, int startIndex, int endIndex, int color) {
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(color), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }


}
