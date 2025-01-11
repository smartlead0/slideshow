package com.tech.slideshow;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowMetrics;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.ads.AdSize;
import com.tech.slideshow.activities.MainActivity;
import com.tech.slideshow.dialog.ConfirmDialog;
import com.tech.slideshow.dialog.ExitBottomDialog;
import com.tech.slideshow.dialog.PermissionDialog;
import com.tech.slideshow.dialog.RateDialog;
import com.tech.slideshow.dialog.RenameDialog;
import com.tech.slideshow.dialog.ResolutionDialog;
import com.tech.slideshow.listener.OnDialogListener;
import com.tech.slideshow.listener.OnRatioListener;
import com.tech.slideshow.listener.RenameListener;
import com.tech.slideshow.model.MyVideo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Utils {
    public static void rateApp(Context mContext) {
        try {
            Uri marketUri = Uri.parse("market://details?id=" + mContext.getPackageName());
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, marketUri));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static CharSequence setToolBarPdfCreated(Context mContext) {
        SpannableString first = new SpannableString("Magic");
        SpannableString second = new SpannableString("Show");
        first.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorAccent)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        second.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimaryText)), 0, second.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return TextUtils.concat(first, second);

    }

    public static CharSequence setAppName(Context mContext) {
        SpannableString zero = new SpannableString(mContext.getString(R.string.str_welcome_to));

        SpannableString first = new SpannableString("Magic");
        SpannableString second = new SpannableString("Show");
        first.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorAccent)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        second.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimaryText)), 0, second.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return TextUtils.concat(zero, first, second);
    }

    public static void shareVideoFinal(Context context, String videoPath, String appName, String packageName) {
        // Kiểm tra xem ứng dụng đã được cài đặt chưa
        if (isPackageInstalled(context, packageName)) {
            // Quét file video để cập nhật vào hệ thống
            MediaScannerConnection.scanFile(context, new String[]{videoPath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    // Tạo Intent để chia sẻ video tới ứng dụng
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("video/*");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setPackage(packageName);

                    // Bắt đầu Intent
                    try {
                        context.startActivity(shareIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, appName + " không hỗ trợ chia sẻ video này.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            // Hiển thị thông báo ứng dụng chưa được cài đặt
            Toast.makeText(context, context.getString(R.string.app_not_install), Toast.LENGTH_LONG).show();

            // Mở Google Play hoặc trình duyệt web để tải ứng dụng
            try {
                Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                context.startActivity(playStoreIntent);
            } catch (ActivityNotFoundException e) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                context.startActivity(browserIntent);
            }
        }
    }

    private static boolean isPackageInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String removeExtension(String str) {
        int lastIndexOf = str.lastIndexOf(Objects.requireNonNull(System.getProperty("file.separator")));
        if (lastIndexOf != -1) {
            str = str.substring(lastIndexOf + 1);
        }
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf2 == -1) {
            return str;
        }
        return str.substring(0, lastIndexOf2);
    }

    static Bitmap doBlur(Bitmap sentBitmap) {
        Bitmap bitmap;
        bitmap = sentBitmap;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = 25 + 25 + 1;

        int[] r = new int[wh];
        int[] g = new int[wh];
        int[] b = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int[] vmin = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int[] dv = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = 25 + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -25; i <= 25; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + 25];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = 25;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - 25 + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + 25 + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -25 * w;
            for (i = -25; i <= 25; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + 25];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = 25;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - 25 + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    public static Bitmap convertSameSize(Bitmap originalImage, Bitmap bgBitmap, int mDisplayWidth, int mDisplayHeight, float x, float y) {
        Bitmap cs = bgBitmap.copy(bgBitmap.getConfig(), true);
        Bitmap blurBitmap = doBlur(cs);
        new Canvas(blurBitmap).drawBitmap(newscaleBitmap(originalImage, mDisplayWidth, mDisplayHeight, x, y), 0.0f, 0.0f, new Paint());
        return cs;
    }

    public static Bitmap scaleCenterCrop(Bitmap source, int newWidth, int newHeight) {

        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        if (sourceWidth == newWidth && sourceHeight == newHeight) {
            return source;
        }
        float scale = Math.max(((float) newWidth) / ((float) sourceWidth), ((float) newHeight) / ((float) sourceHeight));
        float scaledWidth = scale * ((float) sourceWidth);
        float scaledHeight = scale * ((float) sourceHeight);
        float left = (((float) newWidth) - scaledWidth) / 2.0f;
        float top = (((float) newHeight) - scaledHeight) / 2.0f;
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        new Canvas(dest).drawBitmap(source, null, targetRect, null);
        return dest;
    }

    private static Bitmap newscaleBitmap(Bitmap originalImage, int width, int height, float x, float y) {
        Bitmap background = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        float originalWidth = (float) originalImage.getWidth();
        float originalHeight = (float) originalImage.getHeight();
        Canvas canvas = new Canvas(background);
        float scale = ((float) width) / originalWidth;
        float scaleY = ((float) height) / originalHeight;
        float xTranslation = 0.0f;
        float yTranslation = (((float) height) - (originalHeight * scale)) / 2.0f;
        if (yTranslation < 0.0f) {
            yTranslation = 0.0f;
            scale = ((float) height) / originalHeight;
            xTranslation = (((float) width) - (originalWidth * scaleY)) / 2.0f;
        }
        Matrix transformation = new Matrix();
        transformation.postTranslate(xTranslation * x, yTranslation + y);
        Log.d("translation", "xTranslation :" + xTranslation + " yTranslation :" + yTranslation);
        transformation.preScale(scale, scale);
        canvas.drawBitmap(originalImage, transformation, new Paint());
        return background;
    }

    public static Bitmap checkBitmap(String path) {
        int orientation = 1;
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        Bitmap bm = BitmapFactory.decodeFile(path, new BitmapFactory.Options());
        try {
            String orientString = new ExifInterface(path).getAttribute("Orientation");
            if (orientString != null) {
                orientation = Integer.parseInt(orientString);
            }
            int rotationAngle = 0;
            if (orientation == 6) {
                rotationAngle = 90;
            }
            if (orientation == 3) {
                rotationAngle = 180;
            }
            if (orientation == 8) {
                rotationAngle = 270;
            }
            Matrix matrix = new Matrix();
            matrix.setRotate((float) rotationAngle, ((float) bm.getWidth()) / 2.0f, ((float) bm.getHeight()) / 2.0f);
            return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void moreApp(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(GlobalConstant.FAMILY_APP));
        context.startActivity(intent);
    }

    public static AdSize getAdSize(Activity context, View adContainer) {
        WindowMetrics windowMetrics = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            windowMetrics = context.getWindowManager().getCurrentWindowMetrics();
        }
        Rect bounds = null;


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            bounds = windowMetrics.getBounds();
        }
        float adWidthPixels = adContainer.getWidth();
        if (adWidthPixels == 0f && bounds != null) {
            adWidthPixels = bounds.width();
        } else if (adWidthPixels == 0f) {
            adWidthPixels = context.getResources().getDisplayMetrics().widthPixels;
        }
        float density = context.getResources().getDisplayMetrics().density;
        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public static void shareApp(Activity context) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            Resources res = context.getResources();

            i.putExtra(Intent.EXTRA_SUBJECT, res.getString(R.string.share_app_tittle));
            String appUrl = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
            String shareMessage = res.getString(R.string.share_message) + "\n" + appUrl;
            i.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(i, res.getString(R.string.share_action)));
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static boolean checkStoragePermissionsSDK33(Activity mContext) {

        String[] permissions = {Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_IMAGES};
        boolean hasPermission = true;

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
                break;
            }
        }
        return hasPermission;

    }

    public static void checkPostNotification(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 111111);
            }
        }
    }

    public static void showRateDialog(Context mContext) {
        if (mContext == null) {
            return;
        }

        RateDialog renameDialog = new RateDialog(mContext);
        Window window = renameDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        renameDialog.show();
    }

    public static void showRenameDialog(Context mContext, String oldName, RenameListener listener) {
        if (mContext == null) {
            return;
        }
        RenameDialog renameDialog = new RenameDialog(mContext, oldName, listener);
        Window window = renameDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        renameDialog.show();
    }

    public static void showPermissionDialog(MainActivity mContext) {
        if (mContext == null) {
            return;
        }
        PermissionDialog dialog = new PermissionDialog(mContext);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();
    }

    public static void showRatioDialog(Context mContext, OnRatioListener listener) {
        if (mContext == null) {
            return;
        }
        ResolutionDialog dialog = new ResolutionDialog(mContext, listener);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();
    }

    public static void showConfirmDialog(Context mContext, int typeConfirm, OnDialogListener listener) {
        if (mContext == null) {
            return;
        }
        ConfirmDialog dialog = new ConfirmDialog(mContext, typeConfirm, listener);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.show();

    }

    public static void feedbackApp(Activity context) {
        try {
            Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + GlobalConstant.EMAIL_FEEDBACK));
            intent3.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.str_feed_back_app));
            intent3.putExtra(Intent.EXTRA_TEXT, "");
            context.startActivity(intent3);
        } catch (ActivityNotFoundException ignored) {
        }
    }

    public static String formatDateToHumanReadable(Long l) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date(l));
    }

    public static void showExitBottomDialog(Activity mContext) {
        if (mContext == null || mContext.isFinishing()) {
            return;
        }
        ExitBottomDialog dialog = new ExitBottomDialog(mContext);
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                dialog.show(((FragmentActivity) mContext).getSupportFragmentManager(), dialog.getTag());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });
    }

    public static void shareVideo(Context context, String videoPath) {
        File file = new File(videoPath);

        if (!file.exists()) {
            Toast.makeText(context, R.string.toast_video_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }
        Uri videoUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file);

        // Create a share Intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("video/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            // Show sharing options
            context.startActivity(Intent.createChooser(shareIntent, context.getResources().getString(R.string.toast_share_video_via)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.toast_unable_to_share_the_video, Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<MyVideo> getCreatedVideo(File directory) {
        ArrayList<MyVideo> videos = new ArrayList<>();

        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                MyVideo fileHolderModel = new MyVideo();
                fileHolderModel.setName(file.getName());
                fileHolderModel.setAbsolutePath(file.getAbsolutePath());
                fileHolderModel.setLength(file.length());
                videos.add(fileHolderModel);
            }
        }


        return videos;


    }

    public static void deletePdfFiles(String str) {
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            StringBuilder sb = new StringBuilder();
            sb.append("find ");
            sb.append(str);
            sb.append(" -xdev -mindepth 1 -delete");
            try {
                Runtime.getRuntime().exec(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}