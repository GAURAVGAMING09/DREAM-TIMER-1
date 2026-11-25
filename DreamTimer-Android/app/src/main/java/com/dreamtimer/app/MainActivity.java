package com.dreamtimer.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private static final String START_URL = "file:///android_asset/index.html";
    private WebView web;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        // Timer chalte waqt screen ON rahe
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.parseColor("#0a0820"));
        web = new WebView(this);
        web.setBackgroundColor(Color.parseColor("#0a0820"));
        root.addView(web, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);

        // Android 15/16 edge-to-edge: status bar / nav bar / notch ke neeche content na dabe
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            int t, bt, l, r;
            if (Build.VERSION.SDK_INT >= 30) {
                android.graphics.Insets i = insets.getInsets(
                        WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()
                                | WindowInsets.Type.ime());
                t = i.top; bt = i.bottom; l = i.left; r = i.right;
            } else {
                t = insets.getSystemWindowInsetTop(); bt = insets.getSystemWindowInsetBottom();
                l = insets.getSystemWindowInsetLeft(); r = insets.getSystemWindowInsetRight();
            }
            v.setPadding(l, t, r, bt);
            return insets;
        });

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);                 // settings / history save hon
        s.setMediaPlaybackRequiresUserGesture(false); // tick sound / voice
        s.setAllowFileAccess(true);                   // sirf app ke apne assets ke liye
        s.setAllowContentAccess(false);
        s.setAllowFileAccessFromFileURLs(false);
        s.setAllowUniversalAccessFromFileURLs(false);
        s.setGeolocationEnabled(false);
        s.setJavaScriptCanOpenWindowsAutomatically(false); // koi pop-up window nahi
        s.setSupportMultipleWindows(false);
        s.setSafeBrowsingEnabled(true);
        s.setTextZoom(100);                           // system font size se layout na bigde
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        web.setOverScrollMode(View.OVER_SCROLL_NEVER);
        web.setWebChromeClient(new WebChromeClient()); // JS alert / window.open default = blocked
        web.setWebViewClient(new WebViewClient() {
            private boolean allowed(String url) {
                return url != null && (url.startsWith("file:///android_asset/") || url.startsWith("data:") || url.startsWith("blob:"));
            }
            @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                return !allowed(r.getUrl().toString()); // bahar ka koi link nahi khulega
            }
            @Override public WebResourceResponse shouldInterceptRequest(WebView v, WebResourceRequest r) {
                if (allowed(r.getUrl().toString())) return null;
                return new WebResourceResponse("text/plain", "utf-8", null);
            }
        });

        if (b != null) web.restoreState(b); else web.loadUrl(START_URL);
    }

    @Override protected void onSaveInstanceState(Bundle o) { super.onSaveInstanceState(o); web.saveState(o); }
    @Override protected void onResume() { super.onResume(); web.onResume(); }
    @Override protected void onPause() { web.onPause(); super.onPause(); }
    @Override public void onBackPressed() { finish(); }
    @Override protected void onDestroy() { web.destroy(); super.onDestroy(); }
}
