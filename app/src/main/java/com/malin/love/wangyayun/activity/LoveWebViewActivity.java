package com.malin.love.wangyayun.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.malin.love.wangyayun.R;


/**
 * 类描述:主页面
 * 创建人:
 * 创建时间:16-1-7
 * 备注:
 */
public class LoveWebViewActivity extends Activity {

    private WebView mWebView;
    private WebSettings mWebSettings;
    public static final String URL = "file:///android_asset/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewlayout);
        initView();
        bindData();
    }


    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        // 设置支持JavaScript等
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setLightTouchEnabled(false);
        mWebSettings.setSupportZoom(false);
        mWebView.setHapticFeedbackEnabled(false);
    }

    private void bindData() {
        mWebView.loadUrl(URL);
//      就是在asset下放入一个index.html文件，这个文件包含js的代码，注意路径是“///”，这个android_asset不是文件夹的名字是系统自动生成的，其实就是asset文件夹的系统路径。
    }

}
