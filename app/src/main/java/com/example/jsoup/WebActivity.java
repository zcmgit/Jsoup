package com.example.jsoup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * @author zcm
 * @create 2018/10/31
 * @Describe
 */
public class WebActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        WebView webView = findViewById(R.id.web_view);
        Intent intent = getIntent();
        if(intent != null){
            String url = intent.getStringExtra("url");
            webView.loadUrl(url);
        }
    }
}
