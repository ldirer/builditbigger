package com.example.testing.jokedisplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeActivityFragment extends Fragment {

    private static final String LOG_TAG = JokeActivityFragment.class.getSimpleName();
    private String mJoke;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJoke = getArguments().getString(JokeActivity.EXTRA_JOKE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.joke_fragment, container, false);
        TextView jokeTextView = (TextView) root.findViewById(R.id.joke_text_view);
        if (null != mJoke) {
            Log.d(LOG_TAG, "The joke:" + mJoke);
            jokeTextView.setText(Html.fromHtml(mJoke));
        }
        return root;
    }
}
