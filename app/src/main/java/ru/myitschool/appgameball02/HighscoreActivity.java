package ru.myitschool.appgameball02;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HighscoreActivity extends Activity {

    public final String APP_PREFERENCES = "GAMEBALL_SCORES";
    private SharedPreferences mSettings;


    TextView mPoints;
    TextView mClicks;
    TextView mTime;
    TextView mLevel;
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        mText = (TextView) findViewById(R.id.highscore_text);
        mPoints = (TextView) findViewById(R.id.highscore_points);
        mClicks = (TextView) findViewById(R.id.highscore_clicks);
        mTime = (TextView) findViewById(R.id.highscore_time);
        mLevel = (TextView) findViewById(R.id.highscore_level);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (!mSettings.contains("score")) {
            mText.setText("Нету рекордов :(");
            mPoints.setVisibility(View.GONE);
            mClicks.setVisibility(View.GONE);
            mLevel.setVisibility(View.GONE);
            mTime.setVisibility(View.GONE);
        } else {
            mText.setText("Лучший счёт:");

            int finalScore = mSettings.getInt("score", -1);
            int missed = mSettings.getInt("missed", -1);
            long seconds = mSettings.getLong("seconds", -1);
            int level = mSettings.getInt("level", -1);

            mPoints.setText(getResources().getString(R.string.highscore_points, finalScore));
            mClicks.setText(getResources().getString(R.string.highscore_clicks, missed));
            mTime.setText(getResources().getString(R.string.highscore_time, seconds));
            mLevel.setText(getResources().getString(R.string.highscore_level, level));
        }
    }

}
