package com.tech.slideshow.service;

import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.Statistics;
import com.tech.slideshow.FileUtils;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.MyApplication;
import com.tech.slideshow.R;
import com.tech.slideshow.activities.ViewFullActivity;
import com.tech.slideshow.listener.OnProgressReceiver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateVideoService extends IntentService {
    private int currentProgress = 0; // Tiến trình hiện tại
    private int targetProgress = 0;  // Tiến trình mục tiêu từ callback
    private Handler progressHandler;
    public static final int NOTIFICATION_ID = 1001;
    MyApplication myApplication;
    private File audio_file;
    File audio_ip;
    private final NotificationCompat.Builder builder;
    private NotificationManager notify_manager;
    String time;
    private float total_second;
    String CHANNEL_ID = "createVideo";
    private Statistics mStatistics;
    ExecutorService executor;

    public CreateVideoService() {
        super(CreateVideoService.class.getName());
        this.time = "\\btime=\\b\\d\\d:\\d\\d:\\d\\d.\\d\\d";
        this.builder = new NotificationCompat.Builder(this, CHANNEL_ID);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        myApplication = MyApplication.getInstance();
        notify_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Create notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            notify_manager.createNotificationChannel(mChannel);
        }
        progressHandler = new Handler(Looper.getMainLooper());

        builder.setContentTitle(getString(R.string.str_creating_video))
                .setContentText(getString(R.string.str_making_in_progress))
                .setSmallIcon(getNotificationIcon())
                .setOnlyAlertOnce(true);

        setActive();

        // Run video creation in a background thread using Executor
        executor = Executors.newSingleThreadExecutor();
        executor.execute(this::createVideo);
    }

    private void setActive() {
        Config.enableStatisticsCallback(statistics -> {
            mStatistics = statistics;

            if (mStatistics == null) {
                return;
            }

            int time = mStatistics.getTime();
            OnProgressReceiver onProgressReceiver = myApplication.getOnProgressReceiver();

            if (onProgressReceiver != null && time > 0) {
                // Tính toán phần trăm tiến trình
                targetProgress = new BigDecimal(time)
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(total_second * 1000.0f), 0, RoundingMode.HALF_UP)
                        .intValue();

                // Đảm bảo không vượt quá 100%
                if (targetProgress > 100) {
                    targetProgress = 100;
                }

                // Nếu chưa bắt đầu cập nhật mượt mà, bắt đầu ngay
                if (!progressHandler.hasMessages(0)) {
                    startSmoothProgressUpdate();
                }
            }
        });
    }

    private void startSmoothProgressUpdate() {
        progressHandler.post(new Runnable() {
            @Override
            public void run() {
                if (currentProgress < targetProgress) {
                    // Tăng tiến trình từ từ
                    currentProgress++;
                    OnProgressReceiver receiver = myApplication.getOnProgressReceiver();
                    if (receiver != null) {
                        receiver.onVideoProgressFrameUpdate(currentProgress);
                    }

                    // Lặp lại cập nhật sau 50ms
                    progressHandler.postDelayed(this, 50);
                } else {
                    // Dừng nếu đã đạt tiến trình mục tiêu
                    progressHandler.removeCallbacks(this);
                }
            }
        });
    }

    private int getNotificationIcon() {
        return R.mipmap.ic_launcher;
    }

    private void createVideo() {
        String[] inputCode;
        long startTime = System.currentTimeMillis();
        total_second = (myApplication.getSecond() * myApplication.getSelectedImages().size()) - 1.0f;
        joinAudio();

        while (!CreateImageService.isImageComplete) {
            // Wait for images to be ready
        }

        File logFile = new File(FileUtils.TEMP_DIRECTORY, "video.txt");
        logFile.delete();

        // Log video image creation
        for (int i = 0; i < myApplication.videoImages.size(); i++) {
            appendVideoLog(String.format("file '%s'", myApplication.videoImages.get(i)));
        }

        String filename = getVideoName();
        String videoPath = new File(FileUtils.APP_DIRECTORY, filename).getAbsolutePath();

        // Create video input code based on music and frame settings
        inputCode = buildVideoInputCode(logFile, videoPath);


//        int rc = FFmpeg.execute(inputCode);

        FFmpeg.executeAsync(inputCode, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                handleVideoCreationResult(returnCode, startTime, videoPath);

            }
        });
