package com.tech.slideshow.ads;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.tech.slideshow.AdsUtils;


public class FullAds {

    public static InterstitialAd mInterstitialAd;
    public static AdClosedListener mListener;

    public static class InterAdLoadCallBack extends InterstitialAdLoadCallback {
        public final Context mContext;

        public InterAdLoadCallBack(Context context) {
            this.mContext = context;
        }


        public class C2926a extends FullScreenContentCallback {
            public C2926a() {
            }

            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                FullAds.mInterstitialAd = null;
                if (FullAds.mListener != null) {
                    FullAds.mListener.AdClosed();
                }
                FullAds.loadAds(InterAdLoadCallBack.this.mContext);
            }

            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                FullAds.mInterstitialAd = null;
                if (FullAds.mListener != null) {
                    FullAds.mListener.AdClosed();
                }
                FullAds.loadAds(InterAdLoadCallBack.this.mContext);
            }
        }


        @Override
        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            super.onAdFailedToLoad(loadAdError);
            FullAds.mInterstitialAd = null;
//            if (FullAds.mListener != null) {
//                FullAds.mListener.AdClosed();
//            }
        }

        @Override
        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
            super.onAdLoaded(interstitialAd);
            FullAds.mInterstitialAd = interstitialAd;
//            if (FullAds.mListener != null) {
//                mListener.AdLoad();
//            }
            FullAds.mInterstitialAd.setFullScreenContentCallback(new C2926a() {
            });
        }
    }


    public static void loadAds(Context mContext) {
        if (mInterstitialAd == null) {
            InterstitialAd.load(mContext, AdsUtils.ADMOB_ID_FULL_TEST, new AdRequest.Builder().build(), new InterAdLoadCallBack(mContext));
        }
    }

    public static boolean showAds(Activity mActivity, AdClosedListener listener) {
        mListener = listener;
        InterstitialAd interstitialAd = mInterstitialAd;
        if (interstitialAd != null) {
            interstitialAd.show(mActivity);
            return true;
        }
        if (listener != null) {
            listener.AdClosed();
        }
        loadAds(mActivity);
        return false;
    }

}



