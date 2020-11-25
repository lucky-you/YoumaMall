package com.zhowin.base_library.pickerview;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/28
 * function  : 省市区的数据
 */
public class ProvinceCityBean implements IPickerViewData {


    /**
     * id : 1
     * pid : 0
     * name : 北京
     * child : [{"id":2,"pid":1,"name":"北京市","child":[{"id":3,"pid":2,"name":"东城区"},{"id":4,"pid":2,"name":"西城区"},{"id":5,"pid":2,"name":"朝阳区"},{"id":6,"pid":2,"name":"丰台区"},{"id":7,"pid":2,"name":"石景山区"},{"id":8,"pid":2,"name":"海淀区"},{"id":9,"pid":2,"name":"门头沟区"},{"id":10,"pid":2,"name":"房山区"},{"id":11,"pid":2,"name":"通州区"},{"id":12,"pid":2,"name":"顺义区"},{"id":13,"pid":2,"name":"昌平区"},{"id":14,"pid":2,"name":"大兴区"},{"id":15,"pid":2,"name":"怀柔区"},{"id":16,"pid":2,"name":"平谷区"},{"id":17,"pid":2,"name":"密云县"},{"id":18,"pid":2,"name":"延庆县"}]}]
     */

    private int id;
    private String name;
    private List<ProvinceCityBean> child;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProvinceCityBean> getChild() {
        return child;
    }

    public void setChild(List<ProvinceCityBean> child) {
        this.child = child;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }

}
