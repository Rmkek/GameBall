package ru.myitschool.appgameball02;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

class Ball {

    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private int screenH;
    private int screenW;

    private ImageView image;

    private MainActivity activity;

    static int points = 0;

    private int ballSize;

    private View.OnTouchListener imageTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (Ball.this.image.getVisibility() != View.GONE) {

                MainActivity.updateScore(activity);
                MainActivity.removeBall(Ball.this);

                image.setVisibility(View.GONE);

                int numParticles = 50;

                new ParticleSystem(activity, numParticles, R.drawable.star_pink, 200)
                        .setSpeedRange(0.1f, 0.20f)
                        .oneShot(view, numParticles);

                activity.dispatchTouchEvent(motionEvent);

                return true;
            }
            return false;
        }
    };

    Ball(MainActivity activity, int screenH, int screenW,
                int beginSpeed, int endSpeed, int size) {

        Random random = new Random();

        this.activity = activity;
        this.screenH = screenH;
        this.screenW = screenW;
        this.image = new ImageView(activity);
        ballSize = size;

        this.x = random.nextInt(screenW - ballSize);
        this.y = random.nextInt(screenH - ballSize);

        activity.addContentView(image,
                new RelativeLayout.LayoutParams(ballSize, ballSize));

        image.setX(x);
        image.setY(y);
        image.setImageResource(R.drawable.ball);
        image.getLayoutParams().width = ballSize;
        image.getLayoutParams().height = ballSize;
        image.setOnTouchListener(imageTouch);

        this.speedX = random.nextInt(endSpeed - beginSpeed) + beginSpeed + 1;
        this.speedY = random.nextInt(endSpeed - beginSpeed) + beginSpeed + 1;
    }

    void move(){
        x += speedX;
        y += speedY;
        image.setX(x);
        image.setY(y);

        if (x < 0 || x >= screenW - 175) {
            speedX = -speedX;
        }

        if (y < 0 || y >= screenH - 175) {
            speedY = -speedY;
        }
    }


}
