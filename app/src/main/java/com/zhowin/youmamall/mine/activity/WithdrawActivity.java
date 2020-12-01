package com.zhowin.youmamall.mine.activity;


import com.zhowin.base_library.utils.SpanUtils;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityWithdrawBinding;

/**
 * 佣金提现
 */
public class WithdrawActivity extends BaseBindActivity<ActivityWithdrawBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initView() {
        SpanUtils.with(mBinding.tvHitMessage)
                .appendLine("温馨提示：").setBold()
                .appendLine()
                .appendLine("1.不限次数，不限金额，单笔提现费2元；")
                .appendLine("2.请正确填写支付宝账号和姓名，避免造成提现不到账；")
                .appendLine("3.提现时间：周一至周五9:00-17:30，提现当天到账。非工作日内提现，将由下一个工作日到账。")
                .create();
    }

    @Override
    public void initData() {

    }
}
