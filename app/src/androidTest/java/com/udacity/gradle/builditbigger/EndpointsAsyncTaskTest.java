package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EndpointsAsyncTaskTest {
    private static final String LOG_TAG = EndpointsAsyncTaskTest.class.getSimpleName();
    Context mContext = null;

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     *
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mContext = mActivityRule.getActivity();
    }

    /**
     * Test doInBackground using the local development server.
     * @throws Exception
     */
    @org.junit.Test
    public void testDoInBackgroundLocal() throws Exception {
        String myPort = mContext.getResources().getString(R.string.TEST_SERVER_PORT);

        String localUrl = String.format("http://10.0.3.2:%s/_ah/api/", myPort);
        EndpointsAsyncTask task = new EndpointsAsyncTask(localUrl);

        // Since we call it directly doInBackground runs on the main thread.
        String result = task.doInBackground(mContext);
        Log.d(LOG_TAG, "Result=" + result);
        assertNotEquals(result, "");
    }
}