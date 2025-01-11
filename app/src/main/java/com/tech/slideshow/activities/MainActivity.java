package com.tech.slideshow.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tech.slideshow.AdsUtils;
import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.BuildConfig;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.Utils;
import com.tech.slideshow.ads.AdClosedListener;
import com.tech.slideshow.ads.FullAds;
import com.tech.slideshow.ads.NativeAdAdmob;
import com.tech.slideshow.photopick.Matisse;
import com.tech.slideshow.photopick.MimeType;
import com.tech.slideshow.photopick.engine.GlideEngine;

import java.util.List;
import java.util.Objects;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    TextView tvLang;
    private FrameLayout adContainer;
//    private ExitBottomDialog exitDialog;

    int typeChoice = 1;
    private AdView adView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    BottomSheetDialog exitDialog;
    private boolean doubleBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        initViews();
        initListener();
        setUpExitDialog();
        Utils.checkPostNotification(this);
        NativeAdAdmob.showNativeBig8(this, null);
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle(Utils.setToolBarPdfCreated(this));

        }
    }

    private void setUpExitDialog() {
        exitDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.dialog_exit_bottom, null);
        exitDialog.setContentView(bottomSheetView);
        NativeAdAdmob.showNativeBig7(this, bottomSheetView);
        bottomSheetView.findViewById(R.id.layoutQuestion).setOnClickListener(v -> Utils.feedbackApp(MainActivity.this));
        bottomSheetView.findViewById(R.id.btnExit).setOnClickListener(v -> finish());
    }

    private void initListener() {

        findViewById(R.id.btSlideshow).setOnClickListener(this);
        findViewById(R.id.btMyVideo).setOnClickListener(this);
//        findViewById(R.id.btMenu).setOnClickListener(this);
        findViewById(R.id.bottom_iv1).setOnClickListener(this);
        findViewById(R.id.bottom_iv2).setOnClickListener(this);
        findViewById(R.id.bottom_iv3).setOnClickListener(this);
        findViewById(R.id.bottom_iv4).setOnClickListener(this);

    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.findViewById(R.id.cl_share_app).setOnClickListener(this);
        navigationView.findViewById(R.id.cl_rate_us).setOnClickListener(this);
        navigationView.findViewById(R.id.cl_language_options).setOnClickListener(this);
        navigationView.findViewById(R.id.cl_feedback).setOnClickListener(this);
        navigationView.findViewById(R.id.cl_privacy_policy).setOnClickListener(this);
        tvLang = navigationView.findViewById(R.id.tv_language_hint);
        tvLang.setText(SharedPreferenceUtils.getInstance(this).getString(GlobalConstant.LANGUAGE_NAME, "English"));

        ((TextView) navigationView.findViewById(R.id.tv_version)).setText(getResources().getString(R.string.version, BuildConfig.VERSION_NAME));

        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        adContainer = findViewById(R.id.my_template_banner);
        loadAdBanner();

    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if (idView == R.id.btSlideshow) {
            typeChoice = 1;
            checkPermissions(1);
        } else if (idView == R.id.btMyVideo) {
            typeChoice = 2;
            checkPermissions(2);

        }
//        else if (idView == R.id.btMenu) {
//            drawerLayout.openDrawer(GravityCompat.START);
//        }
        else if (idView == R.id.cl_share_app) {
            Utils.shareApp(this);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.cl_rate_us) {
            Utils.showRateDialog(this);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.cl_language_options) {
            Intent intentLang = new Intent(MainActivity.this, LanguageActivity.class);
            intentLang.putExtra(GlobalConstant.FROM_MAIN_ACTIVITY, true);
            startActivity(intentLang);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.cl_feedback) {
            Utils.moreApp(this);
            drawerLayout.closeDrawers();
        } else if (idView == R.id.cl_privacy_policy) {
            BrowserActivity.startBrowserWebActivity(MainActivity.this, getString(R.string.splash_privacy_policy), GlobalConstant.URL_PRIVACY_POLICY);
            drawerLayout.closeDrawers();

        } else if (idView == R.id.bottom_iv1) {

            Utils.showRateDialog(this);
        } else if (idView == R.id.bottom_iv2) {
            Utils.shareApp(this);
        } else if (idView == R.id.bottom_iv3) {
            Utils.moreApp(this);
        } else if (idView == R.id.bottom_iv4) {
            BrowserActivity.startBrowserWebActivity(MainActivity.this, getString(R.string.splash_privacy_policy), GlobalConstant.URL_PRIVACY_POLICY);

        }
    }

    private void startSelectPhoto(int type) {
        if (type == 1) {
            Matisse.from(this)
                    .choose(MimeType.ofImage(), false)
                    .countable(true)
                    .maxSelectable(50)
                    .showSingleMediaType(true)
                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .showPreview(true) // Default is `true`
                    .forResult(11);
        } else if (type == 2) {

            FullAds.showAds(MainActivity.this, new AdClosedListener() {
                @Override
                public void AdClosed() {
                    Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                    startActivity(intent);
                }
            });

        }
    }


    private void loadAdBanner() {
        adView = new AdView(this);
        adView.setAdUnitId(AdsUtils.ADMOB_ID_BANNER_TEST);

        AdSize adSize = Utils.getAdSize(MainActivity.this, adContainer);
        adView.setAdSize(adSize);

        Bundle extras = new Bundle();
        extras.putString("collapsible", "bottom");
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adContainer.removeAllViews();
                adContainer.addView(adView);
            }
        });

        adView.loadAd(adRequest);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSelectPhoto(typeChoice);
            } else {
                Toast.makeText(this, R.string.toast_permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBack) {
            exitDialog.show();
//                exitDialog.show(getSupportFragmentManager(), "ExitDialog");
        } else {
            doubleBack = true;
            Toast.makeText(this, getResources().getString(R.string.toast_exit_app), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBack = false, 2000);
        }
    }

    private void checkPermissions(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Yêu cầu quyền READ_MEDIA_IMAGES và READ_MEDIA_VIDEO (SDK >= 33)
            Dexter.withContext(this)
                    .withPermissions(
                            Manifest.permission.READ_MEDIA_IMAGES,
                            Manifest.permission.READ_MEDIA_VIDEO
                    )
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                // Quyền được cấp, thực hiện hành động
                                startSelectPhoto(type);
                            } else {
                                // Quyền bị từ chối
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // Người dùng đã chọn "Don't ask again"
                                    Utils.showPermissionDialog(MainActivity.this);
                                } else {
                                    // Quyền bị từ chối nhưng chưa chọn "Don't ask again"
                                    Toast.makeText(MainActivity.this, R.string.toast_need_permission, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(
                                List<PermissionRequest> permissions, PermissionToken token) {
                            // Hiển thị lý do yêu cầu quyền trước khi yêu cầu lại
                            token.continuePermissionRequest();
                        }
                    })
                    .check();
        } else {
            // Xử lý quyền WRITE_EXTERNAL_STORAGE (SDK < 33)
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            // Quyền được cấp
                            startSelectPhoto(type);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            if (response.isPermanentlyDenied()) {
                                // Người dùng đã chọn "Don't ask again"
                                Utils.showPermissionDialog(MainActivity.this);
                            } else {
                                Toast.makeText(MainActivity.this, R.string.toast_need_permission, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(
                                PermissionRequest permission, PermissionToken token) {
                            // Hiển thị lý do yêu cầu quyền trước khi yêu cầu lại
                            token.continuePermissionRequest();
                        }
                    })
                    .check();
        }
    }


}