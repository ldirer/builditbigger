package com.example.testing.jokedisplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeActivityFragment extends Fragment {

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
            jokeTextView.setText(mJoke);
        }
        return root;
    }
}
