package com.tech.slideshow.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.tech.slideshow.R;

public class ShareView extends LinearLayout {
    TextView tvName;
    AppCompatImageView imgShare;

    public ShareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_share, this, true);
        imgShare = findViewById(R.id.imgShare);
        tvName = findViewById(R.id.tvShare);
        if (attrs != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ShareView);
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.ShareView_shareIcon, R.drawable.ic_youtube);
            if (resourceId > 0) {
                imgShare.setImageResource(resourceId);
            }
            String string = obtainStyledAttributes.getString(R.styleable.ShareView_shareName);
            if (string != null) {
                tvName.setText(string);
            }
            obtainStyledAttributes.recycle();

        }
        setClickable(true);
        setFocusable(true);
    }


}
