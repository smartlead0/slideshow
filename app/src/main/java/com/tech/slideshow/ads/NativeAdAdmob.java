package com.tech.slideshow.ads;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.tech.slideshow.AdsUtils;
import com.tech.slideshow.GlobalConstant;
import com.tech.slideshow.R;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NativeAdAdmob {



    public static void showNativeBanner7(Activity mActivity, View view) {
        if (GlobalConstant.IS_ADS) {
            ShimmerFrameLayout frLoadingAds;
            FrameLayout frameLayout;
            if (view != null) {
                frameLayout = view.findViewById(R.id.ads_container_banner_7);
                frLoadingAds = view.findViewById(R.id.frLoadingAdsBanner7);
            } else {
                frameLayout = mActivity.findViewById(R.id.ads_container_banner_7);
                frLoadingAds = mActivity.findViewById(R.id.frLoadingAdsBanner7);
            }
            AdLoader.Builder builder = new AdLoader.Builder(mActivity, AdsUtils.ADMOB_ID_NATIVE_TEST);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                    boolean isDestroyed;
                    isDestroyed = mActivity.isDestroyed();
                    if (isDestroyed || mActivity.isFinishing() || mActivity.isChangingConfigurations()) {
                        nativeAd.destroy();
                    }
                    @SuppressLint("InflateParams") NativeAdView adView =
                            (NativeAdView) mActivity.getLayoutInflater()
                                    .inflate(R.layout.ad_native_admob_banner_7, null);
                    populateUnifiedNativeAdBanner(nativeAd, adView);
                    frLoadingAds.setVisibility(View.GONE);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);

                }
            });
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NotNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);

                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }

    private static void populateUnifiedNativeAdBanner(NativeAd nativeAd, NativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }


    public static void showNativeBig7(Activity mActivity, View view) {
        if (GlobalConstant.IS_ADS) {
            ShimmerFrameLayout frLoadingAds;
            FrameLayout frameLayout;

            if (view != null) {
                frameLayout = view.findViewById(R.id.ads_container_big_7);
                frLoadingAds = view.findViewById(R.id.frLoadingAdsBig7);

            } else {
                frameLayout = mActivity.findViewById(R.id.ads_container_big_7);
                frLoadingAds = mActivity.findViewById(R.id.frLoadingAdsBig7);
            }

            AdLoader.Builder builder = new AdLoader.Builder(mActivity, AdsUtils.ADMOB_ID_NATIVE_TEST);
            builder.forNativeAd(nativeAd -> {
                boolean isDestroyed;
                isDestroyed = mActivity.isDestroyed();
                if (isDestroyed || mActivity.isFinishing() || mActivity.isChangingConfigurations()) {
                    nativeAd.destroy();
                }
                @SuppressLint("InflateParams") NativeAdView adView =
                        (NativeAdView) mActivity.getLayoutInflater()
                                .inflate(R.layout.ad_native_admob_big_7, null);
                populateUnifiedNativeAdView1(nativeAd, adView);
                frLoadingAds.setVisibility(View.GONE);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);


            });
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NotNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);

                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }
    public static void showNativeBig8(Activity mActivity, View view) {
        if (GlobalConstant.IS_ADS) {
            ShimmerFrameLayout frLoadingAds;
            FrameLayout frameLayout;

            if (view != null) {
                frameLayout = view.findViewById(R.id.ads_container_big_8);
                frLoadingAds = view.findViewById(R.id.frLoadingAdsBig8);

            } else {
                frameLayout = mActivity.findViewById(R.id.ads_container_big_8);
                frLoadingAds = mActivity.findViewById(R.id.frLoadingAdsBig8);
            }

            AdLoader.Builder builder = new AdLoader.Builder(mActivity, AdsUtils.ADMOB_ID_NATIVE_TEST);
            builder.forNativeAd(nativeAd -> {
                boolean isDestroyed;
                isDestroyed = mActivity.isDestroyed();
                if (isDestroyed || mActivity.isFinishing() || mActivity.isChangingConfigurations()) {
                    nativeAd.destroy();
                }
                @SuppressLint("InflateParams") NativeAdView adView =
                        (NativeAdView) mActivity.getLayoutInflater()
                                .inflate(R.layout.ad_native_admob_big_8, null);
                populateUnifiedNativeAdView1(nativeAd, adView);
                frLoadingAds.setVisibility(View.GONE);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);


            });
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NotNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);

                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }
    public static void showNativeFull(Activity mActivity, View view) {
        if (GlobalConstant.IS_ADS) {
            ShimmerFrameLayout frLoadingAds;
            FrameLayout frameLayout;

            if (view != null) {
                frameLayout = view.findViewById(R.id.ads_container_full);
                frLoadingAds = view.findViewById(R.id.frLoadingAdsFull);

            } else {
                frameLayout = mActivity.findViewById(R.id.ads_container_full);
                frLoadingAds = mActivity.findViewById(R.id.frLoadingAdsFull);
            }

            AdLoader.Builder builder = new AdLoader.Builder(mActivity, AdsUtils.ADMOB_ID_NATIVE_TEST);
            builder.forNativeAd(nativeAd -> {
                boolean isDestroyed;
                isDestroyed = mActivity.isDestroyed();
                if (isDestroyed || mActivity.isFinishing() || mActivity.isChangingConfigurations()) {
                    nativeAd.destroy();
                }
                @SuppressLint("InflateParams") NativeAdView adView =
                        (NativeAdView) mActivity.getLayoutInflater()
                                .inflate(R.layout.ad_native_admob_full, null);
                populateUnifiedNativeAdView1(nativeAd, adView);
                frLoadingAds.setVisibility(View.GONE);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);


            });
            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NotNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);

                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }

    }

    private static void populateUnifiedNativeAdView1(NativeAd nativeAd, NativeAdView adView) {

        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);

    }

}
