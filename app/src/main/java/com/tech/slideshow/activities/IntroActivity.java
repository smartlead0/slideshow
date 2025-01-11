package com.tech.slideshow.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager2.widget.ViewPager2;

import com.tech.slideshow.BaseActivity;
import com.tech.slideshow.R;
import com.tech.slideshow.adapter.ViewPagerAdapter;
import com.tech.slideshow.ads.AdClosedListener;
import com.tech.slideshow.ads.FullAds;
import com.tech.slideshow.fragment.AdsOneFragment;
import com.tech.slideshow.fragment.AdsTwoFragment;
import com.tech.slideshow.fragment.IntroOneFragment;
import com.tech.slideshow.fragment.IntroThreeFragment;
import com.tech.slideshow.fragment.IntroTwoFragment;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class IntroActivity extends BaseActivity {
    public AppCompatButton tvNext;
    public DotsIndicator dotsIndicator;
    public ViewPager2 viewPager2;
    public int positionOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initViews();
        initViewPager();

        tvNext.setOnClickListener(v -> {
            int nextPosition = positionOld + 1;
            if (nextPosition < viewPager2.getAdapter().getItemCount()) {
                viewPager2.setCurrentItem(nextPosition);
            } else {
//                if (SharedPreferenceUtils.getInstance(IntroActivity.this).getBoolean(GlobalConstant.IS_FIRST_PERMISSION, true)) {
//                    SharedPreferenceUtils.getInstance(IntroActivity.this).setBoolean(GlobalConstant.GUIDE_SET, true);
//                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
//                    finish();
//                } else {
                    FullAds.showAds(IntroActivity.this, new AdClosedListener() {
                        @Override
                        public void AdClosed() {
                            startActivity(new Intent(IntroActivity.this, MainActivity.class));
                            finish();
                        }
                    });
//                }
            }
        });
    }

    private void initViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.addFrag(new IntroOneFragment(this), "");
        viewPagerAdapter.addFrag(new AdsOneFragment(this), "");
        viewPagerAdapter.addFrag(new IntroTwoFragment(this), "");
        viewPagerAdapter.addFrag(new AdsTwoFragment(this), "");
        viewPagerAdapter.addFrag(new IntroThreeFragment(this), "");

        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                positionOld = position;
            }
        });
        viewPager2.setOffscreenPageLimit(2); // Điều chỉnh để tiết kiệm bộ nhớ
        viewPager2.setCurrentItem(0);
        dotsIndicator.attachTo(viewPager2);

    }

    private void initViews() {
        tvNext = findViewById(R.id.btNext);
        dotsIndicator = findViewById(R.id.dots_indicator);
        viewPager2 = findViewById(R.id.pagerIntroSlider);

    }
}