package com.tech.slideshow.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.adapter.GalleryAdapter;
import com.tech.slideshow.ads.NativeAdAdmob;
import com.tech.slideshow.customviews.EmptyRecyclerView;
import com.tech.slideshow.model.MyVideo;
import com.tech.slideshow.photopick.Matisse;
import com.tech.slideshow.photopick.MimeType;
import com.tech.slideshow.photopick.engine.GlideEngine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GalleryActivity extends BaseActivity implements View.OnClickListener {
    public EmptyRecyclerView recyclerView;

    public LottieAnimationView loadingView;
    boolean isFromVideo = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        NativeAdAdmob.showNativeBig7(this, null);
        initViews();
        setUpToolbar();
        Intent intent = getIntent();
        if (intent != null) {
            isFromVideo = getIntent().hasExtra(GlobalConstant.EXTRA_FROM_VIDEO);

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
        if (isFromVideo) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void initViews() {
        loadingView = findViewById(R.id.loadingView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(findViewById(R.id.tv_empty_title));
        fetchVideo();
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnAddCard).setOnClickListener(this);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }

    private void fetchVideo() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            ArrayList<MyVideo> galleryModels = getVideoList();
            runOnUiThread(() -> {
                GalleryAdapter objAdapter = new GalleryAdapter(this, galleryModels);
                recyclerView.setAdapter(objAdapter);
                loadingView.setVisibility(View.GONE);
            });
        });
    }

    private ArrayList<MyVideo> getVideoList() {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Thumbnails.DATA
        };
        String selection = MediaStore.Video.Media.DATA + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + "SlideShow Maker" + "%"};
        final String orderBy = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";
        ArrayList<MyVideo> galleryModels = new ArrayList<>();

        try (Cursor cursor = getApplicationContext().getContentResolver()
                .query(uri, projection, selection, selectionArgs, orderBy)) {
            if (cursor != null) {
                int durationIndex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                int dataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                int titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                int dateAddedIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);

                while (cursor.moveToNext()) {
                    MyVideo objModel = new MyVideo();
                    objModel.setLength(cursor.getLong(durationIndex));
                    objModel.setAbsolutePath(cursor.getString(dataIndex));
                    objModel.setName(cursor.getString(titleIndex));
                    long timestamp = cursor.getLong(dateAddedIndex) * 1000;
                    objModel.setLastModified(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                            .format(new Date(timestamp)));

                    galleryModels.add(objModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return galleryModels;
    }

    @Override
    public void onClick(View view) {
        int idView = view.getId();
        if (idView == R.id.btnAddCard || idView == R.id.btnAdd) {
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
        }
    }
}