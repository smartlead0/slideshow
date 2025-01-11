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

import com.tech.slideshow.R;
import com.tech.slideshow.activities.SlideShowActivity;
import com.tech.slideshow.customviews.indicatorseekbar.IndicatorSeekBar;
import com.tech.slideshow.customviews.indicatorseekbar.OnSeekChangeListener;
import com.tech.slideshow.customviews.indicatorseekbar.SeekParams;
import com.tech.slideshow.listener.DurationListener;

public class TimeFragment extends Fragment {
    private SlideShowActivity mActivity;
    private IndicatorSeekBar seekBar;
    float duration = 2;
    private DurationListener mListener;

    public TimeFragment() {
    }

    public TimeFragment(SlideShowActivity mActivity, DurationListener listener) {
        this.mActivity = mActivity;
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_duration, container, false);
        seekBar = view.findViewById(R.id.seekBarDuration);
        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                switch (seekBar.getProgress()) {
                    case 0:
                        duration = 1.0f;
                        break;
                    case 20:
                        duration = 2.0f;
                        break;
                    case 40:
                        duration = 3.0f;
                        break;
                    case 60:
                        duration = 4.0f;
                        break;
                    case 80:
                        duration = 5.0f;
                        break;
                    case 100:
                        duration = 6.0f;
                        break;

                }
                if (mListener != null) {
                    mListener.onDurationSelected(duration);
                }
            }
        });
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
