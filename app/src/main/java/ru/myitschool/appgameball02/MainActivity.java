package ru.myitschool.appgameball02;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Intent intent;

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

    private MediaPlayer mediaPlayer;

    public static synchronized void updateScore(MainActivity activity, int amount) {
        Ball.points += amount;
        activity.textScore.setText(activity.getResources().getString(R.string.points, Ball.points));
    }

    private synchronized void initStaticVariables() {
        Ball.points = 0;
        balls = new ArrayList<>();
    }

    public static synchronized void removeBall(Ball ball) {
        int index = balls.indexOf(ball);
        balls.remove(index);
        Log.d(TAG, String.format("Ball popped. Index: %d%nBall: %s%nHashCode: %s%n", index, ball, ball.hashCode()));
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
        countBalls = 3.0f;
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


        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.wow);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Ball.changeParticle(R.drawable.star_pink);

        initStaticVariables();

        createBalls();
        timer = new MyTimer(11000, 10);
        timer.start();
    }

    private void createBalls() {
        for (int i = 0; i < (int) countBalls; i++){
            balls.add(new Ball(MainActivity.this, screenH, screenW,
                    (int) startSpeed, (int) endSpeed));
        }
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(MainActivity.this, MenuActivity.class);
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
                countBalls += 0.25;
                createBalls();

                updateScore(MainActivity.this, (int) l / 1000); //Not wasted time gets into points.

                startSpeed += 0.35;
                endSpeed += 0.4;

                iLevel++;

                switch (iLevel) {
                    case 10:
                        Ball.changeParticle(R.drawable.star_red);
                        releaseAndPlayMedia();
                        break;
                    case 20:
                        Ball.changeParticle(R.drawable.star_cyan);
                        releaseAndPlayMedia();
                        break;
                    case 30:
                        Ball.changeParticle(R.drawable.star_green);
                        releaseAndPlayMedia();
                        break;
                    case 40:
                        Ball.changeParticle(R.drawable.star_orange);
                        mediaPlayer.start();
                        break;
                    case 50:
                        Ball.changeParticle(R.drawable.star_purple);
                        mediaPlayer.start();
                        break;
                    case 60:
                        Ball.changeParticle(R.drawable.star_yellow);
                        mediaPlayer.start();
                        break;
                    case 70:
                        Ball.changeParticle(R.drawable.confeti2);
                        mediaPlayer.start();
                        break;
                    default:
                        Log.d(TAG, "Switch(iLevel) default method was called");
                        break;
                }

                Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.level, iLevel), Toast.LENGTH_SHORT);
                toast.show();

                timer.cancel();
                timer.start();
            }
        }

        @Override
        public void onFinish() {
            intent = new Intent(MainActivity.this, EndGameActivity.class);
            intent.putExtra("points", Ball.points - countTouch / 5);
            intent.putExtra("clicks", countTouch);
            intent.putExtra("level", iLevel);

            if (iLevel == 1 && (time >= 4000 && time <= 11000)) {
                intent.putExtra("time", 11L); // FIXME: 09.11.2016 Bad practice.
            } else {
                intent.putExtra("time", time / 1000);
            }
            startActivity(intent);
            finish();
        }

        private void releaseAndPlayMedia() {
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.wow);
            mediaPlayer.start();
        }

    }

}
