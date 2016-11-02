package ru.myitschool.appgameball02;

import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Ball {

    private int x, y, size, speedX, speedY;
    private int screenH, screenW;
    private ImageView image;

    public Ball(MainActivity activity, int screenH, int screenW){

        this.screenH = screenH;
        this.screenW = screenW;
        this.image = new ImageView(activity);
        this.size = 150;

        this.x = (int) (Math.random() * screenH - size);
        this.y = (int) (Math.random() * screenW - size);

        activity.addContentView(image,
                new RelativeLayout.LayoutParams(size, size));

        image.setX(x);
        image.setY(y);
        image.setImageResource(R.drawable.ball);

        this.speedX = (int) (5 + (Math.random() * 30));
        this.speedY = (int) (5 + (Math.random() * 30));
    }
}
