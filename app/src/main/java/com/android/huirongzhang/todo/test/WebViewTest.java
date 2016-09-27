package com.android.huirongzhang.todo.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.webview.WebAppInterface;

/**
 * Created by HuirongZhang on 9/25/16.
 */

public class WebViewTest extends Activity {

    private static final String WEB_PAGE = "http://fa.163.com/activity/silver/newIndex/wap/sign.do?from=pz360et1";
    private static final String WEB_LIVE = "http://h.huajiao.com/l/index?liveid=sn._LC_RE_non_2497993014745280621098581_SX&author=24979930&userid=24979930&version=4.2.1.1019&time=1474528092&reference=wx&qd=wx&channel=meizu";

    private WebView mWebView;
    private View mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_web_view);

        mLoadingView = findViewById(R.id.loading_view);

        initWebView();

        initWebSettings();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.loadUrl(WEB_PAGE);
        /**
         * Binding JavaScript code to Android code
         */
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        /**
         * Force links and redirects to open in the WebView instead of in a browser
         *
         * Handling Page Navigation
         *
         * click urls后不再是启动第三方应用程序来处理;
         *
         * 通过该设置,所有的url加载都在WebView中;
         *
         */
        mWebView.setWebViewClient(new WebViewClient());

        /**
         * 自定义WebViewClient,控制会更加灵活。
         *
         */

        mWebView.setWebViewClient(new MyWebViewClient());

        /**
         *
         */
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    private void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();

        /**
         * WebViews don't allow JavaScript by default.
         *
         * To run a web application in the web view,
         *
         * you need to explicitly enable JavaScript
         *
         */
        webSettings.setJavaScriptEnabled(true);//enable js

        /**
         * ua 用处：
         *
         * 1.WebView中设置ua是为了验证是android app中的WebView发出的请求
         *
         * 2.普通的请求设置ua,是服务端为了验证安全性，同时出问题时便于查询哪个版本导致的。
         *
         * 3.custom user agent to verify that the client requesting your web page is actually your Android application.
         *
         * 4.then query the custom user agent in your web page to verify that the client requesting your web page is actually your Android application.
         *
         * 5.If the ua string is null or empty, the system default value will be used
         *
         */
        webSettings.setUserAgentString(null);

        //
        webSettings.setDomStorageEnabled(true);

        /**
         * ensure the page is given a larger viewport
         */
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //webSettings.setSaveFormData(true);


    }

    /**
     * Handling Page Navigation
     */
    private class MyWebViewClient extends WebViewClient {

        /**
         * This method is called whenever the WebView tries to navigate to a different URL.
         * <p>
         * If it returns false, the WebView opens the URL itself.
         *
         * @param view
         * @param url
         * @return
         */

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            /**
             * return false :let my WebView load the page.
             *
             * in order to not override the URL loading
             *
             * it allows the WebView to load the URL as usual
             *
             */
            if (url != null && url.startsWith("http:")) {
                return false;
            }

            /**
             * return true:启动其它程序处理urls
             */
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mLoadingView.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoadingView.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
