package com.zhowin.youmamall.home.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityWebViewBinding;

/**
 * 网页显示通用
 */
public class WebViewActivity extends BaseBindActivity<ActivityWebViewBinding> {


    private String payTitle, paymentUrl;
    private boolean isUrl;

    public static void start(Context context, String title, String url, boolean isUrl) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(ConstantValue.TITLE, title);
        intent.putExtra(ConstantValue.URL, url);
        intent.putExtra(ConstantValue.TYPE, isUrl);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        payTitle = getIntent().getStringExtra(ConstantValue.TITLE);
        paymentUrl = getIntent().getStringExtra(ConstantValue.URL);
        isUrl = getIntent().getBooleanExtra(ConstantValue.TYPE, true);
        mBinding.tvTitleView.setTitle(payTitle);
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(paymentUrl)) {
            setUpWebView(mBinding.mWebView, paymentUrl, isUrl);
        }
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
            mWebView.loadDataWithBaseURL(null, contentUrl, "text/html", "utf-8", null);
        }
    }


}
