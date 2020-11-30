package com.zhowin.youmamall.mine.activity;


import android.view.View;

import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivitySettingBinding;
import com.zhowin.youmamall.mine.callback.OnHitMessageClickListener;
import com.zhowin.youmamall.mine.dialog.ChangeNickNameDialog;
import com.zhowin.youmamall.mine.dialog.HitMessageDialog;

public class SettingActivity extends BaseBindActivity<ActivitySettingBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        setOnClick(R.id.civUserHead, R.id.tvUserNickName, R.id.tvUserMobile, R.id.tvSetPayPassword, R.id.llQRCodeLayout);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civUserHead:
                break;
            case R.id.tvUserNickName:
                showChangeNIckName();
                break;
            case R.id.tvUserMobile:
                showChangeMobile();
                break;
            case R.id.tvSetPayPassword:
                startActivity(SetPasswordActivity.class);
                break;
            case R.id.llQRCodeLayout:
                break;

        }
    }

    private void showChangeMobile() {
        HitMessageDialog hitMessageDialog = new HitMessageDialog(mContext);
        hitMessageDialog.show();
        hitMessageDialog.setOnHitMessageClickListener(new OnHitMessageClickListener() {
            @Override
            public void onDetermineClick(String text) {
                startActivity(ChangeMobileActivity.class);
            }
        });
    }

    private void showChangeNIckName() {
        ChangeNickNameDialog changeNickNameDialog = new ChangeNickNameDialog(mContext);
        changeNickNameDialog.show();
        changeNickNameDialog.setEditNickName("周小川");
        changeNickNameDialog.setOnHitMessageClickListener(new OnHitMessageClickListener() {
            @Override
            public void onDetermineClick(String text) {

            }
        });
    }

}
