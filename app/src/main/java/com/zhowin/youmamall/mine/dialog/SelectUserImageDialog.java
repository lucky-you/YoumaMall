package com.zhowin.youmamall.mine.dialog;

import android.view.View;

import com.zhowin.base_library.base.BaseDialogFragment;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.mine.callback.OnSelectUserHeadDialogListener;


/**
 * author Z_B
 * date :2020/5/27 11:00
 * description:
 */
public class SelectUserImageDialog extends BaseDialogFragment {
    private OnSelectUserHeadDialogListener onSelectUserHeadDialogListener;

    public void setOnSelectUserHeadDialogListener(OnSelectUserHeadDialogListener onSelectUserHeadDialogListener) {
        this.onSelectUserHeadDialogListener = onSelectUserHeadDialogListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_select_user_image_layout;
    }

    @Override
    public void initView() {
        get(R.id.tvTakePicture).setOnClickListener(this::onClick);
        get(R.id.tvAlbumSelection).setOnClickListener(this::onClick);
        get(R.id.tvCancel).setOnClickListener(this::onClick);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onViewClick(View view) {
        int id = view.getId();
        if (id == R.id.tvTakePicture) {
            if (onSelectUserHeadDialogListener != null) {
                onSelectUserHeadDialogListener.onTakePicture();
            }
        } else if (id == R.id.tvAlbumSelection) {
            if (onSelectUserHeadDialogListener != null) {
                onSelectUserHeadDialogListener.onAlbumSelection();
            }
        } else if (id == R.id.tvCancel) {

        }
        dismiss();
    }
}
