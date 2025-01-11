package com.tech.slideshow;

import android.os.Environment;

import com.tech.slideshow.model.Language;
import com.tech.slideshow.model.Music;

import java.io.File;
import java.util.ArrayList;

public class GlobalConstant {

    public static Boolean IS_ADS = true;
    public static final String EXTRA_FROM_VIDEO = "EXTRA_FROM_VIDEO";

    public static String PACKAGE_NAME_YOUTUBE = "com.google.android.youtube";
    public static String PACKAGE_NAME_FACEBOOK = "com.facebook.katana";
    public static String PACKAGE_NAME_GMAIL = "com.google.android.gm";
    public static String PACKAGE_NAME_INSTAGRAM = "com.instagram.android";
    public static String PACKAGE_NAME_MESSENGER = "com.facebook.orca";
    public static String PACKAGE_NAME_WHATSAPP = "com.whatsapp";
    public static String RATIO_FRAME = "ratio_frame";
    public static String FROM_MAIN_ACTIVITY = "from_main_activity";
    public static final String IS_RATED_APP = "is_rate_app";

    public static int REQUEST_PICK_AUDIO = 101;
    public static int REQUEST_REORDER_IMAGE = 103;

    public static int REQUEST_PICK_IMAGES = 102;

    public static String FAMILY_APP = "https://play.google.com/store/apps/dev?id=6127028344835403599";
    public static String GUIDE_SET = "guide_set";
    public static final String EXTRA_FROM_PREVIEW = "extra_from_preview";
    public static final String URL_PRIVACY_POLICY = "https://sites.google.com/view/goat-mobile-apps";
    public static String EMAIL_FEEDBACK = "goat.mobile.apps@gmail.com";
    public static File RootDirectoryMyVideoSaved = new File(Environment.getExternalStorageDirectory() + "/Download/SlideShow Maker");
    public static String TAG_LOG = "Thang FATAL";
    public static String VIDEO_PATH = "video_path";
    public static String VIDEO_NAME = "video_name";

    public static String CURRENT_THEME = "current_theme";
    public static int DIALOG_CONFIRM_EXIT = 0;
    public static int DIALOG_CONFIRM_NO_SAVE = 1;
    public static int DIALOG_CONFIRM_STOP_RENDER = 2;
    public static int DIALOG_CONFIRM_DELETE = 3;

    public static String LANGUAGE_KEY = "language_key";
    public static String LANGUAGE_KEY_NUMBER = "language_key_number";
    public static String LANGUAGE_NAME = "language_name";
    public static String LANGUAGE_SET = "language_set";
    public static final String IS_FIRST_PERMISSION = "is_first_permission";
    public static String PHOTO_ORDER_TIP = "photo_order_tip";
        public  static String TEMP_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/SlideShow Maker/temp/";

    public static ArrayList<Language> createArraylistLanguage() {
        ArrayList<Language> arrayList = new ArrayList<>();
        arrayList.add(new Language("en", "English", R.drawable.flag_en));
        arrayList.add(new Language("vi", "Tiếng Việt", R.drawable.flag_vi));
        arrayList.add(new Language("de", "Deutsch", R.drawable.flag_de));
        arrayList.add(new Language("in", "Indonesia", R.drawable.flag_in));
        arrayList.add(new Language("it", "Italiano", R.drawable.flag_it));
        arrayList.add(new Language("ja", "日本語", R.drawable.flag_ja));
        arrayList.add(new Language("ko", "한국어", R.drawable.flag_ko));
        arrayList.add(new Language("pt", "Português", R.drawable.flag_pt));
        arrayList.add(new Language("ru", "Русский", R.drawable.flag_ru));
        arrayList.add(new Language("ar", "عربي", R.drawable.flag_ar));
        arrayList.add(new Language("cs", "čeština", R.drawable.flag_cs));
        arrayList.add(new Language("es", "Español", R.drawable.flag_es));
        arrayList.add(new Language("fr", "Francés", R.drawable.flag_fr));
        arrayList.add(new Language("hi", "हिंदी", R.drawable.flag_hi));
        arrayList.add(new Language("pl", "Język polski", R.drawable.flag_pl));
        arrayList.add(new Language("ro", "Română", R.drawable.flag_ro));

        arrayList.add(new Language("sv", "Svenska", R.drawable.flag_sv));

        arrayList.add(new Language("th", "แบบไทย", R.drawable.flag_th));
        return arrayList;
    }

