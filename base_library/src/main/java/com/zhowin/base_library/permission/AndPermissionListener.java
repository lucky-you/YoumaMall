package com.zhowin.base_library.permission;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/9/6
 * function  : 获取权限的回调
 */
public interface AndPermissionListener {

    /**
     * 获取权限成功
     *
     * @param permissions
     */
    void PermissionSuccess(List<String> permissions);

    /**
     * 获取权限失败
     *
     * @param permissions
     */
    void PermissionFailure(List<String> permissions);
}
