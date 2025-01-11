package com.tech.slideshow.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;
import com.tech.slideshow.SharedPreferenceUtils;
import com.tech.slideshow.adapter.LanguageActivityAdapter;
import com.tech.slideshow.ads.NativeAdAdmob;
import com.tech.slideshow.model.Language;

import java.util.ArrayList;

public class LanguageActivity extends BaseActivity {
    RecyclerView recyclerView;
    LanguageActivityAdapter adapter;
    int langChoice = 0;
    ArrayList<Language> arrayList;
    boolean isFromMainActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.background));
        Intent intent = getIntent();
        if (intent != null) {
            isFromMainActivity = intent.getBooleanExtra(GlobalConstant.FROM_MAIN_ACTIVITY, false);
        }
        NativeAdAdmob.showNativeBig7(this, null);
        recyclerView = findViewById(R.id.rcv_language_list);
        arrayList = GlobalConstant.createArraylistLanguage();
        adapter = new LanguageActivityAdapter(this, lang -> langChoice = lang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        findViewById(R.id.iv_select_over).setOnClickListener(v -> {


            SharedPreferenceUtils.getInstance(LanguageActivity.this).setBoolean(GlobalConstant.LANGUAGE_SET, true);
            SharedPreferenceUtils.getInstance(LanguageActivity.this).setString(GlobalConstant.LANGUAGE_NAME, arrayList.get(langChoice).getNameLanguage());
            SharedPreferenceUtils.getInstance(LanguageActivity.this).setString(GlobalConstant.LANGUAGE_KEY, arrayList.get(langChoice).getKeyLanguage());
            SharedPreferenceUtils.getInstance(LanguageActivity.this).setInt(GlobalConstant.LANGUAGE_KEY_NUMBER, langChoice);

            if (isFromMainActivity) {
                Intent intentMain = new Intent(LanguageActivity.this, MainActivity.class);
                startActivity(intentMain);
            } else {
                Intent refresh = new Intent(LanguageActivity.this, IntroActivity.class);
                refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(refresh);
            }


            finish();
        });
    }
}