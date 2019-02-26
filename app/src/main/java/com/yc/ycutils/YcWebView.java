package com.yc.ycutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 */

public class YcWebView extends WebView {
    private Context mContext;
    private int DEFAULT_FONT_SIZE_DP = 20;//默认字体大小

    public YcWebView(Context context) {
        super(context);
        init(context);
    }

    public YcWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YcWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public YcWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        initWebSettings();
        initWebChromeClient();
        initWebViewClient();
    }
    private void initWebSettings() {
        WebSettings webSettings = this.getSettings();
        webSettings.setTextZoom(200);
//        WebSettings.TextSize size = new WebSettings.TextSize(100);
//        webSettings.setTextSize(TextSize);
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        // 支持插件,例如flash
//        webSettings.setPluginState(WebSettings.PluginState.ON);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        // 缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        // 其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }


    private void initWebViewClient() {
        this.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("KeithXiaoY", "开始加载");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("KeithXiaoY", "加载结束");
            }

            // 链接跳转都会走这个方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final float scale = mContext.getResources().getDisplayMetrics().density;
                int fontSizePx = (int) (DEFAULT_FONT_SIZE_DP * scale + 0.5f);//dp转px
                String defaultCss = "<style type=\"text/css\"> " +
                        "body {" +
                        "font-size:" + fontSizePx + "px;" +
                        "}" +
                        "</style>";
                view.loadData(defaultCss, "text/html", "utf-8");
                view.loadUrl(url);// 强制在当前 WebView 中加载 url
                return true;
            }
        });
    }

    private void initWebChromeClient() {
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("KeithXiaoY", "newProgress：" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("KeithXiaoY", "标题：" + title);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && this.canGoBack()) {  //表示按返回键
                this.goBack();   //后退
                //webview.goForward();//前进
                return true;    //已处理
            }
        }
        return false;
    }

    public void load(String html) {
        //content是后台返回的h5标签
        this.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }
}
