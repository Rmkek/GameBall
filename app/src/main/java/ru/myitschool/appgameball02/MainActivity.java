package ru.myitschool.appgameball02;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected static List<Ball> balls = null;
    protected static int countBalls = 5;

    private TextView textTimer;
    protected static TextView textScore;
    private TextView textTouch;
    private int screenH, screenW;
    private MyTimer timer = null;

    private int countTouch = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        balls = new ArrayList<>();
        createBalls();
        timer = new MyTimer(10000, 10);
        timer.start();
    }

    private void createBalls(){
        for (int i = 0; i < countBalls; i++){
            balls.add(new Ball(MainActivity.this, screenH, screenW,
                    5, 20, 200));
        }
    }

    public class MyTimer extends CountDownTimer{

        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            moveBalls();
            textTimer.setText(getResources().getString(R.string.timer, (int)l/1000));
        }

        @Override
        public void onFinish() {
            Ball.points = 0;
            finish();
        }
    }

    private void moveBalls() {
        for (Ball ball : balls){
            ball.move();
        }
    }

    @Override
    protected void onRestart() {
        Ball.points = 0;
        super.onRestart();
    }
}
