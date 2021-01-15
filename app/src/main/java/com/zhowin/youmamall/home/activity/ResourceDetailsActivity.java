package com.zhowin.youmamall.home.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhowin.base_library.callback.OnCenterHitMessageListener;
import com.zhowin.base_library.callback.OnPasswordEditListener;
import com.zhowin.base_library.dialog.PasswordDialog;
import com.zhowin.base_library.http.HttpCallBack;
import com.zhowin.base_library.model.UserInfo;
import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.base_library.utils.SplitUtils;
import com.zhowin.base_library.utils.ToastUtils;
import com.zhowin.base_library.view.CenterHitMessageDialog;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityResourceDetailsBinding;
import com.zhowin.youmamall.home.model.ConfirmOrderInfo;
import com.zhowin.youmamall.home.model.ResourcesDetailsInfo;
import com.zhowin.youmamall.http.HttpRequest;
import com.zhowin.youmamall.mine.activity.SetPasswordActivity;
import com.zhowin.youmamall.mine.callback.OnSelectPaymentClickListener;
import com.zhowin.youmamall.mine.dialog.SelectPaymentTypeDialog;

/**
 * 资源详情
 */
public class ResourceDetailsActivity extends BaseBindActivity<ActivityResourceDetailsBinding> {


    private int itemId;
    private int paymentType = 0;
    private boolean isShowLinksUrl; //是否显示支付后的内容

    public static void start(Context context, int itemId) {
        Intent intent = new Intent(context, ResourceDetailsActivity.class);
        intent.putExtra(ConstantValue.ID, itemId);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_resource_details;
    }

    @Override
    public void initView() {
        itemId = getIntent().getIntExtra(ConstantValue.ID, -1);
        setOnClick(R.id.tvSubmitOrder);

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getResourcesDetails(itemId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmitOrder:
                showPaymentDialog();
                break;
        }
    }


    private void showPaymentDialog() {
        SelectPaymentTypeDialog paymentTypeDialog = SelectPaymentTypeDialog.newInstance(paymentType);
        paymentTypeDialog.show(getSupportFragmentManager(), "payment");
        paymentTypeDialog.setOnSelectPaymentClickListener(new OnSelectPaymentClickListener() {
            @Override
            public void onSelectPayment(int type) {
                paymentType = type;
                switch (type) {
                    case 1: //余额支付
                        int isSetPassword = UserInfo.getUserInfo().getIs_pay_pwd();
                        if (1 == isSetPassword) {
                            showPayPasswordDialog();
                        } else {
                            setCommissionPaymentPassword();
                        }
                        break;
                    case 2: //支付宝支付
                    case 3: //微信支付
                        confirmOrder("");
                        break;
                }
            }
        });
    }


    private void setCommissionPaymentPassword() {
        new CenterHitMessageDialog(mContext, "您尚未设置支付密码", new OnCenterHitMessageListener() {
            @Override
            public void onNegativeClick(Dialog dialog) {
            }

            @Override
            public void onPositiveClick(Dialog dialog) {
                SetPasswordActivity.start(mContext, 0);
            }
        }).setNegativeButton("再想想")
                .setPositiveButton("去设置")
                .show();
    }

    private void showPayPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog(mContext);
        dialog.setOnPasswordEditListener(new OnPasswordEditListener() {
            @Override
            public void onCancelPayment() {

            }

            @Override
            public void onDeterminePayment(String password) {
                confirmOrder(password);
            }
        });
        dialog.show();
    }

    private void confirmOrder(String password) {
        showLoadDialog();
        HttpRequest.startResourcesPayment(this, itemId, paymentType, password, new HttpCallBack<ConfirmOrderInfo>() {
            @Override
            public void onSuccess(ConfirmOrderInfo confirmOrderInfo) {
                dismissLoadDialog();
                switch (paymentType) {
                    case 1:
                        ToastUtils.showToast("支付成功");
                        getResourcesDetails(itemId);
                        break;
                    case 2: //支付宝支付
                    case 3://微信支付
                        String paymentUrl = confirmOrderInfo.getUrl();
                        String paymentTitle = 2 == paymentType ? "支付宝支付" : "微信支付";
                        WebViewActivity.start(mContext, paymentTitle, paymentUrl, true);
                        break;
                }
                paymentType = 0;
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }

    private void getResourcesDetails(int itemId) {
        showLoadDialog();
        HttpRequest.getResourcesDetails(this, itemId, new HttpCallBack<ResourcesDetailsInfo>() {
            @Override
            public void onSuccess(ResourcesDetailsInfo resourcesInfo) {
                dismissLoadDialog();
                if (resourcesInfo != null) {
                    mBinding.tvTitleView.setTitle(resourcesInfo.getTitle());
                    String paidLinksUrl = resourcesInfo.getPaid_links();
                    switch (resourcesInfo.getIs_pay()) {
                        case 0:
                            if (TextUtils.isEmpty(paidLinksUrl)) {
                                setUpWebView(mBinding.mWebView, resourcesInfo.getContent(), false);
                            } else {
                                setUpWebView(mBinding.mWebView, paidLinksUrl, true);
                            }
                            break;
                        case 1:
                            setUpWebView(mBinding.mWebView, resourcesInfo.getContent(), false);
                            break;
                        case 2:
                            setUpWebView(mBinding.mWebView, paidLinksUrl, true);
                            break;
                    }
                    mBinding.clResourceBottomLayout.setVisibility(1 == resourcesInfo.getIs_pay() ? View.VISIBLE : View.GONE);
                    String totalPrice = "付费: ¥" + resourcesInfo.getPrice();
                    SpannableString spannableText = SplitUtils.getTextColor(totalPrice, 4, totalPrice.length(), getBaseColor(R.color.color_E83219));
                    mBinding.tvTotalAmount.setText(spannableText);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                dismissLoadDialog();
                ToastUtils.showToast(errorMsg);
            }
        });
    }


    public void setUpWebView(WebView mWebView, String contentUrl, boolean isUrl) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setDefaultTextEncodingName("UTF-8");
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        if (isUrl) {
            mWebView.loadUrl(contentUrl);//url地址
        } else {
            //HTML
            mWebView.loadDataWithBaseURL(null, getNewContent(contentUrl), "text/html", "utf-8", null);
        }
    }


    public static String getNewContent(String htmlText) {
        String htmlContent = "<html><head><meta charset='utf-8' name='viewport' content='width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no'/><style type=\"text/css\">img{ max-width:100%;max-height:100%; -webkit-tap-highlight-color:rgba(0,0,0,0);} video{max-width:100%;background-color:#ffffff;}image{max-width:100%;background-color:#000000;}</style> <script type=\"text/javascript\"> </script> </head> <body> <div> <div id=\"webview_content_wrapper\">" + htmlText + "</div> </div> </body> </html>";
        return htmlContent;
    }

}
