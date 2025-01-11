package com.tech.slideshow.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tech.slideshow.R;
import com.tech.slideshow.activities.IntroActivity;
import com.tech.slideshow.ads.NativeAdAdmob;

public class IntroTwoFragment extends Fragment {
    private IntroActivity mActivity;

    public IntroTwoFragment() {
    }

    public IntroTwoFragment(IntroActivity mActivity) {
        this.mActivity = mActivity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_two, container, false);
        NativeAdAdmob.showNativeBanner7(mActivity, view);

        return view;

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mActivity = (IntroActivity) context;
        }
    }
}