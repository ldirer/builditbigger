package com.udacity.gradle.builditbigger;

public class MainActivityFragment extends com.udacity.gradle.builditbigger.BaseMainActivityFragment {

    @Override
    public void tellJoke(){
        new FetchJokeTask().execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        // In the free version we hide on ad closing.
        hideProgressIndicator();
    }
}
