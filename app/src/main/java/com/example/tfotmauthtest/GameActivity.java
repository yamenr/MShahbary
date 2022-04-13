package com.example.tfotmauthtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    Thread mythread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();// to get screen size
        getWindowManager().getDefaultDisplay().getSize(point);// this will pass our point object and now this point object contains the size of our screen on X-axis and Y-axis

        gameView = new GameView(this, point.x, point.y); // this is to get our size of the screen on Xand Y axis
        setContentView(gameView); // this is to show our gameview
        mythread = new Thread(gameView);
        mythread.start();
        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (event.getX() >= gameView.retry.x && event.getX() <= gameView.retry.x + 200 && event.getY() >= gameView.retry.y && event.getY() <= gameView.retry.y + 200) {
                            if (gameView.isGameOver) {
                              Intent intent = getIntent();
                              finish();
                              startActivity(intent);
                            }
                        } else if (event.getX() < (gameView.screenX / 2)) {
                            gameView.character.isJumping = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        gameView.character.isJumping = false;
                        if (gameView.heart1.x == -500) {
                            gameView.activateslash = true;
                            Log.d("GameView", "activated slash");
                        }
                     break;
                }
                return true;
            }
        });

    }


    @Override
    protected void onPause() { // so when gameview pauses this also pauses
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() { // when gameview resumes this also resumes
        super.onResume();
        gameView.resume();}
}