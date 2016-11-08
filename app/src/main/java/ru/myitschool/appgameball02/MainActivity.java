package ru.myitschool.appgameball02;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private static List<Ball> balls = null;
    private float countBalls = 0;

    private TextView textTimer;
    private TextView textScore;
    private TextView textTouch;

    private int screenH;
    private int screenW;

    private MyTimer timer = null;

    private int iLevel;

    private long time;

    private int countTouch;

    private float startSpeed;
    private float endSpeed;

    Intent intent;

    public static synchronized void updateScore(MainActivity activity) {
        activity.textScore.setText(activity.getResources().getString(R.string.points, ++Ball.points));
    }

    private synchronized void initStaticVariables() {
        Ball.points = 0;
        balls = new ArrayList<>();
    }

    public static synchronized void removeBall(Ball ball) {
        int index = balls.indexOf(ball);
        balls.remove(index);
        Log.d(TAG, String.format("Ball popped. Index: %d%nBall: %s%nHashCode: %s%n",index,ball, ball.hashCode()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            textTouch.setText(getResources().getString(R.string.touches, ++countTouch));
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = 0;
        iLevel = 1;
        countBalls = 5.0f;
        countTouch = 0;

        startSpeed = 3f;
        endSpeed = 7f;

        textTimer = (TextView) findViewById(R.id.text_timer);
        textScore = (TextView) findViewById(R.id.text_score);
        textTouch = (TextView) findViewById(R.id.text_touch);

        textScore.setText(getResources().getString(R.string.points, 0));
        textTimer.setText(getResources().getString(R.string.timer, 10));
        textTouch.setText(getResources().getString(R.string.touches, 0));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenH = displayMetrics.heightPixels;
        screenW = displayMetrics.widthPixels;

        initStaticVariables();

        createBalls();
        timer = new MyTimer(11000, 10);
        timer.start();
    }

    private void createBalls() {
        for (int i = 0; i < (int) countBalls; i++){
            balls.add(new Ball(MainActivity.this, screenH, screenW,
                    (int) startSpeed, (int) endSpeed, 200));
        }
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(MainActivity.this, MenuActivity.class);
        timer.cancel();
        startActivity(intent);
        finish();
    }

    public class MyTimer extends CountDownTimer {

        MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        private void moveBalls() {
            for (Ball ball : balls){
                ball.move();
            }
        }

        @Override
        public void onTick(long l) {
            time += 10;

            moveBalls();
            textTimer.setText(getResources().getString(R.string.timer, (int)l/1000));

            if (balls.isEmpty()) {
                countBalls += 0.5;
                createBalls();

                startSpeed += 0.5;
                endSpeed += 0.5;

                iLevel++;

                Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.level, iLevel), Toast.LENGTH_SHORT);
                toast.show();

                timer.cancel();
                timer.start();
            }
        }

        @Override
        public void onFinish() {
            intent = new Intent(MainActivity.this, EndGameActivity.class);
            intent.putExtra("points", Ball.points);
            intent.putExtra("clicks", countTouch);
            intent.putExtra("level", iLevel);

            if (iLevel == 1 && (time >= 4000 && time <= 11000)) {
                intent.putExtra("time", 11L);
            } else {
                intent.putExtra("time", time / 1000);
            }
            startActivity(intent);
            finish();
        }
    }
}
