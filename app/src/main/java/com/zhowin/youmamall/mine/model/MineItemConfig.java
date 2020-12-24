package com.zhowin.youmamall.mine.model;

/**
 * author : zho
 * date  ：2020/12/24
 * desc ：我的界面配置
 */
public class MineItemConfig {


    /**
     * open_upgrade : 1
     * open_withdraw : 1
     */

    private String open_upgrade;
    private String open_withdraw;

    public String getOpen_upgrade() {
        return open_upgrade;
    }

    public void setOpen_upgrade(String open_upgrade) {
        this.open_upgrade = open_upgrade;
    }

    public String getOpen_withdraw() {
        return open_withdraw;
    }

    public void setOpen_withdraw(String open_withdraw) {
        this.open_withdraw = open_withdraw;
    }
}
