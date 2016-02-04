package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;



/**
 * Used code from: https://developers.google.com/admob/android/interstitial?hl=en to get started with interstitials.
 */
public class MainActivityFragment extends BaseMainActivityFragment {

    private static final String LOG_TAG = MainActivityFragment.class.getCanonicalName();
    // We'll use just one object to display the interstitial ads.
    public InterstitialAd mInterstitialAd;
    private AdView mAdView;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInterstitialAd = new InterstitialAd(getActivity());
        // We'd need to create our own adUnitId at google.com/admob, this one returns test ads.
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id_test));
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new MyAdListener());
        mInterstitialAd.setAdListener(new MyAdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                hideProgressIndicator();
                displayJoke();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        return root;
    }


    @Override
    public void tellJoke() {
        Log.d(LOG_TAG, "in dat cheap tellJoke");

        new FetchJokeTask() {
            @Override
            protected void onPostExecute(String result) {
                // Debug: That's to make sure we see the spinner.
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                mJoke = result;
                // First show the ad! Launching the new activity will be done when the ad is closed.
                if (mInterstitialAd.isLoaded()) {
                    Log.d(LOG_TAG, "Showing the ad?!?");
                    mInterstitialAd.show();
                }
            }
        }.execute(getActivity());
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("see my logcat for id?")
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    /**
     * A super-basic listener that makes sure we load interstitial ads before we need them.
     */
    private class MyAdListener extends AdListener {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
            requestNewInterstitial();
        }
    }
}
