package com.zhowin.youmamall.mine.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.text.SpannableString;
import android.view.View;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SplitUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.widget.DivideLineItemDecoration;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityOpenAgentBinding;
import com.zhowin.youmamall.home.activity.WebViewActivity;
import com.zhowin.youmamall.home.model.ConfirmOrderInfo;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.adapter.OpenAgentListAdapter;
import com.zhowin.youmamall.mine.model.AgentList;

import java.util.ArrayList;
import java.util.List;

/**
 * 开通代理、开通vip共用
 * type : 1-->代理  2-->vip会员
 */
public class OpenAgentActivity extends BaseBindActivity<ActivityOpenAgentBinding> implements
        RadioGroup.OnCheckedChangeListener, BaseQuickAdapter.OnItemClickListener {


    private int classType, payType, selectItemId;
    private OpenAgentListAdapter openAgentListAdapter;


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
        mBinding.tvTitleView.setTitle(1 == classType ? "开通代理" : "开通会员");
        mBinding.rgPaymentButton.setOnCheckedChangeListener(this::onCheckedChanged);
        setOnClick(R.id.tvStartPayment);
        getVipOrAgentRule();
    }

    @Override
    public void initData() {
        openAgentListAdapter = new OpenAgentListAdapter(new ArrayList<>(), classType);
        openAgentListAdapter.setCurrentPosition(0);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.recyclerView.addItemDecoration(new DivideLineItemDecoration(mContext, getBaseColor(R.color.color_f6f6f6), 1));
        mBinding.recyclerView.setAdapter(openAgentListAdapter);
        openAgentListAdapter.setOnItemClickListener(this::onItemClick);
    }


    private void setBottomAgentPrice(String price) {
        String totalPrice = "合计: ¥" + price;
        SpannableString spannableText = SplitUtils.getTextColor(totalPrice, 4, totalPrice.length(), getBaseColor(R.color.color_E83219));
        mBinding.tvTotalAmount.setText(spannableText);
    }


    private void getVipOrAgentRule() {
        showLoadDialog();
        HttpRequest.getVipOrAgentRule(this, 2 == classType, new HttpCallBack<List<AgentList>>() {
            @Override
            public void onSuccess(List<AgentList> agentLists) {
                dismissLoadDialog();
                if (agentLists != null && !agentLists.isEmpty()) {
                    setBottomAgentPrice(agentLists.get(0).getOpen_price());
                    selectItemId = agentLists.get(0).getId();
                    openAgentListAdapter.setNewData(agentLists);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartPayment:
                if (0 == payType) {
                    ToastUtils.showToast("请选择支付方式");
                    return;
                }
                if (1 == classType) {
                    openAgent();
                } else {
                    openVIP();
                }
                break;
        }
    }

    /**
     * 开通VIP
     */
    private void openVIP() {
        showLoadDialog();
        HttpRequest.openVIP(this, payType, new HttpCallBack<ConfirmOrderInfo>() {
            @Override
            public void onSuccess(ConfirmOrderInfo confirmOrderInfo) {
                dismissLoadDialog();
                if (confirmOrderInfo != null) {
                    String paymentUrl = confirmOrderInfo.getUrl();
                    String paymentTitle = 2 == payType ? "支付宝支付" : "微信支付";
                    WebViewActivity.start(mContext, paymentTitle, paymentUrl, true);

                }

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });

    }

    /**
     * 开通代理
     */
    private void openAgent() {
        showLoadDialog();
        HttpRequest.openAgent(this, payType, selectItemId, new HttpCallBack<ConfirmOrderInfo>() {
            @Override
            public void onSuccess(ConfirmOrderInfo confirmOrderInfo) {
                dismissLoadDialog();
                if (confirmOrderInfo != null) {
                    String paymentUrl = confirmOrderInfo.getUrl();
                    String paymentTitle = 2 == payType ? "支付宝支付" : "微信支付";
                    WebViewActivity.start(mContext, paymentTitle, paymentUrl, true);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbWxPay:
                payType = 3;
                break;
            case R.id.rbZFBPay:
                payType = 2;
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        openAgentListAdapter.setCurrentPosition(position);
        setBottomAgentPrice(openAgentListAdapter.getItem(position).getOpen_price());
        selectItemId = openAgentListAdapter.getItem(position).getId();
    }
}
