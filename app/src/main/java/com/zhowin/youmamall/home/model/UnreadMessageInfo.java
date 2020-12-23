package com.zhowin.youmamall.home.model;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/23
 * desc ：未读消息
 */
public class UnreadMessageInfo {


    /**
     * read_num : 2
     * money_log : ["恭喜136****7719用户成功分享，获得推广收益107.00元","恭喜199****5393用户成功分享，获得推广收益100.00元","恭喜138****8888用户成功分享，获得推广收益18.00元","恭喜136****7786用户成功分享，获得推广收益1.00元","恭喜138****8888用户成功分享，获得推广收益18.00元","恭喜136****7786用户成功分享，获得推广收益1.00元","恭喜138****8888用户成功分享，获得推广收益8.00元","恭喜158****2331用户成功分享，获得推广收益11.00元","恭喜138****8888用户成功分享，获得推广收益8.00元","恭喜158****2331用户成功分享，获得推广收益11.00元","恭喜158****2331用户成功分享，获得推广收益11.00元","恭喜158****2331用户成功分享，获得推广收益11.00元","恭喜158****2331用户成功分享，获得推广收益11.00元","恭喜158****2331用户成功分享，获得推广收益11.00元"]
     */

    private int read_num;
    private List<String> money_log;

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public List<String> getMoney_log() {
        return money_log;
    }

    public void setMoney_log(List<String> money_log) {
        this.money_log = money_log;
    }
}
