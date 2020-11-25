package com.zhowin.base_library.pickerview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.zhowin.base_library.R;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/10/12
 * function  :  条件选择器的帮助类
 */
public class PickerViewConditionsUtils {

    /**
     * 条件选择器
     *
     * @param mActivity                       上下文
     * @param mDateList                       数据源
     * @param onSelectConditionsClickListener 选择的回调
     */
    public static OptionsPickerView selectConditionsView(Activity mActivity,
                                                         final List<SelectPickerList> mDateList,
                                                         int selectPosition,
                                                         final OnSelectConditionsClickListener onSelectConditionsClickListener) {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (onSelectConditionsClickListener != null) {
                    onSelectConditionsClickListener.onConditionsSelect(options1, mDateList.get(options1).getSelectTitle(), mDateList.get(options1).getSelectId());
                }
            }
        })
                .setCancelColor(mActivity.getResources().getColor(R.color.color_333))
                .setSubmitColor(mActivity.getResources().getColor(R.color.color_8c86fa))
                .setDecorView((ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content))
                .setSelectOptions(selectPosition)  //设置默认选中项
                .build();
        pvOptions.setPicker(mDateList);
        pvOptions.show();
        return pvOptions;

    }


}
