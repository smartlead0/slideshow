package com.tech.slideshow.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.Utils;

import java.io.File;

public class ViewFullActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout loadingView;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    String videoPath;
    private TextView tvNameVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full);
        initViews();
        initListener();

        Intent intent = getIntent();
        videoPath = intent.getStringExtra(GlobalConstant.VIDEO_PATH);

        File fileVideo = new File(videoPath);


        if (videoPath == null || videoPath.isEmpty()) {
            Toast.makeText(this, R.string.toast_video_path_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        String videoName = fileVideo.getName();

        initializePlayer(videoPath);
        tvNameVideo.setText(videoName);
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

    private void initListener() {
        findViewById(R.id.img_close_view).setOnClickListener(this);
        findViewById(R.id.img_share_view).setOnClickListener(this);

    }

    private void initViews() {
        loadingView = findViewById(R.id.rl_prepare_load_viewVideo);
        playerView = findViewById(R.id.pv_preview);
        tvNameVideo = findViewById(R.id.txt_name_video);
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
    public void onClick(View view) {
        int idView = view.getId();
        if (idView == R.id.img_close_view) {
            onBackPressed();
        } else if (idView == R.id.img_share_view) {
            Utils.shareVideo(this, videoPath);
        }
    }
}