package ru.myitschool.appgameball02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndGameActivity extends Activity {

    TextView mPoints;
    TextView mClicks;
    TextView mTime;
    TextView mLevel;

    public final String APP_PREFERENCES = "GAMEBALL_SCORES";
    private SharedPreferences mSettings;

    private int finalScore;
    private long seconds;
    private int missed;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mPoints = (TextView) findViewById(R.id.end_game_points);
        mClicks = (TextView) findViewById(R.id.end_game_clicks);
        mTime = (TextView) findViewById(R.id.end_game_time);
        mLevel = (TextView) findViewById(R.id.end_game_level);

        int points = (int) getIntent().getDoubleExtra("points", -1);
        if (points == -1) {
            points = getIntent().getIntExtra("points", -1);
        }
        missed = getIntent().getIntExtra("clicks", -1);
        seconds = getIntent().getLongExtra("time", -1);
        level = getIntent().getIntExtra("level", -1);
        finalScore = points - missed / 3;

        mPoints.setText(getResources().getString(R.string.end_game_points, finalScore));
        mClicks.setText(getResources().getString(R.string.end_game_touches, missed));
        mTime.setText(getResources().getString(R.string.end_game_time, seconds));
        mLevel.setText(getResources().getString(R.string.end_game_level, level));
    }


    @Override
    protected void onStop() {
        super.onStop();

        if(mSettings.contains("score")) {
            int previousScore = mSettings.getInt("score", -1);
            if (previousScore < finalScore && previousScore != -1) {

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt("score", finalScore);
                editor.putInt("missed", missed);
                editor.putLong("seconds", seconds);
                editor.putInt("level", level);

                editor.apply();
            }
        } else {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt("score", finalScore);
            editor.putInt("missed", missed);
            editor.putLong("seconds", seconds);
            editor.putInt("level", level);

            editor.apply();
        }
    }

    public void restartGame(View view) {
        if(mSettings.contains("score")) {
            int previousScore = mSettings.getInt("score", -1);
            if (previousScore < finalScore && previousScore != -1) {

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putInt("score", finalScore);
                editor.putInt("missed", missed);
                editor.putLong("seconds", seconds);
                editor.putInt("level", level);

                editor.apply();
            }
        } else {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putInt("score", finalScore);
            editor.putInt("missed", missed);
            editor.putLong("seconds", seconds);
            editor.putInt("level", level);

            editor.apply();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
