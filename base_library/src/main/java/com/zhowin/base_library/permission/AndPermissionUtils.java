package com.zhowin.base_library.permission;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.yanzhenjie.permission.runtime.PermissionDef;
import com.zhowin.base_library.R;
import com.zhowin.base_library.utils.ToastUtils;

import java.util.List;

/**
 * author      : Z_B
 * date       : 2018/8/31
 * function  : 请求权限的工具类
 */
public class AndPermissionUtils {

    /**
     * 单个权限
     *
     * @param context               上下文
     * @param permissions           权限
     * @param andPermissionListener 回调
     */
    public static void requestPermission(final Context context, final AndPermissionListener andPermissionListener, @PermissionDef String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (andPermissionListener != null) {
                            andPermissionListener.PermissionSuccess(permissions);
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        if (andPermissionListener != null) {
                            andPermissionListener.PermissionFailure(permissions);
                        }
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            showSettingDialog(context, permissions);
                        }
                    }
                })
                .start();


    }

    /**
     * 用户禁止了权限，并且勾选了禁止后不在询问，显示的dialog
     *
     * @param context     上下文
     * @param permissions 权限
     */
    private static void showSettingDialog(final Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(context);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

    }

    private static void setPermission(final Context context) {
//        AndPermission.with(context).runtime().setting().start(REQUEST_CODE_SETTING);
        ToastUtils.showToast(context.getString(R.string.message_setting_comeback));

    }

}
