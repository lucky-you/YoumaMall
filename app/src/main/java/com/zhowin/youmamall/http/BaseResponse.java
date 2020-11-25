package com.zhowin.youmamall.http;


import java.util.List;

/**
 * author : zho
 * date  ：2020/9/24
 * desc ： 返回带分页参数的基类
 */
public class BaseResponse<T> {


    /**
     * records : [{"roomId":4,"title":"测试直播间","coverPictureKey":"hijklmg.jpg","decoratePicture":null,"backgroundPictureKey":null,"typeId":1,"typeName":"娱乐房","allowMicFree":0,"existPwd":true,"owner":18}]
     * total : 1
     * size : 10
     * pages : 1
     * current : 1
     */

    private int total;
    private int size;
    private int pages;
    private int current;
    private List<T> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
