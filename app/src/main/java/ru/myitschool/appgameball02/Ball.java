package ru.myitschool.appgameball02;

import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Ball {

    private int x, y,speedX, speedY;
    private int screenH, screenW;
    private ImageView image;

    private static int size;
    public static int countTouch;

    public Ball(MainActivity activity, int screenH, int screenW,
                int beginSpeed, int endSpeed, int size){

        this.screenH = screenH;
        this.screenW = screenW;
        this.image = new ImageView(activity);
        this.size = size;

        this.x = (int) (Math.random() * screenH / 2);
        this.y = (int) (Math.random() * screenW / 2);

        activity.addContentView(image,
                new RelativeLayout.LayoutParams(size, size));

        image.setX(x);
        image.setY(y);
        image.setImageResource(R.drawable.ball);

        this.speedX = (int) (beginSpeed + (Math.random() * (endSpeed - beginSpeed) + 1));
        this.speedY = (int) (beginSpeed + (Math.random() * (endSpeed - beginSpeed) + 1));
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
}
