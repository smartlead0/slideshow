package com.tech.slideshow;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileUtils {

    private static final File mSdCard = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath());


    public static File VIDEO_OUTPUT = new File(MyApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_MOVIES), "SlideShow Maker");

    public static File APP_DIRECTORY = new File(MyApplication.getInstance().getExternalFilesDir(null), "SlideShow Maker");
    public static final File TEMP_DIRECTORY = new File(APP_DIRECTORY, ".temp");
    public static final File TEMP_DIRECTORY_AUDIO = new File(APP_DIRECTORY, ".temp_audio");
    private static final File TEMP_VID_DIRECTORY = new File(TEMP_DIRECTORY, ".temp_vid");
    public static final File frameFile = new File(APP_DIRECTORY, ".frame.png");

    static {
        if (!TEMP_DIRECTORY.exists()) {
            TEMP_DIRECTORY.mkdirs();
        }
        if (!TEMP_VID_DIRECTORY.exists()) {
            TEMP_VID_DIRECTORY.mkdirs();
        }
    }

    private static File getImageDirectory(String theme) {
        File imageDir = new File(TEMP_DIRECTORY, theme);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDir;
    }

    public static File getImageDirectory(String theme, int iNo) {
        File imageDir = new File(getImageDirectory(theme), String.format(Locale.getDefault(), "IMG_%03d", iNo));
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        return imageDir;
    }

    public static void deleteThemeDir(String theme) {
        deleteFile(getImageDirectory(theme));
    }

    public static void deleteTempDir() {
//        for (final File child : Objects.requireNonNull(TEMP_DIRECTORY.listFiles())) {
//            new Thread(() -> FileUtils.deleteFile(child)).start();
//        }
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            deleteTempFiles(TEMP_DIRECTORY);
        });
    }

    private static void deleteTempFiles(File directory) {
        // Kiểm tra thư mục có tồn tại và là thư mục
        if (directory != null && directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Đệ quy xóa các file trong thư mục con
                        deleteTempFiles(file);
                    } else {
                        // Xóa tệp
                        if (!file.delete()) {
                            Log.e("DeleteFiles", "Failed to delete file: " + file.getAbsolutePath());
                        }
                    }
                }
            }
            // Sau khi xóa hết tệp và thư mục con, xóa thư mục hiện tại
            if (!directory.delete()) {
                Log.e("DeleteFiles", "Failed to delete directory: " + directory.getAbsolutePath());
            }
        }
    }

    public static boolean deleteFile(File mFile) {
        boolean idDelete = false;
        if (mFile == null) {
            return true;
        }
        if (mFile.exists()) {
            if (mFile.isDirectory()) {
                idDelete = mFile.delete();
            } else {
                idDelete = mFile.delete();
            }
        }
        return idDelete;
    }

    @SuppressLint({"DefaultLocale"})
    public static String getDuration(long duration) {
        if (duration < 1000) {
            return String.format("%02d:%02d", 0, 0);
        }
        int hours = (int) (duration / (1000 * 60 * 60));
        int minutes = (int) (duration % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((duration % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        }
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String copyFileToSlideShowMakerFolder(File sourceFile, String destinationFileName, Context context) {
        File destinationFile = null;
        String destinationPath = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ sử dụng MediaStore (Scoped Storage)
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, destinationFileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/SlideShow Maker");

            Uri externalContentUri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

            if (externalContentUri == null) {
                Log.e("CopyFile", "Failed to create external content URI");
                return null; // Trả về null nếu không tạo được URI
            }

            try (InputStream inputStream = new FileInputStream(sourceFile);
                 OutputStream outputStream = context.getContentResolver().openOutputStream(externalContentUri)) {

                // Sao chép dữ liệu
                copyStream(inputStream, outputStream);

                // Đặt đường dẫn tệp sau khi sao chép
                destinationPath = getFilePathFromUri(context, externalContentUri);

                // Thêm vào thư viện và xóa file nguồn
                finalizeFileOperation(sourceFile, destinationFileName, context);

            } catch (IOException e) {
                Log.e("CopyFile", "Error copying file to SlideShow Maker folder (Scoped Storage)", e);
            }

        } else {
            // Android 9 trở xuống sử dụng thư mục Movies/SlideShow Maker
            File slideshowFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "SlideShow Maker");
            if (!slideshowFolder.exists() && !slideshowFolder.mkdirs()) {
                Log.e("CopyFile", "Failed to create directory: " + slideshowFolder.getAbsolutePath());
                return null; // Trả về null nếu không tạo được thư mục
            }

            destinationFile = new File(slideshowFolder, destinationFileName);
            destinationPath = destinationFile.getAbsolutePath();

            try (InputStream inputStream = new FileInputStream(sourceFile);
                 OutputStream outputStream = new FileOutputStream(destinationFile)) {

                // Sao chép dữ liệu
                copyStream(inputStream, outputStream);

                // Thêm vào thư viện và xóa file nguồn
                finalizeFileOperation(sourceFile, destinationFile.getAbsolutePath(), context);

            } catch (IOException e) {
                Log.e("CopyFile", "Error copying file to SlideShow Maker folder", e);
            }
        }

        return destinationPath; // Trả về đường dẫn của tệp mới sao chép
    }

    private static String getFilePathFromUri(Context context, Uri uri) {
        String filePath = null;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DATA}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            if (columnIndex != -1) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }

    private static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
    }

    private static void finalizeFileOperation(File sourceFile, String destinationPath, Context context) {
        // Đối với Android 9 trở xuống, cập nhật thư viện hệ thống
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            MediaScannerConnection.scanFile(context, new String[]{destinationPath}, null, null);
        }

        // Xóa file nguồn sau khi sao chép xong
        sourceFile.delete();
        Log.i("CopyFile", "File moved and added to library: " + destinationPath);
    }

//    private static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = inputStream.read(buffer)) > 0) {
//            outputStream.write(buffer, 0, length);
//        }
//    }
//
//    private static void finalizeFileOperation(File sourceFile, String destinationPath, Context context) {
//        // Xóa file nguồn
//        if (sourceFile.delete()) {
//            Log.d("CopyFile", "Source file deleted: " + sourceFile.getAbsolutePath());
//        } else {
//            Log.e("CopyFile", "Failed to delete source file: " + sourceFile.getAbsolutePath());
//        }
//
//        // Quét thư viện media
//        MediaScannerConnection.scanFile(
//                context,
//                new String[]{destinationPath},
//                null,
//                (path, uri) -> Log.d("MediaScanner", "File scanned: " + path)
//        );
//    }
    public   static String getDestinationFilePath(String destinationFileName, Context context) {
        String destinationPath;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ sử dụng Scoped Storage (RELATIVE_PATH)
            destinationPath = Environment.DIRECTORY_MOVIES + "/SlideShow Maker/" + destinationFileName;
        } else {
            // Android 9 trở xuống, lưu trực tiếp vào thư mục Movies/SlideShow Maker
            File slideshowFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "SlideShow Maker");
            destinationPath = new File(slideshowFolder, destinationFileName).getAbsolutePath();
        }

        return destinationPath;
    }
}
