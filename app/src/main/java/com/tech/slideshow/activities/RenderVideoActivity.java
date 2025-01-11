package com.tech.slideshow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.MyApplication;
import com.tech.slideshow.R;
import com.tech.slideshow.Utils;
import com.tech.slideshow.ads.AdClosedListener;
import com.tech.slideshow.ads.FullAds;
import com.tech.slideshow.ads.NativeAdAdmob;
import com.tech.slideshow.listener.OnDialogListener;
import com.tech.slideshow.listener.OnProgressReceiver;
import com.tech.slideshow.service.CreateVideoService;

import java.util.Objects;

public class RenderVideoActivity extends BaseActivity implements OnProgressReceiver {
    private MyApplication myApplication;
    private String videoPath;
    private TextView tvVideoProgress;
    private TextView tvImageProgress;
    private ProgressBar progressImage;

    private ProgressBar progressVideo;
    private AppCompatImageView imgDoneVideo;

    private AppCompatImageView imgDoneImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_video);
        initToolBar();

        NativeAdAdmob.showNativeBig8(this, null);
        initViews();
        myApplication = MyApplication.getInstance();

    }

    private void initViews() {
        tvVideoProgress = findViewById(R.id.tvProgess_video);
        imgDoneVideo = findViewById(R.id.iv_video);
        progressVideo = findViewById(R.id.pb_video);

        tvImageProgress = findViewById(R.id.tvProgess_image);
        imgDoneImage = findViewById(R.id.iv_image);
        progressImage = findViewById(R.id.pb_image);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Utils.showConfirmDialog(this, GlobalConstant.DIALOG_CONFIRM_STOP_RENDER, new OnDialogListener() {
            @Override
            public void onPositiveClick() {
                Log.d("CreateVideoService", MyApplication.isMyServiceRunning(RenderVideoActivity.this, CreateVideoService.class) + "w");

                if (MyApplication.isMyServiceRunning(RenderVideoActivity.this, CreateVideoService.class)) {
                    Log.d("CreateVideoService", "Service is running, attempting to stop.");

                    stopService(new Intent(RenderVideoActivity.this, CreateVideoService.class));

                    Intent intent = new Intent(RenderVideoActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                    finish();
                } else {
                    Log.d("RenderVideoActivity", "Service is not running.");

                }
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        myApplication.setOnProgressReceiver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myApplication.setOnProgressReceiver(null);
//        if (MyApplication.isMyServiceRunning(this, CreateVideoService.class)) {
//            stopService(new Intent(RenderVideoActivity.this, CreateVideoService.class));
//
//            Intent intent = new Intent(RenderVideoActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//            startActivity(intent);
//            finish();
//        }

    }

    @Override
    public void onImageProgressFrameUpdate(float f) {

        runOnUiThread(() -> {
//                    int i = (int) ((f * 25.0f) / 100.0f);
            int i = (int) (f);  // Tính phần trăm trực tiếp từ f

            tvImageProgress.setText(i + "%");
            //    freshDownloadView.setProgress(((float) i) / 100.0f);
        });

    }

    @Override
    public void onImageProgressFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressImage.setVisibility(View.GONE);
                imgDoneImage.setVisibility(View.VISIBLE);
                tvImageProgress.setText(R.string.str_done);
            }
        });
    }

    @Override
    public void onProgressFinish(String str) {
        progressVideo.setVisibility(View.GONE);
        imgDoneVideo.setVisibility(View.VISIBLE);
        tvVideoProgress.setText(R.string.str_done);
        this.videoPath = str;
        FullAds.showAds(RenderVideoActivity.this, new AdClosedListener() {
            @Override
            public void AdClosed() {
                loadVideoPlay();

            }
        });
    }

    @Override
    public void onVideoProgressFrameUpdate(float f) {

        runOnUiThread(() -> {
            int i = (int) (((f * 75.0f) / 100.0f) + 25.0f);
            tvVideoProgress.setText(i + "%");
            //    freshDownloadView.setProgress(((float) i) / 100.0f);
        });

    }

    private void loadVideoPlay() {
        Intent intent = new Intent(this, ShareVideoActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(GlobalConstant.VIDEO_PATH, videoPath);
        startActivity(intent);
        super.onBackPressed();
    }
}