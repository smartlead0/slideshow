package com.tech.slideshow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                FullAds.showAds(SplashActivity.this, new AdClosedListener() {
//                    @Override
//                    public void AdClosed() {
//
//                    }
//                });
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}