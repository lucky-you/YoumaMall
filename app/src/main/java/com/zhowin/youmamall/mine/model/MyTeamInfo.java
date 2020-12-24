package com.zhowin.youmamall.mine.model;

import java.util.List;

/**
 * author : zho
 * date  ：2020/12/24
 * desc ：我的团队
 */
public class MyTeamInfo {


    /**
     * total : 2
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"user_id":15,"level":0,"upgrade_str":"","avatar":"","mobile":"15677187758","nickname":"156****7758","volume":0},{"user_id":14,"level":0,"upgrade_str":"","avatar":"","mobile":"12677187758","nickname":"12677187758","volume":0}]
     * team_size : 2
     * directly_size : 2
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private int team_size;
    private int directly_size;
    private List<MyTeamList> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getTeam_size() {
        return team_size;
    }

    public void setTeam_size(int team_size) {
        this.team_size = team_size;
    }

    public int getDirectly_size() {
        return directly_size;
    }

    public void setDirectly_size(int directly_size) {
        this.directly_size = directly_size;
    }

    public List<MyTeamList> getData() {
        return data;
    }

    public void setData(List<MyTeamList> data) {
        this.data = data;
    }


}
