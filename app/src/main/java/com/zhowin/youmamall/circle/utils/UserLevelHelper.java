package com.zhowin.youmamall.circle.utils;

import com.zhowin.youmamall.R;

/**
 * author : zho
 * date  ：2020/12/9
 * desc ：
 */
public class UserLevelHelper {


    public static int getUserLevel(int level) {
        if (0==level) {
            return levelList[level];
        } else {
            return levelList[level - 1];
        }
    }

    public static int[] levelList = {
            R.drawable.icon_test_1,
            R.drawable.icon_test_2,
            R.drawable.icon_test_3,
            R.drawable.icon_test_4,
            R.drawable.icon_test_5,
            R.drawable.icon_test_6,
            R.drawable.icon_test_7,
            R.drawable.icon_test_8,
            R.drawable.icon_test_9
    };
}
