package com.zhowin.youmamall.mine.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.text.SpannableString;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SplitUtils;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityOpenAgentBinding;
import com.zhowin.youmamall.mine.adapter.OpenAgentListAdapter;
import com.zhowin.youmamall.mine.model.AgentList;

import java.util.ArrayList;
import java.util.List;

/**
 * 开通代理、开通vip共用
 * type : 1-->代理  2-->vip
 */
public class OpenAgentActivity extends BaseBindActivity<ActivityOpenAgentBinding> implements RadioGroup.OnCheckedChangeListener {


    private int classType;
    private int payType;
    private OpenAgentListAdapter openAgentListAdapter;
    private List<AgentList> agentLists = new ArrayList<>();


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, OpenAgentActivity.class);
        intent.putExtra(ConstantValue.TYPE, type);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_open_agent;
    }

    @Override
    public void initView() {
        classType = getIntent().getIntExtra(ConstantValue.TYPE, -1);
        mBinding.tvTitleView.setTitle(1 == classType ? "开通代理" : "开通VIP");
        mBinding.rgPaymentButton.setOnCheckedChangeListener(this::onCheckedChanged);
    }

    @Override
    public void initData() {
        agentLists.add(new AgentList("永久会员", "40", "735"));
        agentLists.add(new AgentList("年度会员", "30", "655"));
        agentLists.add(new AgentList("季度会员", "20", "215"));
        agentLists.add(new AgentList("月度会员", "10", "105"));
        openAgentListAdapter = new OpenAgentListAdapter(agentLists);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(openAgentListAdapter);

        String totalPrice = "合计: ¥" + "105.0";
        SpannableString spannableText = SplitUtils.getTextColor(totalPrice, 4, totalPrice.length(), getBaseColor(R.color.color_E83219));
        mBinding.tvTotalAmount.setText(spannableText);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbWxPay:
                payType = 2;
                break;
            case R.id.rbZFBPay:
                payType = 1;
                break;
        }
    }
}
