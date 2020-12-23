package com.zhowin.youmamall.home.callback;

import com.zhowin.youmamall.home.model.VipWelfareList;

/**
 * author : zho
 * date  ：2020/12/9
 * desc ：右侧更多 和 福利item共用的回调
 */
public interface OnHomeSeeMoreListener {


    void onRightSeeMore();

    void onFLGNItemClick(VipWelfareList item);

}
