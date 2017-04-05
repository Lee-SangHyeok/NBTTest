package com.nbt.oopssang.nbttest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sang on 2017-04-04.
 * NewYorkTimes 기사를 WebView 연동하여 표시한다.
 * @TIMES_URL 보여줄 기사의 url
 * @settingWebView WebView 셋팅.
 */

public class WebViewActivity extends Activity {

    /**
     * 처음 시작 시, Intent로 전달 받을 URL의 EXTRA
     */
    public static String TIMES_URL_EXTRA = "TIMES_URL_EXTRA";
    /**
     * shouldOverrideUrlLoading() 호출 시, 예외처리 할 URL의 EXTRA
     */
    public static String CHECK_PLAY_URL_EXTRA = "TIMES_PLAY_URL";
    /**
     * NewYorkTimes 연동 시, shouldOverrideUrlLoading()에서 예외처리 할 url
     */
    public static String NEWYORKTIMES_CONTAINS_URL = "https://";

    private WebView mWebView;

    private String mPlayUrl;
    private String mCheckUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        settingWebView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WebViewActivity.this.finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void settingWebView(){
        mWebView = (WebView) findViewById(R.id.webview);

        mWebView.setWebViewClient(new NBTWebViewClient());
        mWebView.setWebChromeClient(new NBTWebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mPlayUrl = this.getIntent().getStringExtra(TIMES_URL_EXTRA);
        mCheckUrl = this.getIntent().getStringExtra(CHECK_PLAY_URL_EXTRA);
        Log.d("sang", " url " + mPlayUrl);
        mWebView.loadUrl(mPlayUrl);
    }

    /**
     * 크롬클라이언트. Js 대비용
     */
    private class NBTWebChromeClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
            new AlertDialog.Builder(WebViewActivity.this)
                    .setTitle("알림")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();

            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url,
                                   String message, final JsResult result) {

            new AlertDialog.Builder(WebViewActivity.this)
                    .setTitle("알림")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .create()
                    .show();

            return true;
        }
    }


    private class NBTWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /**
             * 기사 url외의 광고 url, text도 호출된다.
             * "https://" 문자열이 포함된 경우에만 load한다.
             */
            if(! url.isEmpty() && url.contains(mCheckUrl)){
                view.loadUrl(url);
            }
            Log.d("sang", "shouldOverrideUrlLoading url " + url);
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            CookieSyncManager.getInstance().sync();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // SSL 에러가 발생해도 계속 진행
        }

    }
}
