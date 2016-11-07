package ru.myitschool.appgameball02;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

public class Ball {

    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private int screenH;
    private int screenW;
    private ImageView image;

    private MainActivity activity;

    public static int points = 0;

    private static int size;


    Ball(MainActivity activity, int screenH, int screenW,
                int beginSpeed, int endSpeed, int size) {

        this.activity = activity;
        this.screenH = screenH;
        this.screenW = screenW;
        this.image = new ImageView(activity);
        this.size = size;

        this.x = (int) (Math.random() * screenH / 2);
        this.y = (int) (Math.random() * screenW / 2);

        Random random = new Random();

        activity.addContentView(image,
                new RelativeLayout.LayoutParams(size, size));

        image.setX(x);
        image.setY(y);
        image.setImageResource(R.drawable.ball);
        image.setOnTouchListener(imageTouch);

        this.speedX = random.nextInt(endSpeed - beginSpeed) + beginSpeed + 1;
        this.speedY = random.nextInt(endSpeed - beginSpeed) + beginSpeed + 1;
    }

    public void move(){
        x += speedX;
        y += speedY;
        image.setX(x);
        image.setY(y);

        if (x < 0 || x > screenW - size)
            speedX = - speedX;
        if (y < 0 || y > screenH - size)
            speedY = - speedY;
    }

    private View.OnTouchListener imageTouch = new View.OnTouchListener() {

        @Override
        public synchronized boolean onTouch(View view, MotionEvent motionEvent) {
            if (MainActivity.balls.contains(Ball.this)) {
                int numParticles = 10;
                MainActivity.textScore.setText(view.getResources().getString(R.string.points, ++Ball.points));
                /* new ParticleSystem(activity, numParticles, R.drawable.particle1, 300L)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(view, numParticles); */
                MainActivity.balls.remove(Ball.this);
                image.setVisibility(View.GONE);
                return true;
            }
            return false;
        }
    };
}
