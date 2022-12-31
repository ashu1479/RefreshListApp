package com.ashwini.refreshlistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class UrlWebviewActivity extends AppCompatActivity {
    WebView webView;
    String img_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_webview);
        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        img_url = getIntent().getStringExtra("img_url");
        webView.loadUrl(img_url);
    }
}