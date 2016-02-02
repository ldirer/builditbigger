package com.example.testing.jokedisplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/** An activity to display a joke.
*/
public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE_KEY = "joke";
    private static final String LOG_TAG = JokeActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joke_activity);

        JokeActivityFragment fragment = new JokeActivityFragment();
        Intent launchingIntent = getIntent();
        if (null != launchingIntent) {
            Log.d(LOG_TAG, "INTENT NOT NULL");
            fragment.setArguments(launchingIntent.getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment)
                  // Nope, if we add it to the backstack pressing the back button will take us to the (blank) activity.
                  //  .addToBackStack(null)
                    .commit();
        }
        else {
            Log.d(LOG_TAG, "INTENT IS NULL");
        }

        // I first tried to inflate the view *without a fragment*, and failed.
        // I also tried using onCreateView without success.
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        View root = layoutInflater.inflate(R.layout.joke_activity, null);

    }

}
