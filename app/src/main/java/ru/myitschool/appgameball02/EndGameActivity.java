package ru.myitschool.appgameball02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndGameActivity extends Activity {

    TextView mPoints;
    TextView mClicks;
    TextView mTime;
    TextView mLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        mPoints = (TextView) findViewById(R.id.end_game_points);
        mClicks = (TextView) findViewById(R.id.end_game_clicks);
        mTime = (TextView) findViewById(R.id.end_game_time);
        mLevel = (TextView) findViewById(R.id.end_game_level);

        mPoints.setText(getResources().getString(R.string.end_game_points, getIntent().getIntExtra("points", -1)));
        mClicks.setText(getResources().getString(R.string.end_game_touches, getIntent().getIntExtra("clicks", -1)));
        mTime.setText(getResources().getString(R.string.end_game_time, getIntent().getLongExtra("time", -1)));
        mLevel.setText(getResources().getString(R.string.end_game_level, getIntent().getIntExtra("level", -1)));
    }


    public void restartGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
