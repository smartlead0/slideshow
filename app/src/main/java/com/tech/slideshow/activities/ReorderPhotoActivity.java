package com.tech.slideshow.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.MyApplication;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.Utils;
import com.tech.slideshow.adapter.PhotoAdapter;
import com.tech.slideshow.ads.AdClosedListener;
import com.tech.slideshow.ads.FullAds;
import com.tech.slideshow.ads.NativeAdAdmob;
import com.tech.slideshow.listener.OnDialogListener;
import com.tech.slideshow.listener.OnPhotoClick;
import com.tech.slideshow.listener.OnRatioListener;
import com.tech.slideshow.model.Photo;
import com.tech.slideshow.photopick.Matisse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReorderPhotoActivity extends BaseActivity implements View.OnClickListener {
    private PhotoAdapter adapter;
//    private boolean isFromPreview = false;

    private ArrayList<Photo> arrayListPhoto;

    private LinearLayout llTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder_photo);
//        isFromPreview = getIntent().hasExtra(GlobalConstant.EXTRA_FROM_PREVIEW);
        NativeAdAdmob.showNativeBig7(this, null);
        arrayListPhoto = MyApplication.getInstance().getSelectedImages();
        initToolBar();
        initViews();
        Intent intent = getIntent();


//        if (intent != null) {
//            isFromPreview = getIntent().hasExtra(GlobalConstant.EXTRA_FROM_PREVIEW);
        if (!MyApplication.getInstance().isPreview) {
            List<String> arrayList = Matisse.obtainPathResult(intent);
            for (int i = 0; i < arrayList.size(); i++) {
                arrayListPhoto.add(new Photo(arrayList.get(i), i));
            }
//            }

        }
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }
    }

    private void initViews() {

        llTip = findViewById(R.id.tipsLl);

        if (SharedPreferenceUtils.getInstance(this).getBoolean(GlobalConstant.PHOTO_ORDER_TIP, false)) {
            llTip.setVisibility(View.GONE);
        }
        findViewById(R.id.tipsCloseIv).setOnClickListener(this);
        findViewById(R.id.add_more).setOnClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.galleryRv);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
        RecyclerViewDragDropManager mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
        mRecyclerViewDragDropManager.setInitiateOnLongPress(true);
        mRecyclerViewDragDropManager.setInitiateOnMove(false);
        mRecyclerViewDragDropManager.setLongPressTimeout(750);
        mRecyclerViewDragDropManager.setDragStartItemAnimationDuration(250);
        mRecyclerViewDragDropManager.setDraggingItemAlpha(0.8f);
        mRecyclerViewDragDropManager.setDraggingItemScale(1.3f);
        mRecyclerViewDragDropManager.setDraggingItemRotation(15.0f);

        final PhotoAdapter myItemAdapter = new PhotoAdapter(this, arrayListPhoto, new OnPhotoClick() {
            @Override
            public void onRemove(int position) {
                arrayListPhoto.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
                if (arrayListPhoto.isEmpty()) {
                    finish();
                }
            }

            @Override
            public void onCrop(int position) {
//                mPosition = position;
//                Photo photo = arrayListPhoto.get(position);
//                idPhoto = photo.getId();
//                startCrop(Uri.fromFile(new File(arrayListPhoto.get(position).getFilePath())));

            }
        });
        adapter = myItemAdapter;
        RecyclerView.Adapter mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(myItemAdapter);      // wrap for dragging
        GeneralItemAnimator animator = new DraggableItemAnimator(); // DraggableItemAnimator is required to make item animations properly.
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mWrappedAdapter);
        recyclerView.setItemAnimator(animator);
        mRecyclerViewDragDropManager.attachRecyclerView(recyclerView);
        CardView btnConvert = findViewById(R.id.btDone);
        btnConvert.setEnabled(true);
        btnConvert.setOnClickListener(this);
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
        if (MyApplication.getInstance().isPreview) {
            MyApplication.getInstance().setSelectedImages(arrayListPhoto);
            setResult(RESULT_OK);
            finish();
        } else {
            Utils.showConfirmDialog(this, GlobalConstant.DIALOG_CONFIRM_EXIT, new OnDialogListener() {
                @Override
                public void onPositiveClick() {
                    FullAds.showAds(ReorderPhotoActivity.this, new AdClosedListener() {
                        @Override
                        public void AdClosed() {
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

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if (idView == R.id.add_more) {


        } else if (idView == R.id.btDone) {
            if (MyApplication.getInstance().isPreview) {
                FullAds.showAds(ReorderPhotoActivity.this, new AdClosedListener() {
                    @Override
                    public void AdClosed() {
                        MyApplication.getInstance().setSelectedImages(arrayListPhoto);
                        setResult(RESULT_OK);
                        finish();
                    }
                });

            } else {
//                ArrayList<String> arrayUri = new ArrayList<>();
//                for (int i = 0; i < arrayListPhoto.size(); i++) {
//                    arrayUri.add(arrayListPhoto.get(i).getFilePath());
//                }
                MyApplication myApplication = MyApplication.getInstance();
                myApplication.setSelectedImages(arrayListPhoto);

                if (arrayListPhoto.size() > 2) {

                    Utils.showRatioDialog(ReorderPhotoActivity.this, new OnRatioListener() {
                        @Override
                        public void onRatioSelected() {
                            FullAds.showAds(ReorderPhotoActivity.this, new AdClosedListener() {
                                @Override
                                public void AdClosed() {
                                    startActivity(new Intent(ReorderPhotoActivity.this, SlideShowActivity.class));
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(myApplication, R.string.toast_more_2_images, Toast.LENGTH_SHORT).show();
                }


            }


        } else if (idView == R.id.tipsCloseIv) {
            llTip.animate().translationY((float) (-llTip.getHeight())).alpha(0.0f).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    llTip.setVisibility(View.GONE);
                    SharedPreferenceUtils.getInstance(ReorderPhotoActivity.this).setBoolean(GlobalConstant.PHOTO_ORDER_TIP, true);
                }
            });
        }
    }
}