package com.tech.slideshow.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tech.slideshow.R;
import com.tech.slideshow.Utils;
import com.tech.slideshow.activities.IntroActivity;
import com.tech.slideshow.ads.NativeAdAdmob;

public class IntroOneFragment extends Fragment {
    private IntroActivity mActivity;

    public IntroOneFragment(IntroActivity mActivity) {
        this.mActivity = mActivity;
    }

    public IntroOneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro_one, container, false);
        TextView textView = view.findViewById(R.id.txt_welcome);
        NativeAdAdmob.showNativeBanner7(mActivity, view);
        textView.setText(Utils.setAppName(mActivity));
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
