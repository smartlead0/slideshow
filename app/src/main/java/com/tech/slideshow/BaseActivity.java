package com.tech.slideshow;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tech.slideshow.helper.LocaleHelper;

public class BaseActivity extends AppCompatActivity {
    public BaseActivity baseActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = this;
        LocaleHelper.setLocale(baseActivity, SharedPreferenceUtils.getInstance(this).getString(GlobalConstant.LANGUAGE_KEY, "en"));

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
