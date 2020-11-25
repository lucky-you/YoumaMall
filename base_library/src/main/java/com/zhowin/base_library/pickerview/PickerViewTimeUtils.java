package com.zhowin.base_library.pickerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.zhowin.base_library.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author      : Z_B
 * date       : 2018/9/26
 * function  : 选择时间的帮助类
 */
public class PickerViewTimeUtils {

    private static TimePickerView timePickerView;

    /**
     * 选择时间
     *
     * @param onSelectTimeClickListener Dialog 模式下，在底部弹出
     */
    public static void selectTimePickerView(Activity mContext, final OnSelectTimeClickListener onSelectTimeClickListener) {

        timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (onSelectTimeClickListener != null) {
                    onSelectTimeClickListener.onDateTime(getDateTimeOfNoSecond(date));
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDecorView((ViewGroup) mContext.getWindow().getDecorView().findViewById(android.R.id.content))
                .setDate(currentCalendar())
                .setRangDate(startCalendar(), currentCalendar())
                .setCancelColor(mContext.getResources().getColor(R.color.color_f3f3f3))
                .setSubmitColor(mContext.getResources().getColor(R.color.color_8c86fa))
                .build();
        timePickerView.show();
    }

    /**
     * 非Dialog模式
     */
    public static void selectTimePickerViewNoDialog(Context context, ViewGroup decorView, final OnSelectTimeClickListener onSelectTimeClickListener) {
        TimePickerView timePickerViewNoDialog = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (onSelectTimeClickListener != null) {
                    onSelectTimeClickListener.onDateTime(getDateTimeOfNoSecond(date));
                }
            }
        })
                .setLayoutRes(R.layout.include_select_time_view_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }

                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        if (onSelectTimeClickListener != null) {
                            onSelectTimeClickListener.onDateTime(getDateTimeOfNoSecond(date));
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDividerColor(Color.WHITE)
                .setContentTextSize(16)
                .setDate(currentCalendar())//当前时间
                .setRangDate(startCalendar(), currentCalendar())//起始终止年月日设定
                .setDividerType(WheelView.DividerType.FILL) //分割线的样式
                .setDividerColor(context.getResources().getColor(R.color.color_ffe9eef0))
                .setDecorView(decorView)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setOutSideCancelable(false)
                .setTextColorCenter(Color.BLACK)
                .isDialog(false)
                .build();
        timePickerViewNoDialog.setKeyBackCancelable(false);
        timePickerViewNoDialog.show();

    }




    //获取设定的时间
    public static Calendar currentCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int year = calendar.get(Calendar.YEAR);    //获取年,当前年份
        int month = calendar.get(Calendar.MONTH);   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int hour = calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
        int minute = calendar.get(Calendar.MINUTE);          //获取当前分钟
        calendar.set(year, month, day, hour, minute);
        return calendar;
    }


    //获取开始时间
    public static Calendar startCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int year = calendar.get(Calendar.YEAR) - 60;    //获取年,当前年份-60年
        int month = calendar.get(Calendar.MONTH);   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int hour = calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
        int minute = calendar.get(Calendar.MINUTE);          //获取当前分钟
        calendar.set(year, month, day, hour, minute);
        return calendar;

    }

    //获取结束时间,
    public static Calendar endCalendar() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int year = calendar.get(Calendar.YEAR) + 60;    //获取年,当前年份
        int month = calendar.get(Calendar.MONTH);   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int hour = calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
        int minute = calendar.get(Calendar.MINUTE);          //获取当前分钟
        calendar.set(year, month, day, hour, minute);
        return calendar;
    }

    /**
     * 时间戳转字符串
     * 可根据需要自行截取数据显示
     */
    private static String getDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }


    private static String getDateTimeOfNoSecond(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
