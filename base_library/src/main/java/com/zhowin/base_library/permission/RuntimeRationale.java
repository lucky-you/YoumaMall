package com.zhowin.base_library.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;

import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhowin.base_library.R;

import java.util.List;

/**
 * 显示权限的dialog
 */
public final class RuntimeRationale implements Rationale<List<String>> {

    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_rationale,
                TextUtils.join("\n", permissionNames));

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.cancel();
                    }
                }).create();
        dialog.show();//在调用AlertDialog的show()方法后进行修改字体颜色，否则会报错。
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }
}