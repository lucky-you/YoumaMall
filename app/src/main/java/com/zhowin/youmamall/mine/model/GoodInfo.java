package com.zhowin.youmamall.mine.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : zho
 * date  ：2020/12/18
 * desc ：商品详情
 */
public class GoodInfo implements Parcelable {

    private int goodId;
    private String categoryName;
    private int categoryId;
    private String goodName;
    private String goodPrice;
    private String goodStatusName;
    private int goodStatusId;
    private String goodContact;
    private String goodContent;
    private String goodImage;

    public GoodInfo() {
    }

    public GoodInfo(String goodName) {
        this.goodName = goodName;
    }

    protected GoodInfo(Parcel in) {
        goodId = in.readInt();
        categoryName = in.readString();
        categoryId = in.readInt();
        goodName = in.readString();
        goodPrice = in.readString();
        goodStatusName = in.readString();
        goodStatusId = in.readInt();
        goodContact = in.readString();
        goodContent = in.readString();
        goodImage = in.readString();
    }

    public static final Creator<GoodInfo> CREATOR = new Creator<GoodInfo>() {
        @Override
        public GoodInfo createFromParcel(Parcel in) {
            return new GoodInfo(in);
        }

        @Override
        public GoodInfo[] newArray(int size) {
            return new GoodInfo[size];
        }
    };

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodStatusName() {
        return goodStatusName;
    }

    public void setGoodStatusName(String goodStatusName) {
        this.goodStatusName = goodStatusName;
    }

    public int getGoodStatusId() {
        return goodStatusId;
    }

    public void setGoodStatusId(int goodStatusId) {
        this.goodStatusId = goodStatusId;
    }

    public String getGoodContact() {
        return goodContact;
    }

    public void setGoodContact(String goodContact) {
        this.goodContact = goodContact;
    }

    public String getGoodContent() {
        return goodContent;
    }

    public void setGoodContent(String goodContent) {
        this.goodContent = goodContent;
    }

    public String getGoodImage() {
        return goodImage;
    }

    public void setGoodImage(String goodImage) {
        this.goodImage = goodImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(goodId);
        dest.writeString(categoryName);
        dest.writeInt(categoryId);
        dest.writeString(goodName);
        dest.writeString(goodPrice);
        dest.writeString(goodStatusName);
        dest.writeInt(goodStatusId);
        dest.writeString(goodContact);
        dest.writeString(goodContent);
        dest.writeString(goodImage);
    }
}
