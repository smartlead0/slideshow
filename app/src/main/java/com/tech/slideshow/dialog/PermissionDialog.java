package com.tech.slideshow.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;

import com.tech.slideshow.R;
import com.tech.slideshow.activities.MainActivity;

public class PermissionDialog extends Dialog {
    private final MainActivity mContext;

    public PermissionDialog(@NonNull MainActivity context) {
        super(context);
        this.mContext = context;
        setContentView(R.layout.dialog_permission);
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                intentSettings.setData(uri);
                mContext.startActivity(intentSettings);
                dismiss();
            }
        });
    }
}
