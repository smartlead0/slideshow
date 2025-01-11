package com.tech.slideshow.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.R;
import com.tech.slideshow.ads.NativeAdAdmob;

import java.util.Objects;

import im.delight.android.webview.AdvancedWebView;

public class BrowserActivity extends BaseActivity implements AdvancedWebView.Listener {
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_URL = "extra_url";
    String url;
    String mCurrentTitle;
    AdvancedWebView webView;
    TextView tvToolbar;
    LottieAnimationView loadingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        setUpToolbar();
        getData();
        initViews();
        NativeAdAdmob.showNativeBanner7(this, null);
    }

    private void initViews() {
        tvToolbar = findViewById(R.id.tv_app_title);

        loadingContainer = findViewById(R.id.loadingView);
        webView = findViewById(R.id.browser_webview);
        tvToolbar.setText(mCurrentTitle);

        webView.setListener(this, this);
        webView.setMixedContentAllowed(false);
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            this.url = intent.getStringExtra(EXTRA_URL);
            this.mCurrentTitle = intent.getStringExtra(EXTRA_TITLE);
        }
    }

    public static void startBrowserWebActivity(Context context, String str, String str2) {
        Intent intent2 = new Intent(context, BrowserActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra(EXTRA_TITLE, str);
        intent2.putExtra(EXTRA_URL, str2);
        context.startActivity(intent2);
    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {
        loadingContainer.setVisibility(View.GONE);

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

}