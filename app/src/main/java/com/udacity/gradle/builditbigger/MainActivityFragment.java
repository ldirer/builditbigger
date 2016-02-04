package com.udacity.gradle.builditbigger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * Used code from: https://developers.google.com/admob/android/interstitial?hl=en to get started with interstitials.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    // We'll use just one object to display the interstitial ads.
    public InterstitialAd mInterstitialAd;
    protected ProgressBar mProgress;
    private Button mButton;
    private AdView mAdView;
    private TextView mInstructionsTextView;

    public MainActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInterstitialAd = new InterstitialAd(getActivity());
        // We'd need to create our own adUnitId at google.com/admob, this one returns test ads.
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id_test));
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new MyAdListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgress = (ProgressBar) root.findViewById(R.id.progress_indicator);
        // We can't set the onClickListener in the xml as it expects a method in the context (typically the activity), and our tellJoke method is now in the fragment.
        mButton = (Button) root.findViewById(R.id.button_joke);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });

        mInstructionsTextView = (TextView) root.findViewById(R.id.instructions_text_view);

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
    public void onResume() {
        Log.d(LOG_TAG, "in onResume");
        hideProgressIndicator();
        super.onResume();
    }


    @Override
    public void onPause() {
        Log.d(LOG_TAG, "in onPause");
        // Calling hideProgressIndicator here somehow still 'flashes' the progress spinner.
        super.onPause();
    }


    /**
     * TODO: a better way to do this? Maybe using some layout and setVisibility on the progressBar only?
     */
    public void showProgressIndicator(Integer completion) {
        mInstructionsTextView.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);
        mAdView.setVisibility(View.GONE);
        mProgress.setProgress(completion);
        mProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgressIndicator() {
        mInstructionsTextView.setVisibility(View.VISIBLE);
        mButton.setVisibility(View.VISIBLE);
        mAdView.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }

    public void launchJokeTask() {
        EndpointsAsyncTask fetchJokeTask = new EndpointsAsyncTask(this);
        fetchJokeTask.execute(getActivity());
    }

    public void tellJoke(){
        mInterstitialAd.setAdListener(new MyAdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                launchJokeTask();
            }
        });
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
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
