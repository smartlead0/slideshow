package com.tech.slideshow.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.airbnb.lottie.LottieAnimationView;
import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.Utils;
import com.tech.slideshow.ads.NativeAdAdmob;

import java.util.Objects;

public class ShareVideoActivity extends BaseActivity implements View.OnClickListener {
    String videoPath;
    private PlayerView playerView;
    private LottieAnimationView loadingView;
    private ExoPlayer exoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_video);
        initViews();
        initListener();
        initToolBar();

        NativeAdAdmob.showNativeBig8(this, null);
        Intent intent = getIntent();
        videoPath = intent.getStringExtra(GlobalConstant.VIDEO_PATH);
        if (videoPath == null || videoPath.isEmpty()) {
            Toast.makeText(this, R.string.toast_video_path_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        initializePlayer(videoPath);
        if (!SharedPreferenceUtils.getInstance(this).getBoolean(GlobalConstant.IS_RATED_APP, false)) {
            Utils.showRateDialog(this);
        }
    }

    private void initializePlayer(String videoPath) {
        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);

        // Tạo MediaItem từ đường dẫn video
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoPath));
        exoPlayer.setMediaItem(mediaItem);

        // Chuẩn bị và phát video
        exoPlayer.prepare();
        exoPlayer.play();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(GlobalConstant.EXTRA_FROM_VIDEO, true);
        startActivity(intent);
//        startActivity(new Intent(this, GalleryActivity.class));
    }

    private void initListener() {
        findViewById(R.id.btShareOther).setOnClickListener(this);
        findViewById(R.id.btnYoutube).setOnClickListener(this);
        findViewById(R.id.btnMessenger).setOnClickListener(this);
        findViewById(R.id.btnFacebook).setOnClickListener(this);
        findViewById(R.id.btnInstagram).setOnClickListener(this);
        findViewById(R.id.btnGmail).setOnClickListener(this);
        findViewById(R.id.btnWhatsApp).setOnClickListener(this);

        findViewById(R.id.btnHome).setOnClickListener(this);
        findViewById(R.id.img_full).setOnClickListener(this);
    }

    private void initViews() {
        playerView = findViewById(R.id.pv_preview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer != null) {
            exoPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if (idView == R.id.btnHome) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (idView == R.id.img_full) {
            Intent intentView = new Intent(this, ViewFullActivity.class);
            intentView.putExtra(GlobalConstant.VIDEO_PATH, videoPath);
            startActivity(intentView);
        } else if (idView == R.id.btShareOther) {
            Utils.shareVideo(this, videoPath);
        } else if (idView == R.id.btnYoutube) {
            Utils.shareVideoFinal(this, videoPath, "Youtube", GlobalConstant.PACKAGE_NAME_YOUTUBE);

        } else if (idView == R.id.btnMessenger) {
            Utils.shareVideoFinal(this, videoPath, "Messenger", GlobalConstant.PACKAGE_NAME_MESSENGER);

        } else if (idView == R.id.btnFacebook) {
            Utils.shareVideoFinal(this, videoPath, "Facebook", GlobalConstant.PACKAGE_NAME_FACEBOOK);

        } else if (idView == R.id.btnInstagram) {
            Utils.shareVideoFinal(this, videoPath, "Instagram", GlobalConstant.PACKAGE_NAME_INSTAGRAM);

        } else if (idView == R.id.btnGmail) {
            Utils.shareVideoFinal(this, videoPath, "Gmail", GlobalConstant.PACKAGE_NAME_GMAIL);

        } else if (idView == R.id.btnWhatsApp) {
            Utils.shareVideoFinal(this, videoPath, "Whatsapp", GlobalConstant.PACKAGE_NAME_WHATSAPP);
        }
    }
}