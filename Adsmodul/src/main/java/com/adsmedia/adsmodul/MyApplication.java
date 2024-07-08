package com.adsmedia.adsmodul;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static OpenAds openAds;

    @Override
    public void onCreate() {
        super.onCreate();
        openAds = new OpenAds(this);
    }
}