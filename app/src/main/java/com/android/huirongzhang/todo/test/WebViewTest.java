package com.android.huirongzhang.todo.test;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
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

    private static final String WEB_PAGE = "https://developer.android.com/guide/webapps/webview.html";
    private static final String WEB_LIVE = "http://h.huajiao.com/l/index?liveid=sn._LC_RE_non_2497993014745280621098581_SX&author=24979930&userid=24979930&version=4.2.1.1019&time=1474528092&reference=wx&qd=wx&channel=meizu";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_web_view);

        initWebView();

        initWebSettings();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.loadUrl(WEB_LIVE);
        /**
         * Binding JavaScript code to Android code
         */
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        /**
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
        webSettings.setJavaScriptEnabled(true);//enable js
        //webSettings.setUserAgentString("ua");//custom user agent to verify that the client requesting your web page is actually your Android application.

        //
        webSettings.setDomStorageEnabled(true);

        /**
         * ensure the page is given a larger viewport
         */
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //webSettings.setUserAgentString("");//

        //webSettings.setSaveFormData(true);


    }

    /**
     * Handling Page Navigation
     */
    private class MyWebViewClient extends WebViewClient {

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
            if (url != null && url.startsWith("http://h.huajiao")) {
                return false;
            }

            /**
             * return true:启动其它程序处理urls
             */
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    /**
     * Navigating web page history
     *
     * @param keyCode
     * @param event
     * @return
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        /**
         * 1.判断是否是Back Button
         *
         * 2.canGoBack():判断是否有history
         *
         */
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        /**
         * 如果上面条件都不满足,执行系统默认的。
         */
        return super.onKeyDown(keyCode, event);
    }
}
