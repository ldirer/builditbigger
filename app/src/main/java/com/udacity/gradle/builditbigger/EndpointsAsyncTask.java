package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.laurent.myapplication.backend.myApi.MyApi;
import com.example.testing.jokedisplayer.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Integer, String> {
    private static final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();
    private static MyApi myApiService = null;
    private static String mRootUrl = null;
    private Context context;
    private MainActivityFragment mFragment;


    public EndpointsAsyncTask(MainActivityFragment fragment) {
        super();
        mFragment = fragment;
        mRootUrl = "https://build-it-bigger-1210.appspot.com/_ah/api/";
    }

    /**
     * Useful constructor to test the task against a local backend.
     */
    public EndpointsAsyncTask(MainActivityFragment fragment, String rootUrl) {
        super();
        mFragment = fragment;
        mRootUrl = rootUrl;
    }


    //    @SafeVarargs // suppresses a warning on the method having * parameters. Had to make the method final.
    @Override
    protected final String doInBackground(Context... params) {

        publishProgress(0);
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // - 10.0.2.2 is localhost's IP address in Android emulator (.3.2 for genymotion!)
                    // - TODO: remove that option? -> turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setRootUrl(mRootUrl)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }
        context = params[0];

        try {
            String joke = myApiService.getJoke().execute().getData();
            publishProgress(100);
            return joke;
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
            publishProgress(100);
            return "";
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Integer completion = values[0];
        if (completion == 100) {
            Log.d(LOG_TAG, "in onProgressUpdate, full completion");
            // We don't want to hide the progress indicator now, as we're about to display a new activity it would 'flash' the underlying layout to the user.
        }
        else {
            Log.d(LOG_TAG, "in onProgressUpdate, setting visibility to visible!");
            mFragment.showProgressIndicator(completion);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(LOG_TAG, "in onPostExecute");
        // Debug: That's to make sure we see the spinner.
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Intent intent = new Intent(context, JokeActivity.class);
        intent.putExtra(JokeActivity.EXTRA_JOKE_KEY, result);
        context.startActivity(intent);
    }
}
