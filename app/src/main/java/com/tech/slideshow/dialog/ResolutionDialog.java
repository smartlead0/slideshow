package com.tech.slideshow.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.MyApplication;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.listener.OnRatioListener;

public class ResolutionDialog extends Dialog implements View.OnClickListener {
    private final Context mContext;
    private ImageView imgRatio1, imgRatio2, imgRatio3, imgRatio4, imgRatio5;
    private TextView tvRatio1, tvRatio2, tvRatio3, tvRatio4, tvRatio5;
    private final OnRatioListener mListener;

    public ResolutionDialog(@NonNull Context context, OnRatioListener listener) {
        super(context);
        this.mContext = context;
        this.mListener = listener;
        setContentView(R.layout.dialog_resolution);

        SharedPreferenceUtils.getInstance(mContext).setInt(GlobalConstant.RATIO_FRAME, 11);
        initViews();
        initListener();
        MyApplication.getInstance().setVideoWidth(720);
        MyApplication.getInstance().setVideoHeight(720);
        setSelected(2);

    }

    private void initListener() {
        findViewById(R.id.ratio_1).setOnClickListener(this);
        findViewById(R.id.ratio_2).setOnClickListener(this);
        findViewById(R.id.ratio_3).setOnClickListener(this);
        findViewById(R.id.ratio_4).setOnClickListener(this);
        findViewById(R.id.ratio_5).setOnClickListener(this);
        findViewById(R.id.btDone).setOnClickListener(this);
    }

    private void initViews() {
        imgRatio1 = findViewById(R.id.ratio_1_icon);
        imgRatio2 = findViewById(R.id.ratio_2_icon);
        imgRatio3 = findViewById(R.id.ratio_3_icon);
        imgRatio4 = findViewById(R.id.ratio_4_icon);
        imgRatio5 = findViewById(R.id.ratio_5_icon);

        tvRatio1 = findViewById(R.id.ratio_1_text);
        tvRatio2 = findViewById(R.id.ratio_2_text);
        tvRatio3 = findViewById(R.id.ratio_3_text);
        tvRatio4 = findViewById(R.id.ratio_4_text);
        tvRatio5 = findViewById(R.id.ratio_5_text);

    }

    @Override
    public void onClick(View v) {
        int idView = v.getId();
        if (idView == R.id.ratio_1) {
            MyApplication.getInstance().setVideoWidth(960);
            MyApplication.getInstance().setVideoHeight(720);
            SharedPreferenceUtils.getInstance(mContext).setInt(GlobalConstant.RATIO_FRAME, 43);

            setSelected(1);
        } else if (idView == R.id.ratio_2) {
            MyApplication.getInstance().setVideoWidth(720);
            MyApplication.getInstance().setVideoHeight(720);
            SharedPreferenceUtils.getInstance(mContext).setInt(GlobalConstant.RATIO_FRAME, 11);

            setSelected(2);
        } else if (idView == R.id.ratio_3) {
            MyApplication.getInstance().setVideoWidth(720);
            MyApplication.getInstance().setVideoHeight(1280);
            SharedPreferenceUtils.getInstance(mContext).setInt(GlobalConstant.RATIO_FRAME, 916);

            setSelected(3);
        } else if (idView == R.id.ratio_4) {
            MyApplication.getInstance().setVideoWidth(1280);
            MyApplication.getInstance().setVideoHeight(720);
            SharedPreferenceUtils.getInstance(mContext).setInt(GlobalConstant.RATIO_FRAME, 169);

            setSelected(4);
        }
//        else if (idView == R.id.ratio_5) {
//            MyApplication.getInstance().setVideoWidth(720);
//            MyApplication.getInstance().setVideoHeight(960);
//            SharedPreferenceUtils.getInstance(mContext).setString(GlobalConstant.RATIO_FRAME, "3:4");
//
//            setSelected(5);
//        }
        else if (idView == R.id.btDone) {
            if (mListener != null) {
                mListener.onRatioSelected();
            }
            dismiss();
        }
    }

    private void setSelected(int selectedIndex) {
        // Màu sắc cho TextView
        int selectedColor = mContext.getColor(R.color.colorAccent);
        int defaultColor = mContext.getColor(R.color.text_headline_color);

        // Mảng các TextView và ImageView
        TextView[] textViews = {tvRatio1, tvRatio2, tvRatio3, tvRatio4, tvRatio5};
        ImageView[] imageViews = {imgRatio1, imgRatio2, imgRatio3, imgRatio4, imgRatio5};

        // Vòng lặp để cập nhật trạng thái
        for (int i = 0; i < textViews.length; i++) {
            boolean isSelected = (i + 1 == selectedIndex);

            // Đặt màu cho TextView
            textViews[i].setTextColor(isSelected ? selectedColor : defaultColor);

            // Đặt Drawable cho ImageView
            imageViews[i].setImageResource(isSelected ? R.drawable.icon_ratio_select : R.drawable.ic_ratio);
        }
    }

}
