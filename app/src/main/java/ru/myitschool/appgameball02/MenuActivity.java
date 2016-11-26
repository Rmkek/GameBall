package ru.myitschool.appgameball02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View view){
       switch (view.getId()){
           case R.id.btn_new_game:
               //окно начала игры
               intent = new Intent(this, MainActivity.class);
               startActivity(intent);
               break;
           case R.id.btn_continue_game:
                //TODO: continue game.
               break;
           case R.id.btn_exit:
               finishAffinity();
               break;
           case R.id.btn_highscores:
               intent = new Intent(this, HighscoreActivity.class);
               startActivity(intent);
               break;
       }
    }
}
