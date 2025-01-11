package com.tech.slideshow.activities;


import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.google.android.material.tabs.TabLayout;
import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.FileUtils;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.MyApplication;
import com.tech.slideshow.R;
import com.tech.slideshow.Utils;
import com.tech.slideshow.adapter.ViewPagerAdapter2;
import com.tech.slideshow.ads.AdClosedListener;
import com.tech.slideshow.ads.FullAds;
import com.tech.slideshow.ads.NativeAdAdmob;
import com.tech.slideshow.fragment.createvideo.FrameFragment;
import com.tech.slideshow.fragment.createvideo.ImageFragment;
import com.tech.slideshow.fragment.createvideo.MusicFragment;
import com.tech.slideshow.fragment.createvideo.TimeFragment;
import com.tech.slideshow.fragment.createvideo.TransitionFragment;
import com.tech.slideshow.listener.DurationListener;
import com.tech.slideshow.listener.FrameListener;
import com.tech.slideshow.listener.MusicListener;
import com.tech.slideshow.listener.OnDialogListener;
import com.tech.slideshow.listener.OnImageListener;
import com.tech.slideshow.listener.TransitionListener;
import com.tech.slideshow.listener.UpdateImageListListener;
import com.tech.slideshow.model.MusicData;
import com.tech.slideshow.model.Photo;
import com.tech.slideshow.photopick.Matisse;
import com.tech.slideshow.photopick.MimeType;
import com.tech.slideshow.photopick.engine.GlideEngine;
import com.tech.slideshow.service.CreateImageService;
import com.tech.slideshow.service.CreateVideoService;
import com.tech.slideshow.themes.THEMES;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SlideShowActivity extends BaseActivity
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener
        , DurationListener, FrameListener, TransitionListener,
        OnImageListener, MusicListener {
    private MyApplication mApplication;
    private ArrayList<Photo> listImageSelected;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private FrameLayout loadingView;
    private ConstraintLayout imgPausePlayContainer;
    private AppCompatImageView imagePlayPause;
    private ImageView ivFrame;
    private ImageView imagePreview;
    private SeekBar seekBar;
    private MediaPlayer mPlayer;
    private float seconds = 2.0f;
    boolean isFromTouch = false;
    int sProgress = 0;
    private final LockRunnable lockRunnable = new LockRunnable();

    private final ArrayList<Photo> lastSelectedData = new ArrayList<>();
    private UpdateImageListListener mListener;

    public void updateImageList(UpdateImageListListener updateImageListListener) {
        this.mListener = updateImageListListener;

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        initApplication();
        initViews();
        initListener();
        setupTabLayout();
        startImageProcessingService();
        NativeAdAdmob.showNativeBanner7(this, null);

    }

    private void startImageProcessingService() {
        Intent intent = new Intent(this, CreateImageService.class);
        intent.putExtra(CreateImageService.EXTRA_SELECTED_THEME, mApplication.getCurrentTheme());
        startService(intent);
    }

    private void initApplication() {
        mApplication = MyApplication.getInstance();
        mApplication.videoImages.clear();
        mApplication.selectedTheme = THEMES.Shine;
        mApplication.setFrame(0);
        MyApplication.isBreak = false;
    }

    private void setupTabLayout() {
        TabLayout mTabLayout = findViewById(R.id.tabs);
        ViewPager viewPager2 = findViewById(R.id.view_pager);
        ViewPagerAdapter2 adapter = new ViewPagerAdapter2(getSupportFragmentManager());
        adapter.addFrag(new TransitionFragment(this, this), getString(R.string.str_transition));
        adapter.addFrag(new ImageFragment(this, this), getString(R.string.str_photo));
        adapter.addFrag(new MusicFragment(this, this), getString(R.string.str_music));
        adapter.addFrag(new FrameFragment(this, this), getString(R.string.str_frame));
        adapter.addFrag(new TimeFragment(this, this), getString(R.string.str_duration));
        viewPager2.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager2);
    }

    private void initListener() {
        findViewById(R.id.btBack).setOnClickListener(this);
        findViewById(R.id.btDone).setOnClickListener(this);
        findViewById(R.id.video_clicker).setOnClickListener(this);
    }

    private void initViews() {
        seekBar = findViewById(R.id.seekBarPreview1);
        loadingView = findViewById(R.id.loading_layout);
        imagePreview = findViewById(R.id.imagePreview);
        ivFrame = findViewById(R.id.ivFrame);
        imgPausePlayContainer = findViewById(R.id.playPauseContainer);
        imagePlayPause = findViewById(R.id.img_play_pause_preview);
        seconds = mApplication.getSecond();

        listImageSelected = mApplication.getSelectedImages();
        seekBar.setMax((listImageSelected.size() - 1) * 30);
        seekBar.setOnSeekBarChangeListener(this);
        if (!mApplication.getSelectedImages().isEmpty()) {
            Glide.with(this).load(mApplication.getSelectedImages().get(0).getFilePath()).into(imagePreview);
        }
        setTheme();
        lockRunnable.play();
    }

    public void reset() {
        MyApplication.isBreak = false;
        mApplication.videoImages.clear();
        handler.removeCallbacks(lockRunnable);
        lockRunnable.stop();
        Glide.get(this).clearMemory();
        new Thread(() -> Glide.get(SlideShowActivity.this).clearDiskCache()).start();


        FileUtils.deleteTempDir();
        loadingView.setVisibility(View.VISIBLE);
        setTheme();
    }

    public void setTheme() {
        if (mApplication.isFromSdCardAudio) {
            lockRunnable.play();
            return;
        }

        new Thread(() -> {
            THEMES selectedTheme = mApplication.selectedTheme;
            File tempAudioFile = new File(FileUtils.TEMP_DIRECTORY_AUDIO, "temp.mp3");

            try {
                if (!FileUtils.TEMP_DIRECTORY_AUDIO.exists()) {
                    FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs();
                }

                if (tempAudioFile.exists()) {
                    FileUtils.deleteFile(tempAudioFile);
                }

                try (InputStream in = getResources().openRawResource(selectedTheme.getThemeMusic());
                     FileOutputStream out = new FileOutputStream(tempAudioFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }

                MusicData musicData = new MusicData();
                musicData.track_data = tempAudioFile.getAbsolutePath();

                MediaPlayer player = new MediaPlayer();
                try {
                    player.setDataSource(tempAudioFile.getAbsolutePath());

                    // Sử dụng AudioAttributes thay vì setAudioStreamType
                    AudioAttributes audioAttributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA) // Dùng cho media như nhạc, video
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // Đặt là âm nhạc
                            .build();
                    player.setAudioAttributes(audioAttributes);

                    player.prepare();
                    musicData.track_duration = player.getDuration();
                } finally {
                    player.release(); // Giải phóng MediaPlayer
                }

                musicData.track_Title = "temp";
                mApplication.setMusicData(musicData);

            } catch (IOException e) {
                Log.e("Music data", e.toString());
            }

            runOnUiThread(() -> {
                reinitMusic();
                lockRunnable.play();
            });
        }).start();
    }

    @Override
    public void onBackPressed() {
        showDialogConfirm();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.sProgress = progress;
        if (isFromTouch) {
            runOnUiThread(() -> seekBar.setProgress(Math.min(progress, seekBar.getSecondaryProgress())));
            displayImage();
            seekMediaPlayer();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.isFromTouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.isFromTouch = false;
    }


    private void seekMediaPlayer() {
        if (mPlayer != null) {
            try {
                mPlayer.seekTo(((int) (((((float) sProgress) / 30.0f) * seconds) * 1000.0f)) % mPlayer.getDuration());
            } catch (IllegalStateException e) {
                Log.e("Music data", e.toString());

            }
        }
    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if (idView == R.id.btDone) {
            handler.removeCallbacks(lockRunnable);
            startService(new Intent(this, CreateVideoService.class));
            Intent intent = new Intent(SlideShowActivity.this, RenderVideoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (idView == R.id.btBack) {
            showDialogConfirm();
        } else if (idView == R.id.video_clicker) {
            if (lockRunnable.isPause()) {
                lockRunnable.play();
            } else {
                lockRunnable.pause();
            }
        }
    }

    @Override
    public void onDurationSelected(float duration) {
        if (duration != seconds) {
            seconds = duration;
            mApplication.setSecond(seconds);
            lockRunnable.play();
        }
    }

    public int getFrame() {
        return mApplication.getFrame();
    }

    public void setFrame(int data) {
        if (data == -1) {
            ivFrame.setImageDrawable(null);
        } else {
            ivFrame.setImageResource(data);
        }
        mApplication.setFrame(data);
    }

    @Override
    public void onFrameSelected(int frames) {
        if (frames != getFrame()) {
            setFrame(frames);
            if (frames != R.drawable.frame00) {
                FileUtils.deleteFile(FileUtils.frameFile);
                try {
                    Bitmap bm = Utils.scaleCenterCrop(BitmapFactory.decodeResource(getResources(), frames), MyApplication.getInstance().getVideoWidth(), MyApplication.getInstance().getVideoHeight());
                    FileOutputStream outStream = new FileOutputStream(FileUtils.frameFile);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    bm.recycle();
                    System.gc();
                } catch (Exception e) {
                    Log.e("Music data", e.toString());
                }
            }
        }
    }

    @Override
    public void onMusicSelected(int musicTye) {
        setMusic(musicTye);
    }

    @Override
    public void onAddMusic() {
        loadingView.setVisibility(View.GONE);
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, GlobalConstant.REQUEST_PICK_AUDIO);
            } else {
                Toast.makeText(this, R.string.toast_app_not_found, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("Thang", e + " ");
        }
    }

    @Override
    public void onAddImage() {
        loadingView.setVisibility(View.GONE);
        MyApplication.isBreak = true;
        mApplication.isEditModeEnable = true;
        lastSelectedData.clear();
        lastSelectedData.addAll(listImageSelected);
        mApplication.isPreview = true;
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .maxSelectable(50)
                .showSingleMediaType(true)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .showPreview(false) // Default is `true`
                .forResult(GlobalConstant.REQUEST_PICK_IMAGES);


//        Intent intent = new Intent(this, MatisseActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//        mApplication.isPreview = true;
//        startActivityForResult(intent, GlobalConstant.REQUEST_PICK_IMAGES);
    }

    @Override
    public void onReorder() {
        loadingView.setVisibility(View.GONE);
        mApplication.isEditModeEnable = true;
        mApplication.isPreview = true;
        startActivityForResult(new Intent(this, ReorderPhotoActivity.class)
                , GlobalConstant.REQUEST_REORDER_IMAGE);
    }

    @Override
    public void onTransitionSelected(THEMES transition) {
        deleteThemeDir(mApplication.selectedTheme.toString());
        mApplication.videoImages.clear();
        mApplication.selectedTheme = transition;
        mApplication.setCurrentTheme(mApplication.selectedTheme.toString());
        reset();
        Intent intent = new Intent(mApplication, CreateImageService.class);
        intent.putExtra(CreateImageService.EXTRA_SELECTED_THEME, mApplication.getCurrentTheme());
        startService(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mApplication.isEditModeEnable = false;
        if (resultCode == RESULT_OK) {
            Intent intent;
            if (requestCode == GlobalConstant.REQUEST_PICK_AUDIO) {

            } else if (requestCode == GlobalConstant.REQUEST_PICK_IMAGES) {

                Log.e("isNeedRestart", "isNeedRestart true");
                stopService(new Intent(getApplicationContext(), CreateImageService.class));
                this.lockRunnable.stop();
                this.seekBar.postDelayed(() -> {
                    MyApplication.isBreak = false;
                    mApplication.videoImages.clear();
                    mApplication.min_pos = Integer.MAX_VALUE;
                    Intent intent1 = new Intent(this.getApplicationContext(), CreateImageService.class);
                    intent1.putExtra(CreateImageService.EXTRA_SELECTED_THEME, mApplication.getCurrentTheme());
                    this.startService(intent1);
                }, 1000);
                listImageSelected = this.mApplication.getSelectedImages();
                seekBar.setMax((this.mApplication.getSelectedImages().size() - 1) * 30);
            } else if (requestCode == GlobalConstant.REQUEST_REORDER_IMAGE) {
                lockRunnable.stop();
                if (CreateImageService.isImageComplete || !MyApplication.isMyServiceRunning(mApplication, CreateImageService.class)) {
                    MyApplication.isBreak = false;
                    mApplication.videoImages.clear();
                    mApplication.min_pos = Integer.MAX_VALUE;
                    intent = new Intent(getApplicationContext(), CreateImageService.class);
                    intent.putExtra(CreateImageService.EXTRA_SELECTED_THEME, mApplication.getCurrentTheme());
                    startService(intent);
                }
                sProgress = 0;
                seekBar.setProgress(sProgress);
                listImageSelected = mApplication.getSelectedImages();
                seekBar.setMax((mApplication.getSelectedImages().size() - 1) * 30);
            }
        }
    }

    public void setMusic(final int i) {
        lockRunnable.pause();
        new Thread(() -> {
            try {
                FileUtils.TEMP_DIRECTORY_AUDIO.mkdirs();
                FileUtils.APP_DIRECTORY.mkdir();
                File file = new File(FileUtils.TEMP_DIRECTORY_AUDIO, "temp.mp3");
                if (file.exists()) {
                    FileUtils.deleteFile(file);
                }
                InputStream openRawResource = getResources().openRawResource(i);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openRawResource.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(file.getAbsolutePath());
                mediaPlayer.setAudioStreamType(3);
                mediaPlayer.prepare();
                final MusicData musicData = new MusicData();
                musicData.track_data = file.getAbsolutePath();
                mediaPlayer.setOnPreparedListener(mediaPlayer1 -> {
                    musicData.track_duration = mediaPlayer1.getDuration();
                    mediaPlayer1.stop();
                });
                musicData.track_Title = "temp";
                mApplication.setMusicData(musicData);
            } catch (Exception e) {
                Log.e("Music data", e.toString());
            }

            runOnUiThread(() -> {
                reinitMusic();
                sProgress = 0;
                lockRunnable.play();
            });
        }).start();
    }

    private void deleteThemeDir(final String dir) {
        new Thread() {
            public void run() {
                FileUtils.deleteThemeDir(dir);
            }
        }.start();
    }

    class LockRunnable implements Runnable {
        boolean isPause = false;

        @Override
        public void run() {
            displayImage();
            if (!isPause) {
                handler.postDelayed(lockRunnable, Math.round(50.0f * seconds));
            }
        }

        boolean isPause() {
            return isPause;
        }

        void play() {
            isPause = false;
            playMusic();
            handler.postDelayed(lockRunnable, Math.round(50.0f * seconds));
            imgPausePlayContainer.setVisibility(View.GONE);

        }

        void pause() {
            isPause = true;
            pauseMusic();
            imgPausePlayContainer.setVisibility(View.VISIBLE);
        }

        void stop() {
            pause();
            sProgress = 0;
            if (mPlayer != null) {
                mPlayer.stop();
            }
            reinitMusic();
            seekBar.setProgress(sProgress);
        }
    }

    private synchronized void displayImage() {
        try {
            if (sProgress >= seekBar.getMax()) {
                sProgress = 0;
                lockRunnable.stop();
            } else {
                if (sProgress > 0 && loadingView.getVisibility() == View.VISIBLE) {
                    loadingView.setVisibility(View.GONE);
                    if (!(mPlayer == null || mPlayer.isPlaying())) {
                        mPlayer.start();
                    }
                }
                seekBar.setSecondaryProgress(mApplication.videoImages.size());
                if (seekBar.getProgress() < seekBar.getSecondaryProgress()) {
                    sProgress %= mApplication.videoImages.size();
                    Glide.with(this).asBitmap().load(mApplication.videoImages.get(sProgress))
                            .signature(new MediaStoreSignature("image/*", System.currentTimeMillis(), 0))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into((Target<Bitmap>) new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    imagePreview.setImageBitmap(resource);
                                }
                            });
                    sProgress++;
                    if (!isFromTouch) {
                        seekBar.setProgress(sProgress);
                    }

                }
            }
        } catch (Exception e) {
            Glide.with(this);
        }
    }

    private void reinitMusic() {
        try {
            MusicData musicData = mApplication.getMusicData();
            if (musicData != null) {
                MediaPlayer create = MediaPlayer.create(this, Uri.parse(musicData.track_data));
                mPlayer = create;
                create.setLooping(true);
                try {
                    mPlayer.prepare();
                    return;
                } catch (IllegalStateException ex) {
                    Log.e("Mediaplayer", "Prepare Illegal");
                    return;
                } catch (IOException musicData1) {
                    Log.e("Mediaplayer", "Prepare IOExc");
                    return;
                }
            }
            Log.e("Music data", "null");
        } catch (Exception e) {
            Log.e("Music data", e.toString());
        }
    }

    private void playMusic() {
        if (loadingView.getVisibility() != View.VISIBLE && mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }

    private void pauseMusic() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        lockRunnable.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.OnUpdateImageListListener();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        Glide.get(this).clearMemory();
        handler.removeCallbacks(lockRunnable);
    }

    private void showDialogConfirm() {
        Utils.showConfirmDialog(this, GlobalConstant.DIALOG_CONFIRM_NO_SAVE, new OnDialogListener() {
            @Override
            public void onPositiveClick() {
                mApplication.videoImages.clear();
                MyApplication.isBreak = true;
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancel(1001);

                FullAds.showAds(SlideShowActivity.this, new AdClosedListener() {
                    @Override
                    public void AdClosed() {
                        Intent intent = new Intent(SlideShowActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        MyApplication.getInstance().isPreview = false;
                        finish();
                    }
                });
            }

            @Override
            public void onNegativeClick() {

            }
        });
    }
}