    public static int[] getFrameList11() {
        return new int[]{R.drawable.frame00,R.drawable.frame_01_11, R.drawable.frame_02_11, R.drawable.frame_03_11, R.drawable.frame_04_11, R.drawable.frame_05_11,
                R.drawable.frame_06_11, R.drawable.frame_07_11, R.drawable.frame_08_11, R.drawable.frame_09_11, R.drawable.frame_10_11, R.drawable.frame_11_11,
                R.drawable.frame_12_11, R.drawable.frame_13_11, R.drawable.frame_14_11, R.drawable.frame_15_11, R.drawable.frame_16_11};

    }

    public static int[] getFrameList43() {
        return new int[]{R.drawable.frame00,R.drawable.frame_01_43, R.drawable.frame_02_43, R.drawable.frame_03_43, R.drawable.frame_04_43, R.drawable.frame_05_43,
                R.drawable.frame_06_43, R.drawable.frame_07_43, R.drawable.frame_08_43, R.drawable.frame_09_43, R.drawable.frame_10_43, R.drawable.frame_11_43,
                R.drawable.frame_12_43, R.drawable.frame_13_43, R.drawable.frame_14_43, R.drawable.frame_15_43, R.drawable.frame_16_43};

    }

    public static int[] getFrameList169() {
        return new int[]{R.drawable.frame00,R.drawable.frame_01_169, R.drawable.frame_02_169, R.drawable.frame_03_169, R.drawable.frame_04_169, R.drawable.frame_05_169,
                R.drawable.frame_06_169, R.drawable.frame_07_169, R.drawable.frame_08_169, R.drawable.frame_09_169, R.drawable.frame_10_169, R.drawable.frame_11_169,
                R.drawable.frame_12_169, R.drawable.frame_13_169, R.drawable.frame_14_169, R.drawable.frame_15_169, R.drawable.frame_16_169};

    }

    public static int[] getFrameList916() {
        return new int[]{R.drawable.frame00,R.drawable.frame_01_916, R.drawable.frame_02_916, R.drawable.frame_03_916, R.drawable.frame_04_916, R.drawable.frame_05_916,
                R.drawable.frame_06_916, R.drawable.frame_07_916, R.drawable.frame_08_916, R.drawable.frame_09_916, R.drawable.frame_10_916, R.drawable.frame_11_916,
                R.drawable.frame_12_916, R.drawable.frame_13_916, R.drawable.frame_14_916, R.drawable.frame_15_916, R.drawable.frame_16_916};

    }

    public static ArrayList<Music> getDataMusic() {
        ArrayList<Music> arrayList = new ArrayList<>();

        arrayList.add(new Music(R.raw.music_1, "Melody 1", R.drawable.thumbnail_snake_on_the_beach));
        arrayList.add(new Music(R.raw.music_2, "Melody 2", R.drawable.thumbnail_happy_birthday));
        arrayList.add(new Music(R.raw.music_3, "Melody 3", R.drawable.thumbnail_after_you));
        arrayList.add(new Music(R.raw.music_4, "Melody 4", R.drawable.thumbnail_auld_lang_syne));
        arrayList.add(new Music(R.raw.music_5, "Melody 5", R.drawable.thumbnail_borderless));
        arrayList.add(new Music(R.raw.music_6, "Melody 6", R.drawable.thumbnail_carol_of_the_bells));


        return arrayList;
    }

}
