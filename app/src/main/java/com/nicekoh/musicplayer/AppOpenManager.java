package com.nicekoh.musicplayer;/*
package com.nicekoh.videoplayer;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;


public class AppOpenManager implements Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "AppOpenManager";
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private Activity currentActivity;
    private final VideoActivityGallery myApplication;

    */
/** Constructor
     * @param myApplication*//*

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public AppOpenManager(VideoActivityGallery myApplication) {
        this.myApplication = myApplication;
    }
    
    */
/** LifecycleObserver methods *//*


    public void onStart() {
        showAdIfAvailable();
        Log.d(LOG_TAG, "onStart");
    }

    */
/** Request an ad *//*

    public void fetchAd() {
        // We will implement this below.
        if (isAdAvailable()){
            return;
        }

        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    */
/**
                     * Called when an app open ad has loaded.
                     *
                     * @param ad the loaded app open ad.
                     *//*

                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        AppOpenManager.this.appOpenAd = ad;
                    }

                    */
/**
                     * Called when an app open ad has failed to load.
                     *
                     * @param loadAdError the error.
                     *//*

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        // Handle the error.
                    }

                };
        AdRequest request = getAdRequest();
        AppOpenAd.load(
                myApplication, AD_UNIT_ID, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    private void showAdIfAvailable() {

        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.");

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Set the reference to null so isAdAvailable() returns false.
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {}

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);

        } else {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAd();
        }

    }

    */
/** ActivityLifecycleCallback methods *//*

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }



    */
/** Creates and returns ad request. *//*

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    */
/** Utility method that checks if ad exists and can be shown. *//*

    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

}
*/
