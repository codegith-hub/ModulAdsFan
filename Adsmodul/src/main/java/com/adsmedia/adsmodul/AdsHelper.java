package com.adsmedia.adsmodul;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.adsmedia.mastermodul.MasterAdsHelper;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AdsHelper {
    public static boolean openads = false;
    public static String keyopenApp = "f4020d13-3918-4c6a-899b-9585f30cdb84";
    public static void gdpr(Activity activity, Boolean childDirected) {
    }

    public static void initializeAds(Activity activity) {
        if (!AudienceNetworkAds.isInitialized(activity)) {
            AudienceNetworkAds
                    .buildInitSettings(activity)
                    .withInitListener(new AudienceNetworkInitializeHelper())
                    .initialize();
        }

    }
    public static void debugMode(Boolean debug) {
        MasterAdsHelper.debugMode(debug);
        AdSettings.setTestMode(true);
    }
    public static AdView bannerFan;
    public static void showBanner(Activity activity, RelativeLayout layout, String metaId) {
        bannerFan = new AdView(activity, metaId,
                com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        layout.addView(bannerFan);
        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };
        bannerFan.loadAd(bannerFan.buildLoadAdConfig().withAdListener(adListener).build());
    }

    public static InterstitialAd interstitialFAN;

    public static void loadInterstitial(Activity activity, String admobId) {
        interstitialFAN = new com.facebook.ads.InterstitialAd(activity,admobId);
        interstitialFAN.loadAd();
    }

    public static int count = 0;

    public static void showInterstitial(Activity activity, String admobId, int interval) {
        if (count >= interval) {
            if (interstitialFAN == null || !interstitialFAN.isAdLoaded()) {
                MasterAdsHelper.showInterstitial(activity);
            } else {
                interstitialFAN.show();
            }
            loadInterstitial(activity, admobId);
            count = 0;
        } else {
            count++;
        }
    }

    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            //Logger.logStackTrace(TAG,e);
        }
        return "";
    }
}