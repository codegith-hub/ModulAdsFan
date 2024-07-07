package com.adsmedia.adsmodul;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;


import java.util.Date;

public class OpenAds implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    public static String IDOPEN = "";
    public static MyApplication myApplication;
    public static AppOpenAdManager appOpenAdManager;
    public static Activity currentActivity;

    public OpenAds(MyApplication myApplication) {
        OpenAds.myApplication = myApplication;
        OpenAds.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    public static void LoadOpenAds(String idOpenAds) {
        IDOPEN = idOpenAds;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onMoveToForeground() {
        // Show the ad (if available) when the app moves to foreground.
        appOpenAdManager.showAdIfAvailable(currentActivity);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (!appOpenAdManager.isShowingAd) {
            currentActivity = activity;
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }


    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    public static class AppOpenAdManager {
        private static final String LOG_TAG = "AppOpenAdManager";
           private static boolean isLoadingAd = false;
        static boolean isShowingAd = false;
        private static long loadTime = 0;

        public AppOpenAdManager() {
        }

        public static void loadAd(Context context) {
            if (isLoadingAd || isAdAvailable()) {
                return;
            }
            isLoadingAd = true;

        }

        private static boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
            long dateDifference = (new Date()).getTime() - loadTime;
            long numMilliSecondsPerHour = 3600000;
            return (dateDifference < (numMilliSecondsPerHour * numHours));
        }

        private static boolean isAdAvailable() {
            return false;
        }

        public static void showAdIfAvailable(@NonNull final Activity activity) {
            showAdIfAvailable(activity, new OnShowAdCompleteListener() {
                @Override
                public void onShowAdComplete() {

                }
            });
        }

        public static void showAdIfAvailable(
                @NonNull final Activity activity,
                @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
                if (isShowingAd) {
                    return;
                }

                if (!isAdAvailable()) {
                    onShowAdCompleteListener.onShowAdComplete();
                    loadAd(activity);
                    return;
                }

        }
    }
}