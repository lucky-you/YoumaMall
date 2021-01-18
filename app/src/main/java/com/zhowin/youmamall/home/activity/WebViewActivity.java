package com.zhowin.youmamall.home.activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhowin.base_library.utils.ConstantValue;
import com.zhowin.youmamall.BuildConfig;
import com.zhowin.youmamall.R;
import com.zhowin.youmamall.base.BaseBindActivity;
import com.zhowin.youmamall.databinding.ActivityWebViewBinding;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println("paymentUrl:" + paymentUrl);
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
        mWebView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("weixin://wap/pay?")) { //微信
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        Intent weChatIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        weChatIntent.addCategory("android.intent.category.BROWSABLE");
                        weChatIntent.setComponent(null);
                        startActivity(weChatIntent);
                        return true;
                    } else if (url.startsWith("https://wx.tenpay.com")) {
                        Map<String, String> extraHeaders = new HashMap<String, String>();
                        extraHeaders.put("Referer", BuildConfig.API_HOST);
                        view.loadUrl(url, extraHeaders);
                        return true;
                    }
                    if (url.startsWith("alipays:") || url.contains("platformapi/startapp") || url.contains("payments/alipay")) {//支付宝
                        try {
                            Intent aliIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            aliIntent.addCategory("android.intent.category.BROWSABLE");
                            aliIntent.setComponent(null);
                            startActivity(aliIntent);
                        } catch (Exception e) {
                        }
                        return true;
                    }
                } catch (Exception e) { // 防止crash
                    return true;// 没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                view.loadUrl(url);
                return true;

            }
        });
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
