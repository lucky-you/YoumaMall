package com.zhowin.base_library.pickerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.zhowin.base_library.R;

import java.util.ArrayList;

/**
 * author : Z_B
 * date : 2018/8/28
 * function : 省市区三级选择的帮助类
 */
public class PickerViewCityUtils {

    private ArrayList<ProvinceCityBean> provinceList = new ArrayList<>(); //省份
    private ArrayList<ArrayList<ProvinceCityBean>> provinceCityList = new ArrayList<>();//省、市
    private ArrayList<ArrayList<ArrayList<ProvinceCityBean>>> provinceCityAreaList = new ArrayList<>();//省、市、区
    private static final int MSG_LOAD_DATA = 0x0001; // 开始解析数据
    private static final int MSG_LOAD_SUCCESS = 0x0002; // 解析数据成功
    private Activity mActivity;
    private OnCitySelectClickListener onCitySelectClickListener;
    private int jsonType;//不同的地方解析不同的json

    public PickerViewCityUtils(Activity activity, int type) {
        this.mActivity = activity;
        this.jsonType = type;
        mHandler.sendEmptyMessage(MSG_LOAD_DATA); //发个消息，开始解析数据
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    new Thread() {
                        @Override
                        public void run() {
                            // 子线程中解析省市区数据
                            if (1 == jsonType) {
                                initAreaJsonData();
                            } else {
                                initJsonData();
                            }
                        }
                    }.start();
                    break;
                case MSG_LOAD_SUCCESS:
                    //获取数据成功,显示控件
                    showPickerView();
                    break;
            }
        }
    };


    public void setOnCitySelectClickListener(OnCitySelectClickListener onCitySelectClickListener) {
        this.onCitySelectClickListener = onCitySelectClickListener;
    }

    /**
     * 弹出选择器
     */
    private void showPickerView() {
        if (provinceList.isEmpty() || provinceCityAreaList.isEmpty()) return;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mActivity, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String provinceName = provinceList.get(options1).getPickerViewText();
                int provinceId = provinceList.get(options1).getId();
                String cityName = provinceCityList.get(options1).get(options2).getPickerViewText();
                int cityId = provinceCityList.get(options1).get(options2).getId();
                String areaName = provinceCityAreaList.get(options1).get(options2).get(options3).getPickerViewText();
                int areaId = provinceCityAreaList.get(options1).get(options2).get(options3).getId();
                if (onCitySelectClickListener != null) {
                    onCitySelectClickListener.onSelectCityResult(provinceName, cityName, areaName, provinceId, cityId, areaId);
                }
            }
        })
                .setTitleText(mActivity.getString(R.string.select_city))
                .setTitleColor(mActivity.getResources().getColor(R.color.color_999))
                .setTitleSize(15)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setCancelColor(mActivity.getResources().getColor(R.color.color_666))
                .setSubmitColor(mActivity.getResources().getColor(R.color.color_8c86fa))
                .setDecorView((ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
//        pvOptions.setPicker(provinceList);//一级选择器
        if (3 == jsonType) {
            pvOptions.setPicker(provinceList, provinceCityList, provinceCityAreaList);//三级选择器
        } else {
            pvOptions.setPicker(provinceList, provinceCityList);//二级选择器
        }
        pvOptions.show();
    }

    /**
     * 解析数据,不包含全国
     */
    private void initJsonData() {
        String JsonData = new GetJsonDataUtil().getJson(mActivity, "province_id.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceCityBean> jsonBean = new GetJsonDataUtil().parseData(JsonData);//用Gson 转成实体
        provinceList = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<ProvinceCityBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<ProvinceCityBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getChild().size(); c++) {//遍历该省份的所有城市
                ProvinceCityBean CityName = jsonBean.get(i).getChild().get(c);
                CityList.add(CityName);//添加城市
                ArrayList<ProvinceCityBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getChild().get(c).getChild() == null
                        || jsonBean.get(i).getChild().get(c).getChild().size() == 0) {
                    City_AreaList.add(new ProvinceCityBean());
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getChild().get(c).getChild());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            provinceCityList.add(CityList);
            provinceCityAreaList.add(Province_AreaList);
        }
        //发消息，解析数据完成
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    /**
     * 解析数据，包含全国
     */
    private void initAreaJsonData() {
        String JsonData = new GetJsonDataUtil().getJson(mActivity, "province_city_area.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceCityBean> jsonBean = new GetJsonDataUtil().parseData(JsonData);//用Gson 转成实体
        provinceList = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<ProvinceCityBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<ProvinceCityBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getChild().size(); c++) {//遍历该省份的所有城市
                ProvinceCityBean CityName = jsonBean.get(i).getChild().get(c);
                CityList.add(CityName);//添加城市
                ArrayList<ProvinceCityBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getChild().get(c).getChild() == null
                        || jsonBean.get(i).getChild().get(c).getChild().size() == 0) {
                    City_AreaList.add(new ProvinceCityBean());
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getChild().get(c).getChild());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            provinceCityList.add(CityList);
            provinceCityAreaList.add(Province_AreaList);
        }
        //发消息，解析数据完成
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }
}
