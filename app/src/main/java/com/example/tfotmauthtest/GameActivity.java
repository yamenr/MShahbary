package com.example.tfotmauthtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    Point point = new Point();// to get screen size
    getWindowManager().getDefaultDisplay().getSize(point);// this will pass our point object and now this point object contains the size of our screen on X-axis and Y-axis

    gameView = new GameView(this, point.x, point.y); // this is to get our size of the screen on Xand Y axis

    setContentView(gameView); // this is to show our gameview
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