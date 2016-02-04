package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.laurent.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();
    private static MyApi myApiService = null;
    protected String mRootUrl = null;

    public EndpointsAsyncTask() {
        super();
        // Where does this url belong? Build variable, strings.xml?
        mRootUrl = "https://build-it-bigger-1210.appspot.com/_ah/api/";
    }

    /**
     * Useful constructor to test the task against a local backend.
     */
    public EndpointsAsyncTask(String rootUrl) {
        super();
        mRootUrl = rootUrl;
    }


    @Override
    protected String doInBackground(Context... params) {

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // - 10.0.2.2 is localhost's IP address in Android emulator (.3.2 for genymotion!)
                    // turn off compression when running against local devappserver
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

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage());
            return "";
        }
    }

}