//        setActive();

    }

    private String[] buildVideoInputCode(File logFile, String videoPath) {
        String[] inputCode;
        if (myApplication.getMusicData() == null) {
            inputCode = new String[]{
                    "-r", String.valueOf(30.0f / myApplication.getSecond()),
                    "-f", "concat", "-safe", "0", "-i", logFile.getAbsolutePath(),
                    "-r", "30", "-c:v", "libx264", "-preset", "ultrafast", "-pix_fmt", "yuv420p", videoPath
            };
        } else if (myApplication.getFrame() != 0) {
            // Add frame to video
            inputCode = new String[]{
                    "-r", String.valueOf(30.0f / myApplication.getSecond()),
                    "-f", "concat", "-safe", "0", "-i", logFile.getAbsolutePath(),
                    "-i", FileUtils.frameFile.getAbsolutePath(), "-stream_loop", "-1",
                    "-i", audio_file.getAbsolutePath(), "-filter_complex", "overlay=0:0", "-strict", "experimental",
                    "-r", String.valueOf(30.0f / myApplication.getSecond()), "-t", String.valueOf(total_second),
                    "-c:v", "libx264", "-preset", "ultrafast", "-pix_fmt", "yuv420p", "-ac", ExifInterface.GPS_MEASUREMENT_2D, videoPath
            };
        } else {
            // Use audio without frame
            inputCode = new String[]{
                    "-r", String.valueOf(30.0f / myApplication.getSecond()),
                    "-f", "concat", "-safe", "0", "-i", logFile.getAbsolutePath(),
                    "-stream_loop", "-1", "-i", audio_file.getAbsolutePath(), "-strict", "experimental",
                    "-r", "30", "-t", String.valueOf(total_second), "-c:v", "libx264", "-preset", "ultrafast",
                    "-pix_fmt", "yuv420p", "-ac", ExifInterface.GPS_MEASUREMENT_2D, videoPath
            };
        }
        return inputCode;
    }

    private void handleVideoCreationResult(int rc, long startTime, String videoPath) {
        if (rc == RETURN_CODE_SUCCESS) {
            Log.i(Config.TAG, "Command execution completed successfully.");
        } else if (rc == RETURN_CODE_CANCEL) {
            Log.i(Config.TAG, "Command execution cancelled by user.");
        } else {
            Log.i(Config.TAG, String.format("Command execution failed with rc=%d.", rc));
            Config.printLastCommandOutput(Log.INFO);
        }

        // Move video to final location and update UI
        String newPathVideo = FileUtils.copyFileToSlideShowMakerFolder(new File(videoPath), videoPath, myApplication);
        if (newPathVideo != null) {
            builder.setContentText("Video created in " + FileUtils.getDuration(System.currentTimeMillis() - startTime))
                    .setProgress(0, 0, false);
            notify_manager.notify(NOTIFICATION_ID, builder.build());

            myApplication.clearAllSelection();
            buildNotification(newPathVideo);

            // Notify UI thread with video creation progress
            new Handler(Looper.getMainLooper()).post(() -> {
                OnProgressReceiver receiver = myApplication.getOnProgressReceiver();
                if (receiver != null) {
                    receiver.onVideoProgressFrameUpdate(100);
                    receiver.onProgressFinish(newPathVideo);
                }
            });

            FileUtils.deleteTempDir();
            stopSelf();
        }
//        String newPathVideo = FileUtils.getDestinationFilePath(videoPath, myApplication);


    }

    private void joinAudio() {
        try {
            audio_ip = new File(FileUtils.TEMP_DIRECTORY, "audio.txt");
            audio_file = new File(FileUtils.APP_DIRECTORY, "audio.mp3");
            audio_file.delete();
            audio_ip.delete();
            appendAudioLog(String.format("file '%s'", myApplication.getMusicData().track_data));
            FFmpeg.execute(new String[]{
                    "-f", "concat", "-safe", "0", "-i", audio_ip.getAbsolutePath(),
                    "-c", "copy", "-preset", "ultrafast", "-ac", ExifInterface.GPS_MEASUREMENT_2D, audio_file.getAbsolutePath()
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendAudioLog(String text) {
        File logFile = new File(FileUtils.TEMP_DIRECTORY, "audio.txt");
        try {
            if (!logFile.exists()) logFile.createNewFile();
            try (BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true))) {
                buf.append(text);
                buf.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.isServiceRunning = true;

    }

    private void buildNotification(String videoPath) {
        Intent notificationIntent = new Intent(this, ViewFullActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra(GlobalConstant.VIDEO_PATH, videoPath);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Resources res = getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Video Created")
                .setContentText("Video is ready. Tap to view.");

        notify_manager.notify(NOTIFICATION_ID, builder.build());
    }

    private void appendVideoLog(String text) {
        File logFile = new File(FileUtils.TEMP_DIRECTORY, "video.txt");
        try {
            if (!logFile.exists()) logFile.createNewFile();
            try (BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true))) {
                buf.append(text);
                buf.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {


        super.onDestroy();


    }

    private String getVideoName() {
        return "video_" + System.currentTimeMillis() + ".mp4";
    }
}