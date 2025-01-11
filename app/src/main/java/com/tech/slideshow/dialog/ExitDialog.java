package com.tech.slideshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.tech.slideshow.R;

public class ExitDialog extends Dialog {

    public ExitDialog(@NonNull Activity context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_exit, null);
        setContentView(view);
        findViewById(R.id.tv_bt_positive).setOnClickListener(v -> context.finish());
        findViewById(R.id.tv_bt_negative).setOnClickListener(v -> dismiss());
    }
}
