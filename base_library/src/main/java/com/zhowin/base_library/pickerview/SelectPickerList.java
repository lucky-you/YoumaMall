package com.zhowin.base_library.pickerview;

import android.os.Parcel;
import android.os.Parcelable;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * author Z_B
 * date :2020/1/13 16:48
 * description: 样式选择的model类
 */
public class SelectPickerList implements IPickerViewData, Parcelable {

    private int selectId;
    private String selectTitle;

    public SelectPickerList(int selectId, String selectTitle) {
        this.selectId = selectId;
        this.selectTitle = selectTitle;
    }

    protected SelectPickerList(Parcel in) {
        selectTitle = in.readString();
        selectId = in.readInt();
    }

    public static final Creator<SelectPickerList> CREATOR = new Creator<SelectPickerList>() {
        @Override
        public SelectPickerList createFromParcel(Parcel in) {
            return new SelectPickerList(in);
        }

        @Override
        public SelectPickerList[] newArray(int size) {
            return new SelectPickerList[size];
        }
    };

    public String getSelectTitle() {
        return selectTitle;
    }

    public void setSelectTitle(String selectTitle) {
        this.selectTitle = selectTitle;
    }

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    @Override
    public String getPickerViewText() {
        return getSelectTitle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(selectTitle);
        dest.writeInt(selectId);
    }
}
