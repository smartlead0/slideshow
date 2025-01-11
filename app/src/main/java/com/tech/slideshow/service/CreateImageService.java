package com.tech.slideshow.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.tech.slideshow.FileUtils;
import com.tech.slideshow.MyApplication;
import com.tech.slideshow.Utils;
import com.tech.slideshow.listener.OnProgressReceiver;
import com.tech.slideshow.mask.FinalMaskBitmap;
import com.tech.slideshow.model.Photo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class CreateImageService extends IntentService {
    public static final String EXTRA_SELECTED_THEME = "selected_theme";
    public static boolean isImageComplete = false;
    MyApplication application;
    private int videoWidth;
    private int videoHeight;
    private ArrayList<Photo> arrayList;
    private String selectedTheme;
    private int totalImages;

    public CreateImageService() {
        this(CreateImageService.class.getName());
    }


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CreateImageService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.application = MyApplication.getInstance();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        this.selectedTheme = intent.getStringExtra(EXTRA_SELECTED_THEME);
        this.arrayList = this.application.getSelectedImages();
        this.application.initArray();
        isImageComplete = false;
        videoWidth = application.getVideoWidth();
        videoHeight = application.getVideoHeight();
        createImages();

    }

    private void createImages() {
        Bitmap newSecondBmp2 = null;
        this.totalImages = this.arrayList.size();

        for (int i = 0; i < this.arrayList.size() - 1 && isSameTheme() && !MyApplication.isBreak; i++) {
            Bitmap newFirstBmp;
            File imgDir = FileUtils.getImageDirectory(this.application.selectedTheme.toString(), i);

            try {
                if (i == 0) {
                    newFirstBmp = prepareFirstBitmap(this.arrayList.get(i).getFilePath());
                } else {
                    if (newSecondBmp2 == null || newSecondBmp2.isRecycled()) {
                        newSecondBmp2 = prepareBitmap(this.arrayList.get(i).getFilePath());
                    }
                    newFirstBmp = newSecondBmp2;
                }

                newSecondBmp2 = prepareBitmap(this.arrayList.get(i + 1).getFilePath());

                FinalMaskBitmap.reintRect();
                FinalMaskBitmap.EFFECT effect = this.application.selectedTheme.getTheme().get(i % this.application.selectedTheme.getTheme().size());
                effect.initBitmaps(newFirstBmp, newSecondBmp2);

                processEffectFrames(effect, newFirstBmp, newSecondBmp2, imgDir);

            } catch (Exception e) {
                Log.e("CreateImageService", "Error processing image at index " + i, e);
            }
        }

        Glide.get(this).clearDiskCache();

        new Handler(Looper.getMainLooper()).post(() -> {
            OnProgressReceiver receiver = MyApplication.getInstance().getOnProgressReceiver();
            if (receiver != null) {
                receiver.onImageProgressFinish();
            }
        });
        isImageComplete = true;
        stopSelf();
    }

    private void processEffectFrames(FinalMaskBitmap.EFFECT effect, Bitmap newFirstBmp, Bitmap newSecondBmp2, File imgDir) throws IOException {
        for (int j = 0; j < FinalMaskBitmap.ANIMATED_FRAME && isSameTheme() && !MyApplication.isBreak; j++) {
            Bitmap bitmap = executeEffect(effect, newFirstBmp, newSecondBmp2, j);
            saveBitmap(bitmap, imgDir, j);
            bitmap.recycle();

            if (application.isEditModeEnable && application.min_pos != Integer.MAX_VALUE) {
                return;
            }

            calculateProgress();
        }
    }

    private void saveBitmap(Bitmap bitmap, File imgDir, int frameIndex) throws IOException {
        File file = new File(imgDir, String.format(Locale.getDefault(), "img%02d.jpg", frameIndex));
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("Failed to create directory: " + file.getParent());
        }

        try (OutputStream fileOutputStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        }
        application.videoImages.add(file.getAbsolutePath());

        if (frameIndex == FinalMaskBitmap.ANIMATED_FRAME - 1) {
            for (int m = 0; m < 8 && isSameTheme() && !MyApplication.isBreak; m++) {
                application.videoImages.add(file.getAbsolutePath());
            }
        }
    }

    private Bitmap prepareBitmap(String filePath) throws IOException {
        Bitmap bitmap = Utils.checkBitmap(filePath);
        if (bitmap == null) {
            throw new IOException("Failed to load bitmap: " + filePath);
        }
        Bitmap temp = Utils.scaleCenterCrop(bitmap, videoWidth, videoHeight);
        Bitmap result = Utils.convertSameSize(bitmap, temp, videoWidth, videoHeight, 1.0f, 0.0f);
        temp.recycle();
        return result;
    }

    private Bitmap prepareFirstBitmap(String filePath) throws IOException {
        Bitmap firstBitmap = Utils.checkBitmap(filePath);
        if (firstBitmap == null) {
            throw new IOException("Failed to load bitmap: " + filePath);
        }
        Bitmap temp = Utils.scaleCenterCrop(firstBitmap, videoWidth, videoHeight);
        Bitmap result = Utils.convertSameSize(firstBitmap, temp, videoWidth, videoHeight, 1.0f, 0.0f);
        temp.recycle();
        firstBitmap.recycle();
        System.gc();
        return result;
    }


    private Bitmap executeEffect(FinalMaskBitmap.EFFECT effect, Bitmap newFirstBmp, Bitmap newSecondBmp2, int j) {
        String effectname = effect.name();
        Bitmap bitmap;

        if (isRollEffect(effectname)) {
            bitmap = effect.getMask(newFirstBmp, newSecondBmp2, j);
        } else {
            bitmap = applyMaskEffect(newFirstBmp, newSecondBmp2, effect, j);
        }

        return bitmap;
    }

    private boolean isRollEffect(String effectname) {
        return effectname.equals("Roll2D_TB") || effectname.equals("Roll2D_BT")
                || effectname.equals("Roll2D_LR") || effectname.equals("Roll2D_RL")
                || effectname.equals("Whole3D_TB") || effectname.equals("Whole3D_BT")
                || effectname.equals("Whole3D_LR") || effectname.equals("Whole3D_RL");
    }

    private Bitmap applyMaskEffect(Bitmap newFirstBmp, Bitmap newSecondBmp2, FinalMaskBitmap.EFFECT effect, int j) {
        Bitmap bmp = Bitmap.createBitmap(videoWidth, videoHeight, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(newFirstBmp, 0.0f, 0.0f, null);
        canvas.drawBitmap(effect.getMask(videoWidth, videoHeight, j), 0.0f, 0.0f, paint);

        Bitmap bitmap3 = Bitmap.createBitmap(videoWidth, videoHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap3);
        canvas2.drawBitmap(newSecondBmp2, 0.0f, 0.0f, null);
        canvas2.drawBitmap(bmp, 0.0f, 0.0f, new Paint());

        return bitmap3;
    }

    private void calculateProgress() {
        final int totalFrames = (int) ((this.totalImages - 1) * FinalMaskBitmap.ANIMATED_FRAME);
        if (totalFrames > 0) {
            // Đảm bảo rằng videoImages.size() không vượt quá totalFrames
            final int size = (int) (((float) this.application.videoImages.size() * 100.0f) / totalFrames);

            Log.d("ProgressDebug", "Progress: " + size + "%");

            // Đảm bảo rằng phần trăm tiến trình không vượt quá 100%
            int progress = Math.min(size, 100); // Giới hạn giá trị tiến trình tối đa là 100%

            new Handler(Looper.getMainLooper()).post(() -> {
                OnProgressReceiver onProgressReceiver = CreateImageService.this.application.getOnProgressReceiver();
                if (onProgressReceiver != null) {
                    onProgressReceiver.onImageProgressFrameUpdate((float) progress);
                }
            });
        }
    }

    private boolean isSameTheme() {
        return this.selectedTheme.equals(this.application.getCurrentTheme());
    }
}
