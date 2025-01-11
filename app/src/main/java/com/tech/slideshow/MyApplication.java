package com.tech.slideshow;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;


import com.google.android.gms.ads.MobileAds;
import com.tech.slideshow.listener.OnProgressReceiver;
import com.tech.slideshow.model.MusicData;
import com.tech.slideshow.model.Photo;
import com.tech.slideshow.themes.THEMES;

import java.util.ArrayList;

public class MyApplication extends Application {
    public static boolean isServiceRunning = false;

    public int VIDEO_HEIGHT = 720;
    public int VIDEO_WIDTH = 1080;
    private static MyApplication mInstance;
    public static boolean isBreak = false;

    public boolean isPreview = false;
    int frame = -1;
    public boolean isEditModeEnable = false;
    public boolean isFromSdCardAudio = false;
    public int min_pos = Integer.MAX_VALUE;
    private MusicData musicData;
    private OnProgressReceiver onProgressReceiver;
    private float second = 2.0f;
    private ArrayList<Photo> selectedImages = new ArrayList<>();
    public ArrayList<String> videoImages = new ArrayList<>();
    public THEMES selectedTheme = THEMES.Shine;

    public static MyApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(this, initializationStatus -> {
                    });
                })
                .start();
    }
    public void initArray() {
        this.videoImages = new ArrayList<>();
    }

    public float getSecond() {
        return this.second;
    }

    public void setSecond(float second) {
        this.second = second;
    }

    public void setMusicData(MusicData musicData) {
        this.isFromSdCardAudio = false;
        this.musicData = musicData;
    }

    public void setVideoWidth(int width) {
        this.VIDEO_WIDTH = width;
    }

    public void setVideoHeight(int height) {
        this.VIDEO_HEIGHT = height;
    }

    public int getVideoHeight() {
        return this.VIDEO_HEIGHT;
    }

    public int getVideoWidth() {
        return this.VIDEO_WIDTH;
    }

    public MusicData getMusicData() {
        return this.musicData;
    }

    public void setSelectedImages(ArrayList<Photo> array) {
        this.selectedImages = array;
    }


    public ArrayList<Photo> getSelectedImages() {
        return this.selectedImages;
    }

    public void clearAllSelection() {
        this.videoImages.clear();
//        this.allAlbum = null;
        getSelectedImages().clear();
        System.gc();
//        getFolderList();
    }

    public void setCurrentTheme(String currentTheme) {
        SharedPreferenceUtils.getInstance(this).setString(GlobalConstant.CURRENT_THEME, currentTheme);
    }

    public String getCurrentTheme() {
        return SharedPreferenceUtils.getInstance(this).getString(GlobalConstant.CURRENT_THEME, THEMES.Shine.toString());
    }

    public void setOnProgressReceiver(OnProgressReceiver onProgressReceiver) {
        this.onProgressReceiver = onProgressReceiver;
    }

    public OnProgressReceiver getOnProgressReceiver() {
        return this.onProgressReceiver;
    }

    public void setFrame(int data) {
        this.frame = data;
    }

    public int getFrame() {
        return this.frame;
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Với Android O trở lên, sử dụng biến static để theo dõi trạng thái
            return MyApplication.isServiceRunning;
        } else {
            // Với các phiên bản cũ hơn, sử dụng ActivityManager
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager != null) {
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if (serviceClass.getName().equals(service.service.getClassName())) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
