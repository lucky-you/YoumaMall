package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/11/27
 * desc ：分类列表
 */
public class ColumnList {

    private int drawable;
    private String title;

    public ColumnList(int drawable, String title) {
        this.drawable = drawable;
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
