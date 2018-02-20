package com.example.abel.newsfeed.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.abel.newsfeed.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowserActivity extends AppCompatActivity {

    public static final String URL = "URL";

    @BindView(R.id.browserWebView)
    WebView browserWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(URL);
        browserWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.hold, R.anim.dissapear_to_bottom);
    }
}
