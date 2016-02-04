package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.testing.jokedisplayer.JokeActivity;


/**
 * A class that will be inherited in all flavors source sets.
 * Behavior is pretty much the same as in the paid flavor, but this avoids code duplication.
 */
public abstract class BaseMainActivityFragment extends Fragment {

    private static final String LOG_TAG = BaseMainActivityFragment.class.getSimpleName();
    protected FrameLayout mProgress;
    protected Button mButton;
    protected TextView mInstructionsTextView;
    protected String mJoke;

    public BaseMainActivityFragment() {
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    BaseMainActivityFragment fragment = this;

    public class FetchJokeTask extends EndpointsAsyncTask {

        @Override
        protected void onPreExecute() {
            fragment.showProgressIndicator();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(LOG_TAG, "in onPostExecute");
            // Debug: That's to make sure we see the spinner.
//            try {
//                Thread.sleep(4000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            mJoke = result;
            displayJoke();
            // I didn't want to hide the progressbar just now in order not to "flash" the underlying layout to the user.
            // The solution I used is not much better though.
        }

    }

    protected void displayJoke() {
        Intent intent = new Intent(fragment.getActivity(), JokeActivity.class);
        intent.putExtra(JokeActivity.EXTRA_JOKE_KEY, mJoke);
        fragment.getActivity().startActivity(intent);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgress = (FrameLayout) root.findViewById(R.id.progress_indicator);
        // We can't set the onClickListener in the xml as it expects a method in the context (typically the activity), and our tellJoke method is now in the fragment.
        mButton = (Button) root.findViewById(R.id.button_joke);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke();
            }
        });

        mInstructionsTextView = (TextView) root.findViewById(R.id.instructions_text_view);

        return root;
    }


    public void showProgressIndicator() {
        Log.d(LOG_TAG, "in showProgressIndicator");
        mProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgressIndicator() {
        Log.d(LOG_TAG, "in hideProgressIndicator");
        mProgress.setVisibility(View.GONE);
    }

    public abstract void tellJoke();

}
