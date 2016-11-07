package ru.myitschool.appgameball02;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Ball> balls = null;
    private int countBalls = 5;

    private TextView textTimer, textScore, textTouch;
    private int screenH, screenW;
    private MyTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textTimer = (TextView) findViewById(R.id.text_timer);
        textScore = (TextView) findViewById(R.id.text_score);
        textTouch = (TextView) findViewById(R.id.text_touch);

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
        }

        @Override
        public void onFinish() {
            finish();
        }
    }

    private void moveBalls() {
        for (Ball ball : balls){
            ball.move();
        }
    }
}
