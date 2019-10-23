package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class RedditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddit);
        Intent intent = getIntent();
        WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        String url = intent.getStringExtra("link");
        webView.loadUrl(url);
        Log.d("address", url);
    }
}
