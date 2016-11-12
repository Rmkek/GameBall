package ru.myitschool.appgameball02;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

class Ball {

    static int points = 0;

    private int x;
    private int y;
    private int speedX;
    private int speedY;
    private int screenH;

    private int screenW;

    private ImageView image;

    private MainActivity activity;

    private int ballSize;

    private Random random = new Random();

    private MediaPlayer mediaPlayer;

    private ViewGroup vg;

    private static int particleResId = R.drawable.star_pink;

    View.OnClickListener ballClick = new View.OnClickListener() {
        private void drawParticles(View view) {
            new ParticleSystem(activity, 50, particleResId, 200)
                    .setSpeedRange(0.1f, 0.20f)
                    .oneShot(view, 50);
        }

        @Override
        public void onClick(View view) {
            if (Ball.this.image.getVisibility() == View.VISIBLE) {
                drawParticles(view);

                MainActivity.updateScore(activity, 1);
                vg.removeView(image);
                MainActivity.removeBall(Ball.this);

                mediaPlayer.start();
            }
        }
    };

    Ball(MainActivity activity, int screenH, int screenW,
                int beginSpeed, int endSpeed) {
        this.activity = activity;
        this.screenH = screenH;
        this.screenW = screenW;
        this.image = new ImageView(activity);
        ballSize = (int) ((screenH % screenW) / 2.8);

        this.x = random.nextInt(screenW - ballSize);
        this.y = random.nextInt(screenH - ballSize);

        activity.addContentView(image,
                new RelativeLayout.LayoutParams(ballSize, ballSize));
        vg = (ViewGroup)(image.getParent());

        image.setX(x);
        image.setY(y);
        image.setImageResource(R.drawable.ball);
        image.getLayoutParams().width = ballSize;
        image.getLayoutParams().height = ballSize;
        image.setOnClickListener(ballClick);

        this.speedX = random.nextInt(endSpeed - beginSpeed) + beginSpeed + 1;
        this.speedY = random.nextInt(endSpeed - beginSpeed) + beginSpeed + 1; // +beginSpeed

        mediaPlayer = MediaPlayer.create(activity, R.raw.balloon_pop);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

    }

    void move(){
        if (x < 0 || x >= screenW - ballSize) {
        speedX = -speedX - random.nextInt(1); //todo: fixme
        }

        if (y < 0 || y >= screenH - ballSize) {
            speedY = -speedY - random.nextInt(1);
        }

        x += speedX;
        y += speedY;
        image.setX(x);
        image.setY(y);
    }

    static synchronized void changeParticle(int drawableResId) {
        particleResId = drawableResId;
    }

}
