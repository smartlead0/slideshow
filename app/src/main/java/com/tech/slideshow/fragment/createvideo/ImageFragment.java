package com.tech.slideshow.fragment.createvideo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.slideshow.R;
import com.tech.slideshow.activities.SlideShowActivity;
import com.tech.slideshow.adapter.ImageAdapter;
import com.tech.slideshow.listener.OnImageListener;

public class ImageFragment extends Fragment {
    private SlideShowActivity mActivity;
    private OnImageListener onImageListener;


    public ImageFragment() {
    }

    public ImageFragment(SlideShowActivity mActivity, OnImageListener listener) {
        this.mActivity = mActivity;
        this.onImageListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ImageAdapter adapter = new ImageAdapter(new OnImageListener() {
            @Override
            public void onAddImage() {
                if (onImageListener != null) {
                    onImageListener.onAddImage();
                }
            }

            @Override
            public void onReorder() {
                if (onImageListener != null) {
                    onImageListener.onReorder();
                }
            }
        });
        recyclerView.setAdapter(adapter);
        mActivity.updateImageList(() -> adapter.notifyDataSetChanged());
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mActivity = (SlideShowActivity) context;
        }
    }
}
