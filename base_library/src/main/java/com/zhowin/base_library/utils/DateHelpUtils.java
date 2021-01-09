package com.zhowin.base_library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳
 */
public class DateHelpUtils {

    static SimpleDateFormat mDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 获取当前时间
     */
    public static String getCurrentData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    public static String getCurrentTime() {
        long time = System.currentTimeMillis() / 1000;
        return String.valueOf(time);
    }


    /**
     * 转换成当天时间
     */
    public static String getStringDateOfDay(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getStringDate(long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 没有秒
     */
    public static String getStrTimeNotSeconds(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(date);
    }


    public static String getCurrentDayNotYear(long timeStamp) {
        Date date = new Date(timeStamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }


    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static String getDurationInString(long time) {
        String durStr = "";
        if (time == 0) {
            return "0秒";
        }
        time = time / 1000;
        long day = time / (60 * 60 * 24);
        time = time - (60 * 60 * 24) * day;
        long hour = time / (60 * 60);
        time = time - (60 * 60) * hour;
        long min = time / 60;
        time = time - 60 * min;
        long sec = time;
        if (day != 0) {
            durStr = day + "天" + hour + "时" + min + "分" + sec + "秒";
        } else if (hour != 0) {
            durStr = hour + "时" + min + "分" + sec + "秒";
        } else if (min != 0) {
            durStr = min + "分" + sec + "秒";
        } else {
            durStr = sec + "秒";
        }
        return durStr;
    }

    public static String getPostDetailTime(long timeStamp) {
        long totleTime = System.currentTimeMillis() - timeStamp;
        if (totleTime >= 24 * 60 * 60 * 1000) {
            Date date = new Date(timeStamp * 1000);
            return mDateFormat1.format(date);
        } else if (totleTime >= 60 * 60 * 1000) {
            return totleTime / (60 * 60 * 1000) + "小时前";
        } else if (totleTime >= 60 * 1000) {
            return totleTime / (60 * 1000) + "分钟前";
        } else {
            if (totleTime < 1000) {
                return "刚刚";
            }
            return totleTime / 1000 + "秒前";
        }
    }

}
