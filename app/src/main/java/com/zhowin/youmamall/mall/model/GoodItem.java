package com.zhowin.youmamall.mall.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : zho
 * date  ：2020/12/9
 * desc ： 商品的基本信息
 */
public class GoodItem implements Parcelable {

    private int id;
    private String name;
    private String price;
    private String image;

    public GoodItem() {
    }

    protected GoodItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readString();
        image = in.readString();
    }

    public static final Creator<GoodItem> CREATOR = new Creator<GoodItem>() {
        @Override
        public GoodItem createFromParcel(Parcel in) {
            return new GoodItem(in);
        }

        @Override
        public GoodItem[] newArray(int size) {
            return new GoodItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(image);
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Creator<GoodItem> getCREATOR() {
        return CREATOR;
    }
}
