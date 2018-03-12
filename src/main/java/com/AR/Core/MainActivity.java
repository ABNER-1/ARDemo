package com.AR.Core;

/**
 * Created by abner on 2018/3/11.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    WebView mWebView;
    String url = "file:///android_asset/AR/home.html";
//    String url = "http://www.baidu.com";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        WebView mWebView = (WebView) this.findViewById(R.id.webid);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.addJavascriptInterface(this, "nativeMethod");
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);//关键点
        settings.setLayoutAlgorithm( WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        settings.setSupportZoom(true); // 支持缩放
        settings.setLoadWithOverviewMode(true);
        mWebView.loadUrl( url );
//        loadWeb();
    }

//    public void loadWeb(){
//        //此方法可以在webview中打开链接而不会跳转到外部浏览器
//        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.loadUrl(url);
//    }


    //重载onKeyDown的函数，使其在页面内回退,而不是直接退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()){
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @JavascriptInterface
    public void toActivity(String activityName) {
        //此处应该定义常量对应，同时提供给web页面编写者
        if(TextUtils.equals(activityName, "order")){
            startActivity(new Intent(this,Order.class));
//            startActivity(new Intent(this,UnityPlayerActivity.class));
        }
    }

}
